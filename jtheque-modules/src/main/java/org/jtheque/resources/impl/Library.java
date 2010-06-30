package org.jtheque.resources.impl;

import org.osgi.framework.Bundle;

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

public class Library {
    private String id;
    private Bundle bundle;

    public Library(String id) {
        super();

        this.id = id;
    }

    /**
     * Return the id of the library.
     *
     * @return The id of the library.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id of the library.
     *
     * @param id The id of the library.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Return the bundle of the library.
     *
     * @return The bundle of the library.
     */
    public Bundle getBundle() {
        return bundle;
    }

    /**
     * Set the bundle of the library.
     *
     * @param bundle The bundle of the library.
     */
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}