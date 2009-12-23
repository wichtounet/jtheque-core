package org.jtheque.core.managers.feature;

import java.util.List;

import org.jtheque.core.managers.feature.IFeatureManager.CoreFeature;

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

/**
 * A menu. It seems a container for features. This container has main features and
 * features for each of the core features.
 *
 * @author Baptiste Wicht
 *
 * @see AbstractMenu
 */
public interface Menu {
    /**
     * Return all the sub features of this menu for the specified CoreFeature.
     *
     * @param feature The core feature.
     *
     * @return A List containing all the sub features of this core feature.
     */
    List<Feature> getSubFeatures(CoreFeature feature);

    /**
     * Return all the main features of this menu.
     *
     * @return A List containing all the main features of this menu. 
     */
    List<Feature> getMainFeatures();
}