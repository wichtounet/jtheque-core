package org.jtheque.features.able;

import org.jtheque.features.able.IFeature.FeatureType;
import org.jtheque.features.impl.Feature;
import org.jtheque.ui.utils.actions.JThequeAction;

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
 * Simple factory class to create IFeature.
 *
 * @author Baptiste Wicht
 */
public class Features {
    /**
     * Utility class, not instantiable. 
     */
    private Features() {
        throw new AssertionError();
    }

    /**
     * Construct a new IFeature for an action.
     *
     * @param type     The type of feature.
     * @param position The position of the feature in the parent.
     * @param action   The action to execute when the feature is pressed.
     *
     * @return the created IFeature.
     */
    public static IFeature newFeature(FeatureType type, int position, JThequeAction action) {
        return new Feature(action, position, type, null, null);
    }

    /**
     * Construct a new IFeature for a menu.
     *
     * @param type     The type of feature.
     * @param titleKey The i18n key of the title of the feature.
     * @param position The position of the feature in the parent.
     * @param features The sub features of the feature.
     *
     * @return the created IFeature.
     */
    public static IFeature newFeature(FeatureType type, String titleKey, int position, IFeature... features) {
        IFeature feature = new Feature(null, position, type, titleKey, null);

        for (IFeature sub : features) {
            feature.addSubFeature(sub);
        }

        return feature;
    }


    /**
     * Construct a new IFeature for an action.
     *
     * @param type     The type of feature.
     * @param position The position of the feature in the parent.
     * @param action   The action to execute when the feature is pressed.
     * @param icon     The icon of the feature.
     *
     * @return the created IFeature.
     */
    public static IFeature newFeature(FeatureType type, int position, JThequeAction action, String icon) {
        return new Feature(action, position, type, null, icon);
    }
}