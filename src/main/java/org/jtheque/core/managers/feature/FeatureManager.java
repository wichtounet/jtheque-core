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
import org.jtheque.core.managers.ManagerException;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.feature.Feature.FeatureType;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.Action;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

/**
 * A Feature manager.
 *
 * @author Baptiste Wicht
 */
public final class FeatureManager extends AbstractManager implements IFeatureManager {
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
    public void preInit() {
        //Nothing to do
    }

    @Override
    public void close() throws ManagerException {
        //Nothing to close
    }

    @Override
    public void init() throws ManagerException {
        coreFeatures.put(CoreFeature.FILE, createAndAddFeature(0, "menu.file"));
        coreFeatures.put(CoreFeature.EDIT, createAndAddFeature(1, "menu.edit"));
        coreFeatures.put(CoreFeature.ADVANCED, createAndAddFeature(990, "menu.advanced"));
        coreFeatures.put(CoreFeature.HELP, createAndAddFeature(1000, "menu.help"));

        addMenu(new CoreMenu());
    }

    @Override
    public void addMenu(Menu menu){
        for(CoreFeature feature : CoreFeature.values()){
            for(Feature f : menu.getSubFeatures(feature)){
                getFeature(feature).addSubFeature(f);
            }
        }

        for(Feature f : menu.getMainFeatures()){
            addFeature(f);
        }
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

    @Override
    public Feature createFeature(int position, FeatureType type, String key) {
        Feature feature = new ManagedFeature();
        feature.setPosition(position);
        feature.setType(type);
        feature.setTitleKey(key);

        return feature;
    }

    /**
     * Create and add the feature.
     *
     * @param position The position of the feature.
     * @param key      The i18n key of the feature.
     * @return The added feature.
     */
    private Feature createAndAddFeature(int position, String key) {
        Feature feature = createFeature(position, FeatureType.PACK, key);

        features.add(feature);

        return feature;
    }

    @Override
    public Feature addSubFeature(Feature parent, String actionName, FeatureType type, int position, String baseName, String icon) {
        Action action = Managers.getManager(IResourceManager.class).getAction(actionName);

        action.putValue(Action.SMALL_ICON, Managers.getManager(IResourceManager.class).getIcon(
                baseName,
                icon, ImageType.PNG));

        return addSubFeature(parent, action, type, position);
    }

    @Override
    public Feature addSubFeature(Feature parent, String action, FeatureType type, int position) {
        return addSubFeature(parent, Managers.getManager(IResourceManager.class).getAction(action), type, position);
    }

    /**
     * Add a sub feature to the parent feature.
     *
     * @param action   The <code>Action</code> to use.
     * @param parent   The parent feature.
     * @param type     The type of feature.
     * @param position The position in the parent feature.
     * @return The <code>Feature</code>.
     */
    private static Feature addSubFeature(Feature parent, Action action, FeatureType type, int position) {
        Feature feature = new Feature();
        feature.setType(type);
        feature.setAction(action);
        feature.setPosition(position);

        parent.addSubFeature(feature);

        return feature;
    }

    @Override
    public void addFeature(Feature feature) {
        if (feature.getType() != FeatureType.PACK) {
            throw new IllegalArgumentException("Can only add feature of type pack directly. ");
        }

        features.add(feature);

        fireFeatureAdded(feature);
    }

    @Override
    public void removeFeature(Feature feature) {
        features.remove(feature);

        fireFeatureRemoved(feature);
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