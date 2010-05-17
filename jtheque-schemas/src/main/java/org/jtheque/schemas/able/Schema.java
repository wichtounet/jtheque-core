package org.jtheque.schemas.able;

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
 * A Schema of database.
 *
 * @author Baptiste Wicht
 */
public interface Schema extends Comparable<Schema> {
    /**
     * Return the version of the schema.
     *
     * @return The version of the schema.
     * @see Version
     */
    Version getVersion();

    /**
     * Return the name of the schema.
     *
     * @return The name of the schema.
     */
    String getId();

    /**
     * Return all the dependencies of the schema.
     *
     * @return An array containing all the dependencies of the schema.
     */
    String[] getDependencies();

    /**
     * Install the schema. It seems nothing was already installed.
     */
    void install();

    /**
     * Update the schema from an another version.
     *
     * @param from The installed version.
     * @see Version
     */
    void update(Version from);
}
