package org.jtheque.core.managers.feature;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.core.managers.AbstractManager;
import org.jtheque.core.managers.feature.Feature.FeatureType;
import org.jtheque.core.managers.module.ModuleListener;
import org.jtheque.core.managers.module.ModuleResourceCache;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.module.beans.ModuleState;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.CollectionUtils;

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
public final class FeatureManager extends AbstractManager implements IFeatureManager, ModuleListener {
    private final Collection<Feature> features;

    private final Map<CoreFeature, Feature> coreFeatures;

    /**
     * Construct a new FeatureManager.
     */
    public FeatureManager() {
        super();

        features = new ArrayList<Feature>(10);
        coreFeatures = new EnumMap<CoreFeature, Feature>(CoreFeature.class);
    }

    @Override
    public void init(){
        coreFeatures.put(CoreFeature.FILE, createAndAddFeature(0, "menu.file"));
        coreFeatures.put(CoreFeature.EDIT, createAndAddFeature(1, "menu.edit"));
        coreFeatures.put(CoreFeature.ADVANCED, createAndAddFeature(990, "menu.advanced"));
        coreFeatures.put(CoreFeature.HELP, createAndAddFeature(1000, "menu.help"));

        addMenu("", new CoreMenu());
    }

    @Override
    public void addMenu(String moduleId, Menu menu){
        for(CoreFeature feature : CoreFeature.values()){
            for(Feature f : menu.getSubFeatures(feature)){
                getFeature(feature).addSubFeature(f);
            }
        }

        for(Feature f : menu.getMainFeatures()){
            addFeature(f);
        }

        if(StringUtils.isNotEmpty(moduleId)){
            ModuleResourceCache.addResource(moduleId, Menu.class, menu);
        }
    }

    /**
     * Add a feature to the menu.
     *
     * @param feature The feature to add.
     */
    private void addFeature(Feature feature) {
        if (feature.getType() != FeatureType.PACK) {
            throw new IllegalArgumentException("Can only add feature of type pack directly. ");
        }

        features.add(feature);

        fireFeatureAdded(feature);
    }

    @Override
    public void removeMenu(Menu menu){
        for(CoreFeature feature : CoreFeature.values()){
            for(Feature f : menu.getSubFeatures(feature)){
                getFeature(feature).removeSubFeature(f);
            }
        }

        for(Feature f : menu.getMainFeatures()){
            removeFeature(f);
        }
    }

    /**
     * Remove the features from the main menu.
     *
     * @param feature The feature to remove.
     */
    private void removeFeature(Feature feature) {
        features.remove(feature);

        fireFeatureRemoved(feature);
    }

    /**
     * Create and add the feature.
     *
     * @param position The position of the feature.
     * @param key      The i18n key of the feature.
     * @return The added feature.
     */
    private Feature createAndAddFeature(int position, String key) {
        Feature feature = new ManagedFeature();
        feature.setPosition(position);
        feature.setType(FeatureType.PACK);
        feature.setTitleKey(key);

        features.add(feature);

        return feature;
    }

    @Override
    public Collection<Feature> getFeatures() {
        return CollectionUtils.copyOf(features);
    }

    @Override
    public Feature getFeature(CoreFeature feature) {
        return coreFeatures.get(feature);
    }

    @Override
    public void addFeatureListener(FeatureListener listener) {
        getListeners().add(FeatureListener.class, listener);
    }

    @Override
    public void removeFeatureListener(FeatureListener listener) {
        getListeners().add(FeatureListener.class, listener);
    }

    /**
     * Avert the listeners thant a feature has been added.
     *
     * @param feature The feature who's been added.
     */
    private void fireFeatureAdded(Feature feature) {
        FeatureListener[] l = getListeners().getListeners(FeatureListener.class);

        FeatureEvent event = new FeatureEvent(this, feature);

        for (FeatureListener listener : l) {
            listener.featureAdded(event);
        }
    }

    /**
     * Avert the listeners thant a feature has been removed.
     *
     * @param feature The feature who's been removed.
     */
    private void fireFeatureRemoved(Feature feature) {
        FeatureListener[] l = getListeners().getListeners(FeatureListener.class);

        FeatureEvent event = new FeatureEvent(this, feature);

        for (FeatureListener listener : l) {
            listener.featureRemoved(event);
        }
    }

    @Override
    public void moduleStateChanged(ModuleContainer module, ModuleState newState, ModuleState oldState) {
        if(oldState == ModuleState.LOADED && (newState == ModuleState.INSTALLED ||
                newState == ModuleState.DISABLED || newState == ModuleState.UNINSTALLED)){
            Set<Menu> resources = ModuleResourceCache.getResource(module.getId(), Menu.class);

            for(Menu menu : resources){
                removeMenu(menu);
            }

            ModuleResourceCache.removeResourceOfType(module.getId(), Menu.class);
        }
    }

    /**
     * A managed feature.
     *
     * @author Baptiste Wicht
     */
    protected static final class ManagedFeature extends Feature {
        @Override
        public void addSubFeature(Feature feature) {
            super.addSubFeature(feature);

            fireSubFeatureAdded(this, feature);
        }

        @Override
        public void removeSubFeature(Feature feature) {
            super.removeSubFeature(feature);

            fireSubFeatureRemoved(this, feature);
        }

        /**
         * Avert the listeners thant a sub feature has been added in a specific feature.
         *
         * @param feature    The feature in which the sub feature has been added.
         * @param subFeature The subFeature who's been added.
         */
        private void fireSubFeatureAdded(Feature feature, Feature subFeature) {
            FeatureListener[] l = getListeners().getListeners(FeatureListener.class);

            FeatureEvent event = new FeatureEvent(this, feature, subFeature);

            for (FeatureListener listener : l) {
                listener.subFeatureAdded(event);
            }
        }

        /**
         * Avert the listeners thant a sub feature has been removed in a specific feature.
         *
         * @param feature    The feature in which the sub feature has been removed.
         * @param subFeature The subFeature who's been removed.
         */
        private void fireSubFeatureRemoved(Feature feature, Feature subFeature) {
            FeatureListener[] l = getListeners().getListeners(FeatureListener.class);

            FeatureEvent event = new FeatureEvent(this, feature, subFeature);

            for (FeatureListener listener : l) {
                listener.subFeatureRemoved(event);
            }
        }
    }
}