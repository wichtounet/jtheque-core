package org.jtheque.features.impl;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.features.able.CoreFeature;
import org.jtheque.features.able.FeatureListener;
import org.jtheque.features.able.IFeature;
import org.jtheque.features.able.IFeatureService;
import org.jtheque.features.able.Menu;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.SwingUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 * A Feature manager.
 *
 * @author Baptiste Wicht
 */
public final class FeatureService implements IFeatureService, ModuleListener {
    private final WeakEventListenerList listeners = new WeakEventListenerList();
    private final Collection<IFeature> features;
    private final Map<CoreFeature, Feature> coreFeatures;
    private final ILanguageService languageService;

    /**
     * Construct a new FeatureService.
     *
     * @param languageService The language service.
     * @param moduleService   The module service.
     */
    public FeatureService(ILanguageService languageService, IModuleService moduleService) {
        super();

        this.languageService = languageService;

        moduleService.addModuleListener("", this);

        features = new ArrayList<IFeature>(10);

        coreFeatures = new EnumMap<CoreFeature, Feature>(CoreFeature.class);

        coreFeatures.put(CoreFeature.FILE, createAndAddFeature(0, "menu.file"));
        coreFeatures.put(CoreFeature.EDIT, createAndAddFeature(1, "menu.edit"));
        coreFeatures.put(CoreFeature.ADVANCED, createAndAddFeature(990, "menu.advanced"));
        coreFeatures.put(CoreFeature.HELP, createAndAddFeature(1000, "menu.help"));
    }

    @Override
    public void addMenu(String moduleId, final Menu menu) {
        languageService.addInternationalizable(menu);

        if (StringUtils.isNotEmpty(moduleId)) {
            ModuleResourceCache.addResource(moduleId, Menu.class, menu);
        }

        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                for (CoreFeature feature : CoreFeature.values()) {
                    coreFeatures.get(feature).addSubFeatures(menu.getSubFeatures(feature));
                }

                addFeatures(menu.getMainFeatures());

                menu.refreshText(languageService);
            }
        });
    }

    /**
     * Add the given features into the menu.
     *
     * @param newFeatures The features to add to the menu.
     */
    private void addFeatures(Iterable<IFeature> newFeatures) {
        for (IFeature feature : newFeatures) {
            if (feature.getType() != IFeature.FeatureType.PACK) {
                throw new IllegalArgumentException("Can only add feature of type pack directly. ");
            }

            features.add(feature);

            fireFeatureAdded(feature);
        }
    }

    /**
     * Remove the specified menu.
     *
     * @param menu The menu to remove.
     */
    private void removeMenu(Menu menu) {
        for (CoreFeature feature : CoreFeature.values()) {
            coreFeatures.get(feature).removeSubFeatures(menu.getSubFeatures(feature));
        }

        for (IFeature f : menu.getMainFeatures()) {
            features.remove(f);

            fireFeatureRemoved(f);
        }
    }

    /**
     * Create and add the feature.
     *
     * @param position The position of the feature.
     * @param key      The i18n key of the feature.
     *
     * @return The added feature.
     */
    private Feature createAndAddFeature(int position, String key) {
        Feature feature = new ManagedFeature(key, position);

        features.add(feature);

        return feature;
    }

    @Override
    public Collection<IFeature> getFeatures() {
        return CollectionUtils.copyOf(features);
    }

    @Override
    public IFeature getFeature(CoreFeature feature) {
        return coreFeatures.get(feature);
    }

    @Override
    public void addFeatureListener(FeatureListener listener) {
        listeners.add(FeatureListener.class, listener);
    }

    @Override
    public void removeFeatureListener(FeatureListener listener) {
        listeners.add(FeatureListener.class, listener);
    }

    /**
     * Avert the listeners thant a feature has been added.
     *
     * @param feature The feature who's been added.
     */
    private void fireFeatureAdded(IFeature feature) {
        FeatureListener[] l = listeners.getListeners(FeatureListener.class);

        for (FeatureListener listener : l) {
            listener.featureAdded(feature);
        }
    }

    /**
     * Avert the listeners thant a feature has been removed.
     *
     * @param feature The feature who's been removed.
     */
    private void fireFeatureRemoved(IFeature feature) {
        FeatureListener[] l = listeners.getListeners(FeatureListener.class);

        for (FeatureListener listener : l) {
            listener.featureRemoved(feature);
        }
    }

    @Override
    public void moduleStopped(Module module) {
        Set<Menu> resources = ModuleResourceCache.getResource(module.getId(), Menu.class);

        for (Menu menu : resources) {
            removeMenu(menu);
        }

        ModuleResourceCache.removeResourceOfType(module.getId(), Menu.class);
    }

    @Override
    public void moduleStarted(Module module) {
        //Nothing to do here
    }

    @Override
    public void moduleInstalled(Module module) {
        //Nothing to do here
    }

    @Override
    public void moduleUninstalled(Module module) {
        //Nothing to do here
    }

    /**
     * A managed feature.
     *
     * @author Baptiste Wicht
     */
    private final class ManagedFeature extends Feature {
        /**
         * Construct a new ManagedFeature.
         *
         * @param titleKey The title key.
         * @param position The position of the feature.
         */
        protected ManagedFeature(String titleKey, int position) {
            super(null, position, FeatureType.PACK, titleKey, null);
        }

        @Override
        public void addSubFeature(IFeature feature) {
            super.addSubFeature(feature);

            fireSubFeatureAdded(this, feature);
        }

        @Override
        public void removeSubFeature(IFeature feature) {
            super.removeSubFeature(feature);

            fireSubFeatureRemoved(this, feature);
        }

        /**
         * Avert the listeners thant a sub feature has been added in a specific feature.
         *
         * @param feature    The feature in which the sub feature has been added.
         * @param subFeature The subFeature who's been added.
         */
        private void fireSubFeatureAdded(IFeature feature, IFeature subFeature) {
            FeatureListener[] l = listeners.getListeners(FeatureListener.class);

            for (FeatureListener listener : l) {
                listener.subFeatureAdded(feature, subFeature);
            }
        }

        /**
         * Avert the listeners thant a sub feature has been removed in a specific feature.
         *
         * @param feature    The feature in which the sub feature has been removed.
         * @param subFeature The subFeature who's been removed.
         */
        private void fireSubFeatureRemoved(IFeature feature, IFeature subFeature) {
            FeatureListener[] l = listeners.getListeners(FeatureListener.class);

            for (FeatureListener listener : l) {
                listener.subFeatureRemoved(feature, subFeature);
            }
        }
    }
}