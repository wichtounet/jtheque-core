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

import java.util.EventObject;

/**
 * A Feature Event. It seems an event who's thrown when a change occurs in the feature manager.
 *
 * @author Baptiste Wicht
 */
public final class FeatureEvent extends EventObject {
    private static final long serialVersionUID = -6305357619064873817L;

    private final Feature feature;
    private Feature subFeature;

    /**
     * Construct a new FeatureEvent.
     *
     * @param source  The source of the event.
     * @param feature The feature.
     */
    public FeatureEvent(Object source, Feature feature) {
        super(source);

        this.feature = feature;
    }

    /**
     * Construct a new FeatureEvent.
     *
     * @param source     The source of the event.
     * @param feature    The feature.
     * @param subFeature The sub feature.
     */
    public FeatureEvent(Object source, Feature feature, Feature subFeature) {
        super(source);

        this.feature = feature;
        this.subFeature = subFeature;
    }

    /**
     * Return the feature.
     *
     * @return The feature.
     */
    public Feature getFeature() {
        return feature;
    }

    /**
     * Return the sub feature.
     *
     * @return The sub feature.
     */
    public Feature getSubFeature() {
        return subFeature;
    }
}