package org.jtheque.features;

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
    private String baseName;

    private final Collection<Feature> subFeatures = new ArrayList<Feature>(20);
    private final Action action;

    /**
     * Construct a new Feature for an action.
     *
     * @param type The type of feature.
     * @param position The position of the feature in the parent.
     * @param action The action to execute when the feature is pressed.
     */
    public Feature(FeatureType type, Integer position, Action action){
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
    public final Action getAction() {
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
     * Return the icon of the feature.
     *
     * @return The icon of the feature.
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

    /**
     * Return the base name for getting the icon.
     *
     * @return The base name.
     */
    public final String getBaseName() {
        return baseName;
    }

    /**
     * Set the base name for the resources.
     *
     * @param baseName The base name.
     */
    public final void setBaseName(String baseName) {
        this.baseName = baseName;
	}
}