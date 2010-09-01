package org.jtheque.features;

import org.jtheque.features.Feature.FeatureType;
import org.jtheque.ui.utils.actions.JThequeAction;
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
     * Construct a new Feature for an action.
     *
     * @param type     The type of feature.
     * @param position The position of the feature in the parent.
     * @param action   The action to execute when the feature is pressed.
     *
     * @return the created Feature.
     */
    public static org.jtheque.features.Feature newFeature(FeatureType type, int position, JThequeAction action) {
        return new Feature(action, position, type, null, null);
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
    public static org.jtheque.features.Feature newFeature(FeatureType type, String titleKey, int position, org.jtheque.features.Feature... features) {
        org.jtheque.features.Feature feature = new Feature(null, position, type, titleKey, null);

        for (org.jtheque.features.Feature sub : features) {
            feature.addSubFeature(sub);
        }

        return feature;
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
    public static org.jtheque.features.Feature newFeature(FeatureType type, int position, JThequeAction action, String icon) {
        return new Feature(action, position, type, null, icon);
    }

    /**
     * A Feature of JTheque.
     *
     * @author Baptiste Wicht
     */
    @ThreadSafe
    private static final class Feature implements org.jtheque.features.Feature {
        @GuardedInternally
        private final Collection<org.jtheque.features.Feature> subFeatures = CollectionUtils.newConcurrentList();
        
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
        private Feature(JThequeAction action, int position, FeatureType type, String titleKey, String icon) {
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
        public Collection<org.jtheque.features.Feature> getSubFeatures() {
            return Collections.unmodifiableCollection(subFeatures);
        }

        @Override
        public void addSubFeature(org.jtheque.features.Feature feature) {
            if (feature.getType() == FeatureType.PACK) {
                throw new IllegalArgumentException("Cannot add feature of type Pack to a menu");
            }

            subFeatures.add(feature);
        }

        @Override
        public void addSubFeatures(Collection<org.jtheque.features.Feature> subFeatures) {
            for (org.jtheque.features.Feature feature : subFeatures) {
                addSubFeature(feature);
            }
        }

        @Override
        public void removeSubFeatures(Collection<org.jtheque.features.Feature> features) {
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