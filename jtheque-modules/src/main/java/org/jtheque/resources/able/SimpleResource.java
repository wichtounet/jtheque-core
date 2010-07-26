package org.jtheque.resources.able;

import org.osgi.framework.BundleContext;

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
 * A simple resource.
 *
 * @author Baptiste Wicht
 */
public interface SimpleResource {
    /**
     * Return the id of the resource.
     *
     * @return The id of the resource. 
     */
    String getId();

    /**
     * Install the resource in the bundle context. Depending on the implementation, this method
     * can do nothing.
     *
     * @param bundleContext The bundle context.
     */
    void install(BundleContext bundleContext);
}