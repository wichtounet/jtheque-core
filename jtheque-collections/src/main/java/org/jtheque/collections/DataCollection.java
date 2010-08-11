package org.jtheque.collections;

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

import org.jtheque.persistence.Entity;

/**
 * A collection specification.
 *
 * @author Baptiste Wicht
 */
public interface DataCollection extends Entity {
    /**
     * Set the title of the collection
     *
     * @param title The collection's title.
     */
    void setTitle(String title);

    /**
     * Return the title of the collection.
     *
     * @return The title of the collection.
     */
    String getTitle();

    /**
     * Indicate if the collection is password protected or not.
     *
     * @return true if the collection is password protected else false.
     */
    boolean isProtection();

    /**
     * Set a boolean flag indicating if the collection is password-protected or not.
     *
     * @param protection A boolean flag indicating if the collection is password-protected or not.
     */
    void setProtection(boolean protection);

    /**
     * Return the password of the collection encrypted with SHA-256.
     *
     * @return The encrypted password.
     */
    String getPassword();

    /**
     * Set the password of the collection.
     *
     * @param password The password of the collection.
     */
    void setPassword(String password);
}
