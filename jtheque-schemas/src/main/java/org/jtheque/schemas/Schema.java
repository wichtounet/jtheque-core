package org.jtheque.schemas;

import org.jtheque.utils.bean.Version;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
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
