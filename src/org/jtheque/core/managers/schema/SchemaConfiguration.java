package org.jtheque.core.managers.schema;

import org.jtheque.core.managers.state.AbstractState;
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
 * The configuration of the schemas.
 *
 * @author Baptiste Wicht
 */
//Must be public for StateManager
public final class SchemaConfiguration extends AbstractState {
    /**
     * Return the version of the schema.
     *
     * @param name The name of the schema.
     * @return The version of the schema.
     */
    public Version getVersion(String name) {
        String property = getProperty(name + "-version", "null");

        if ("null".equals(property)) {
            return null;
        }

        return new Version(property);
    }

    /**
     * Set the version of the schema.
     *
     * @param name    The name of the schema.
     * @param version The version of the schema.
     */
    public void setVersion(String name, Version version) {
        setProperty(name + "-version", version.getVersion());
    }

    /**
     * Indicate if the schema has been recovered or not.
     *
     * @param name The name of the schema.
     * @return true if the schema has been recovered else false.
     */
    public boolean isNotRecovered(String name) {
        return !Boolean.parseBoolean(getProperty(name + "-recovered", "false"));
    }

    /**
     * Mark the schema as recovered.
     *
     * @param name The name of the schema.
     */
    public void setRecovered(String name) {
        setProperty(name + "-recovered", "true");
    }
}