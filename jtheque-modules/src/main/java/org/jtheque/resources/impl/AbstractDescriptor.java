package org.jtheque.resources.impl;

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
 * An abstract descriptor with only an id. This class is immutable.
 *
 * @author Baptiste Wicht
 */
public class AbstractDescriptor {
    private final String id;

    /**
     * Construct a new AbstractDescriptor.
     *
     * @param id The id of the descriptor.
     */
    public AbstractDescriptor(String id) {
        super();

        this.id = id;
    }

    /**
     * Return the id of the resource.
     *
     * @return The id of the resource.
     */
    public String getId() {
        return id;
    }
}
