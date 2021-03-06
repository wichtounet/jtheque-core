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

import org.jtheque.utils.annotations.ThreadSafe;

import java.util.Collection;

/**
 * A feature manager specification.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public interface FeatureService {
    /**
     * Add a menu to the application. It's an object who contains some features that will be added to the menu bar. If
     * the module id is provided, this menu will be automatically removed on the module stop.
     *
     * @param moduleId The id of the module who did the add.
     * @param menu     The menu to add to the application.
     */
    void addMenu(String moduleId, Menu menu);

    /**
     * Return all the features of the application. This is only the features of first level.
     *
     * @return A Collection containing all the features. This collection is unmodifiable.
     */
    Collection<Feature> getFeatures();

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