package org.jtheque.resources;

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
 * A resource of a module. That kind of resource can be installed.
 *
 * @author Baptiste Wicht
 */
public interface Resource {
    /**
     * Return the id of the resource.
     *
     * @return The id of the resource.
     */
    String getId();

    /**
     * Return the version of the resource.
     *
     * @return The version of the resource.
     */
    Version getVersion();

    /**
     * Return the URL of the resource.
     *
     * @return The URL of the resource.
     */
    String getUrl();

    /**
     * Return the file of the resource. This is only the file name not the path.
     *
     * @return The file of the resource.
     */
    String getFile();

    /**
     * Indicate if the resource is a library or not.
     *
     * @return {@code true} if the resource is a library else {@code false}.
     */
    boolean isLibrary();
}