package org.jtheque.features.able;

import org.jtheque.i18n.able.Internationalizable;

import java.util.Collection;

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
 * A menu. Namely, it's a container for features. It represent the menu at the level of a module. This container has
 * main features and features for each of the core features.
 *
 * @author Baptiste Wicht
 */
public interface Menu extends Internationalizable {
    /**
     * Return all the main features of this menu.
     *
     * @return A Collection containing all the main features of this menu.
     */
    Collection<Feature> getMainFeatures();

    /**
     * Return all the sub features of this menu for the specified CoreFeature.
     *
     * @param feature The core feature.
     *
     * @return A Collection containing all the sub features of this core feature.
     */
    Collection<Feature> getSubFeatures(CoreFeature feature);
}