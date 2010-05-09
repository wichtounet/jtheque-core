package org.jtheque.features;

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

import org.jtheque.ui.utils.actions.JThequeAction;

import javax.swing.Action;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A Feature of JTheque.
 *
 * @author Baptiste Wicht
 */
public class Feature {
    private FeatureType type;
    private String titleKey;
    private Integer position;
    private String icon;

    private final Collection<Feature> subFeatures = new ArrayList<Feature>(20);
    private final JThequeAction action;

    /**
     * Construct a new Feature for an action.
     *
     * @param type The type of feature.
     * @param position The position of the feature in the parent.
     * @param action The action to execute when the feature is pressed.
     */
    public Feature(FeatureType type, Integer position, JThequeAction action){
        super();

        this.action = action;
        this.type = type;
        this.position = position;
    }

    /**
     * Construct a new Feature for a menu.
     *
     * @param type The type of feature.
     * @param titleKey The i18n key of the title of the feature.
     * @param position The position of the feature in the parent. 
     */
    public Feature(FeatureType type, String titleKey, Integer position){
        super();

        action = null;
        this.type = type;
        this.titleKey = titleKey;
        this.position = position;
    }

    /**
     * The Feature Type.
     *
     * @author Baptiste Wicht
     */
    public enum FeatureType {
        PACK,
        SEPARATED_ACTIONS,
        ACTIONS,
        SEPARATED_ACTION,
        ACTION
    }

    /**
     * Return the type of the feature.
     *
     * @return The type of the feature.
     */
    public final FeatureType getType() {
        return type;
    }

    /**
     * Set the type of the feature.
     *
     * @param type The type of the feature.
     */
    public final void setType(FeatureType type) {
        this.type = type;
    }

    /**
     * Return the internationalization key of the title of the feature.
     *
     * @return The internationalisation key of the feature.
     */
    public final String getTitleKey() {
        return titleKey;
    }

    /**
     * Set the internationalization key of the title of the feature.
     *
     * @param titleKey The title key.
     */
    public final void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    /**
     * Return the action of the feature.
     *
     * @return The action.
     */
    public final JThequeAction getAction() {
        return action;
    }

    /**
     * Return the sub features of the feature.
     *
     * @return A List containing all the sub feature of the feature.
     */
    public final Collection<Feature> getSubFeatures() {
        return subFeatures;
    }

    /**
     * Add a sub feature to the feature.
     *
     * @param feature The feature to add.
     */
    public void addSubFeature(Feature feature) {
        if (feature.type == FeatureType.PACK) {
            throw new IllegalArgumentException(
                    "Unable to add feature of type Pack to a menu");
        }

        subFeatures.add(feature);
    }

    /**
     * Remove a sub feature to the feature.
     *
     * @param feature The feature to remove.
     */
    public void removeSubFeature(Feature feature) {
        subFeatures.remove(feature);
    }

    /**
     * Return the position of the feature.
     *
     * @return The position of the feature.
     */
    public final Integer getPosition() {
        return position;
    }

    /**
     * Set the position of the feature.
     *
     * @param position The position of the feature.
     */
    public final void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * Return the icon id of the feature.
     *
     * @return The icon id of the feature.
     */
    public final String getIcon() {
        return icon;
    }

    /**
     * Set the icon of the feature.
     *
     * @param icon The icon of the feature.
     */
    public final void setIcon(String icon) {
        this.icon = icon;
    }
}