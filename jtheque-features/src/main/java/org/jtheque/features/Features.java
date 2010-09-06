package org.jtheque.features;

import org.jtheque.features.Feature.FeatureType;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;
import java.util.Collections;

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

/**
 * Simple factory class to create Feature. The features returned by this factory are thread safe.
 *
 * @author Baptiste Wicht
 */
public final class Features {
    /**
     * Utility class, not instantiable.
     */
    private Features() {
        throw new AssertionError();
    }

    /**
     * Create a pack feature.
     *
     * @param position The position of the feature in the menu bar.
     * @param key      The i18n key of the feature.
     * @param features The sub features.
     *
     * @return The created main feature.
     */
    public static Feature newPackFeature(int position, String key, Feature... features) {
        return newFeature(FeatureType.PACK, key, position, features);
    }

    /**
     * Create a separated (it seems with a line separator) feature with a given action.
     *
     * @param position The position of the feature.
     * @param action   The action of the feature.
     *
     * @return The created Feature.
     */
    public static Feature newActionFeature(int position, JThequeAction action) {
        return newFeature(FeatureType.ACTION, position, action);
    }

    /**
     * Create a separated (it seems with a line separator) feature with a given action.
     *
     * @param position The position of the feature.
     * @param action   The action of the feature.
     * @param icon     The icon of the feature.
     *
     * @return The created Feature.
     */
    public static Feature newActionFeature(int position, JThequeAction action, String icon) {
        return newFeature(FeatureType.ACTION, position, action, icon);
    }

    /**
     * Create a separated (it seems with a line separator) feature with a given action.
     *
     * @param position The position of the feature.
     * @param action   The action of the feature.
     *
     * @return The created Feature.
     */
    public static Feature newSeparatedActionFeature(int position, JThequeAction action) {
        return newFeature(FeatureType.SEPARATED_ACTION, position, action);
    }


    /**
     * Create a separated (it seems with a line separator) feature with a given action.
     *
     * @param position The position of the feature.
     * @param action   The action of the feature.
     * @param icon     The icon of the feature.
     *
     * @return The created Feature.
     */
    public static Feature newSeparatedActionFeature(int position, JThequeAction action, String icon) {
        return newFeature(FeatureType.SEPARATED_ACTION, position, action, icon);
    }

    /**
     * Create an actions feature with the given sub features.
     *
     * @param position The position of the feature.
     * @param key      The i18n key of the feature.
     * @param features The sub features.
     *
     * @return The created feature.
     */
    public static Feature newActionsFeature(int position, String key, Feature... features) {
        return newFeature(FeatureType.ACTIONS, key, position, features);
    }

    /**
     * Create an actions (it seems with a line separator) feature with the given sub features.
     *
     * @param position The position of the feature.
     * @param key      The i18n key of the feature.
     * @param features The sub features.
     *
     * @return The created separated feature.
     */
    public static Feature newSeparatedActionsFeature(int position, String key, Feature... features) {
        return newFeature(FeatureType.SEPARATED_ACTIONS, key, position, features);
    }

    /**
     * Construct a new Feature for an action.
     *
     * @param type     The type of feature.
     * @param position The position of the feature in the parent.
     * @param action   The action to execute when the feature is pressed.
     * @param icon     The icon of the feature.
     *
     * @return the created Feature.
     */
    private static Feature newFeature(FeatureType type, int position, JThequeAction action, String icon) {
        if (position < 0) {
            throw new IllegalArgumentException("position cannot be negative");
        }

        if(action == null){
            throw new IllegalArgumentException("action cannot be null");
        }

        if(StringUtils.isEmpty(icon)){
            throw new IllegalArgumentException("icon cannot be empty");
        }

        return new SimpleFeature(action, position, type, null, icon);
    }

    /**
     * Construct a new Feature for an action.
     *
     * @param type     The type of feature.
     * @param position The position of the feature in the parent.
     * @param action   The action to execute when the feature is pressed.
     *
     * @return the created Feature.
     */
    private static Feature newFeature(FeatureType type, int position, JThequeAction action) {
        if (position < 0) {
            throw new IllegalArgumentException("position cannot be negative");
        }

        if (action == null) {
            throw new IllegalArgumentException("action cannot be null");
        }

        return new SimpleFeature(action, position, type, null, null);
    }

    /**
     * Construct a new Feature for a menu.
     *
     * @param type     The type of feature.
     * @param titleKey The i18n key of the title of the feature.
     * @param position The position of the feature in the parent.
     * @param features The sub features of the feature.
     *
     * @return the created Feature.
     */
    private static Feature newFeature(FeatureType type, String titleKey, int position, Feature... features) {
        if (position < 0) {
            throw new IllegalArgumentException("position cannot be negative");
        }

        if (StringUtils.isEmpty(titleKey)) {
            throw new IllegalArgumentException("icon cannot be empty");
        }

        Feature feature = new SimpleFeature(null, position, type, titleKey, null);

        for (Feature sub : features) {
            feature.addSubFeature(sub);
        }

        return feature;
    }

    /**
     * A Feature of JTheque.
     *
     * @author Baptiste Wicht
     */
    @ThreadSafe
    private static final class SimpleFeature implements Feature {
        @GuardedInternally
        private final Collection<Feature> subFeatures = CollectionUtils.newConcurrentList();

        private final JThequeAction action;
        private final FeatureType type;
        private final String titleKey;
        private final String icon;
        private final int position;

        /**
         * Construct a new Feature.
         *
         * @param action   The action.
         * @param position The position in the parent menu.
         * @param type     The type of feature.
         * @param titleKey The i18n title key.
         * @param icon     The icon name.
         */
        private SimpleFeature(JThequeAction action, int position, FeatureType type, String titleKey, String icon) {
            super();

            this.action = action;
            this.position = position;
            this.type = type;
            this.titleKey = titleKey;
            this.icon = icon;
        }

        @Override
        public FeatureType getType() {
            return type;
        }

        @Override
        public String getTitleKey() {
            return titleKey;
        }

        @Override
        public JThequeAction getAction() {
            return action;
        }

        @Override
        public Collection<Feature> getSubFeatures() {
            return Collections.unmodifiableCollection(subFeatures);
        }

        @Override
        public void addSubFeature(Feature feature) {
            if (feature.getType() == FeatureType.PACK) {
                throw new IllegalArgumentException("Cannot add feature of type Pack to a menu");
            }

            subFeatures.add(feature);
        }

        @Override
        public void addSubFeatures(Collection<Feature> subFeatures) {
            for (Feature feature : subFeatures) {
                addSubFeature(feature);
            }
        }

        @Override
        public void removeSubFeatures(Collection<Feature> features) {
            subFeatures.removeAll(features);
        }

        @Override
        public int getPosition() {
            return position;
        }

        @Override
        public String getIcon() {
            return icon;
        }
    }
}