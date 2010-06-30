package org.jtheque.modules.utils;

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
 * An image resource.
 *
 * @author Baptiste Wicht
 */
public class ImageResource {
    private final String name;
    private final String resource;

    /**
     * Construct  a new ImageResource.
     *
     * @param name     The name of the description.
     * @param resource The image resource.
     */
    public ImageResource(String name, String resource) {
        super();

        this.name = name;
        this.resource = resource;
    }

    /**
     * Return the name of the image.
     *
     * @return The name of the image.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the resource.
     *
     * @return The resource.
     */
    public String getResource() {
        return resource;
    }
}