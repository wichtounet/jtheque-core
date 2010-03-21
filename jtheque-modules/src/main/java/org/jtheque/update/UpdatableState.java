package org.jtheque.update;

import org.jtheque.states.AbstractState;
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
 * A state for the updatables versions.
 *
 * @author Baptiste Wicht
 */
//Must be public for StateService
public final class UpdatableState extends AbstractState {
    /**
     * Return the version of the updatable.
     *
     * @param name The name of the updatable.
     * @return The version of the updatable.
     */
    public Version getVersion(String name) {
        String property = getProperty(name, "null");

        if ("null".equals(property)) {
            return null;
        }

        return new Version(property);
    }

    /**
     * Set the version of the updatable.
     *
     * @param name    The name of the updatable.
     * @param version The version of the updatable.
     */
    public void setVersion(String name, Version version) {
        setProperty(name, version.getVersion());
    }
}