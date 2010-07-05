package org.jtheque.core.able;

import org.jtheque.utils.bean.Version;

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
 * A Versionable object.
 *
 * @author Baptiste Wicht
 */
public interface Versionable {
    /**
     * Return the current version of the object.
     *
     * @return The current version of the object. 
     */
    Version getVersion();

    /**
     * Return the URL of the descriptor.
     *
     * @return The URL of the descriptor. 
     */
    String getDescriptorURL();
}