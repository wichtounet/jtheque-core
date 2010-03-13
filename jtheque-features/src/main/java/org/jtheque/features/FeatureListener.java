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

import java.util.EventListener;

/**
 * A Feature Listener.
 *
 * @author Baptiste Wicht
 */
public interface FeatureListener extends EventListener {
    /**
     * A feature has been added.
     *
     * @param feature The feature.
     */
    void featureAdded(Feature feature);

    /**
     * A feature has been removed.
     *
     * @param feature The feature.
     */
    void featureRemoved(Feature feature);

    /**
     * A sub feature has been added.
     *
     * @param feature The feature.
     * @param subFeature
     */
    void subFeatureAdded(Feature feature, Feature subFeature);

    /**
     * A sub feature has been removed.
     *
     * @param feature The feature.
     * @param subFeature
     */
    void subFeatureRemoved(Feature feature, Feature subFeature);
}