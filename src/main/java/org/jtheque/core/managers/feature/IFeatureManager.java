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

import org.jtheque.core.managers.feature.Feature.FeatureType;

import java.util.Collection;

/**
 * A feature manager specification.
 *
 * @author Baptiste Wicht
 */
public interface IFeatureManager {
    /**
     * An enumeration of the core features.
     */
    enum CoreFeature {
        FILE,
        EDIT,
        ADVANCED,
        HELP
    }

    /**
     * Add a menu to the application. It's an object who contains some features that will be
     * added to the menu bar.
     *
     * @param menu The menu to add to the application.
     */
    void addMenu(Menu menu);

    /**
     * Remove the menu from the application.
     *
     * @param menu The menu to remove. 
     */
    void removeMenu(Menu menu);

    /**
     * Create a feature.
     *
     * It's better to create a Custom Menu and to add it using addMenu method.
     *
     * @param position The position of the feature.
     * @param type     The type of the feature.
     * @param key      The internationalization key.
     * @return The created feature.
     */
    @Deprecated
    Feature createFeature(int position, FeatureType type, String key);

    /**
     * Add a sub feature to a parent feature.
     *
     * It's better to create a Custom Menu and to add it using addMenu method.
     *
     * @param parent     The parent feature.
     * @param actionName The bean name of the action.
     * @param type       The type of the sub feature.
     * @param position   The position of the sub feature.
     * @param icon       The icon of the action.
     * @param baseName   The images base name.
     * @return The sub feature.
     */
    @Deprecated
    Feature addSubFeature(Feature parent, String actionName, FeatureType type, int position, String baseName, String icon);

    /**
     * Add a sub feature to a parent feature.
     *
     * It's better to create a Custom Menu and to add it using addMenu method.
     *
     * @param parent     The parent feature.
     * @param actionName The bean name of the action.
     * @param type       The type of the sub feature.
     * @param position   The position of the sub feature.
     * @return The sub feature.
     */
    @Deprecated
    Feature addSubFeature(Feature parent, String actionName, FeatureType type, int position);

    /**
     * Add a feature.
     *
     * It's better to create a Custom Menu and to add it using addMenu method.
     *
     * @param feature The feature to add.
     */
    @Deprecated
    void addFeature(Feature feature);

    /**
     * Remove a feature.
     *
     * It's better to create a Custom Menu and to add it using addMenu method.
     *
     * @param feature The feature to remove.
     */
    @Deprecated
    void removeFeature(Feature feature);

    /**
     * Return all the features of the application.
     *
     * @return A List containing all the features.
     */
    Collection<Feature> getFeatures();

    /**
     * Return the core feature.
     *
     * @param feature The feature type.
     * @return The searched feature.
     */
    Feature getFeature(CoreFeature feature);

    /**
     * Add a feature listener.
     *
     * @param listener The feature listener to add.
     */
    void addFeatureListener(FeatureListener listener);

    /**
     * Remove a feature listener.
     *
     * @param listener The feature listener to remove.
     */
    void removeFeatureListener(FeatureListener listener);
}