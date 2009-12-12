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

import org.jdesktop.swingx.event.WeakEventListenerList;
import org.jtheque.core.managers.IManager;
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
public final class FeatureManager implements IFeatureManager, IManager {
    private final WeakEventListenerList listeners;

    /* Features */
    private final Collection<Feature> features;
    private final Map<CoreFeature, Feature> coreFeatures;

    /**
     * Construct a new FeatureManager.
     */
    public FeatureManager() {
        super();

        features = new ArrayList<Feature>(10);
        coreFeatures = new EnumMap<CoreFeature, Feature>(CoreFeature.class);

        listeners = new WeakEventListenerList();
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
        createFileFeature();
        createEditFeature();
        createAdvancedFeature();
        createHelpFeature();
    }

    /**
     * Create the file feature.
     */
    private void createFileFeature() {
        coreFeatures.put(CoreFeature.FILE, createAndAddFeature(0, "menu.file"));

        createBackupFeature();
        createRestoreFeature();

        addSubFeature(getFeature(CoreFeature.FILE), "exitAction", FeatureType.SEPARATED_ACTION, 1000, "exit");
    }

    /**
     * Create the backup feature.
     */
    private void createBackupFeature() {
        Feature backupFeature = createFeature(200, FeatureType.SEPARATED_ACTIONS, "menu.backup");

        addSubFeature(backupFeature, "backupToJTDAction", FeatureType.ACTION, 1, "text");
        addSubFeature(backupFeature, "backupToXMLAction", FeatureType.ACTION, 2, "xml");

        getFeature(CoreFeature.FILE).addSubFeature(backupFeature);
    }

    /**
     * Create the restore feature.
     */
    private void createRestoreFeature() {
        Feature restoreFeature = createFeature(201, FeatureType.ACTIONS, "menu.restore");

        addSubFeature(restoreFeature, "restoreToJTDAction", FeatureType.ACTION, 1, "text");
        addSubFeature(restoreFeature, "restoreToXMLAction", FeatureType.ACTION, 2, "xml");

        getFeature(CoreFeature.FILE).addSubFeature(restoreFeature);
    }

    /**
     * Create the edit feature.
     */
    private void createEditFeature() {
        coreFeatures.put(CoreFeature.EDIT, createAndAddFeature(1, "menu.edit"));
    }

    /**
     * Create the advanced feature.
     */
    private void createAdvancedFeature() {
        coreFeatures.put(CoreFeature.ADVANCED, createAndAddFeature(990, "menu.advanced"));

        addSubFeature(getFeature(CoreFeature.ADVANCED), "displayConfigurationViewAction", FeatureType.SEPARATED_ACTION, 500, "options");
        addSubFeature(getFeature(CoreFeature.ADVANCED), "manageModuleAction", FeatureType.SEPARATED_ACTION, 750, "update");
    }

    /**
     * Create the help feature.
     */
    private void createHelpFeature() {
        coreFeatures.put(CoreFeature.HELP, createAndAddFeature(1000, "menu.help"));

        addSubFeature(getFeature(CoreFeature.HELP), "helpAction", FeatureType.SEPARATED_ACTION, 1, "help");
        addSubFeature(getFeature(CoreFeature.HELP), "informOfABugAction", FeatureType.SEPARATED_ACTION, 2, "mail");
        addSubFeature(getFeature(CoreFeature.HELP), "proposeImprovementAction", FeatureType.SEPARATED_ACTION, 4, "idea");
        addSubFeature(getFeature(CoreFeature.HELP), "displayMessagesAction", FeatureType.SEPARATED_ACTION, 6);
        addSubFeature(getFeature(CoreFeature.HELP), "displayLogViewAction", FeatureType.SEPARATED_ACTION, 25);
        addSubFeature(getFeature(CoreFeature.HELP), "aboutAction", FeatureType.SEPARATED_ACTION, 150, "about");
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

    /**
     * Add a sub feature to the parent feature with the core images base name.
     *
     * @param parent     The parent feature.
     * @param actionName The name of the action bean.
     * @param type       The type of feature.
     * @param position   The position in the parent feature.
     * @param icon       The icon to use.
     * @return The <code>Feature</code>.
     */
    private Feature addSubFeature(Feature parent, String actionName, FeatureType type, int position, String icon) {
        return addSubFeature(parent, actionName, type, position, Managers.getCore().getImagesBaseName(), icon);
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
    private void fireFeatureAdded(Feature feature) {
        FeatureListener[] l = listeners.getListeners(FeatureListener.class);

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
        FeatureListener[] l = listeners.getListeners(FeatureListener.class);

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
    protected final class ManagedFeature extends Feature {
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
            FeatureListener[] l = listeners.getListeners(FeatureListener.class);

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
            FeatureListener[] l = listeners.getListeners(FeatureListener.class);

            FeatureEvent event = new FeatureEvent(this, feature, subFeature);

            for (FeatureListener listener : l) {
                listener.subFeatureRemoved(event);
            }
        }
    }
}