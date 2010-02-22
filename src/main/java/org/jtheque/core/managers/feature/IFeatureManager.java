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