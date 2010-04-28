package org.jtheque.schemas;

import org.jtheque.states.AbstractState;
import org.jtheque.states.State;
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
@State(id = "jtheque-schema-configuration")
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
}