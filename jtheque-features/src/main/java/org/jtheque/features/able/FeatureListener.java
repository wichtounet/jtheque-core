package org.jtheque.features.able;

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
     * A feature has been modified. This indicate that a sub-feature has been added or removed.
     *
     * @param feature The feature.
     */
    void featureModified(Feature feature);
}