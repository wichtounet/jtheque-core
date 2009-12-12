package org.jtheque.core.managers.state;

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

import org.jtheque.core.managers.ManagerException;

/**
 * An exception who occurs during the configuration execution.
 *
 * @author Baptiste Wicht
 */
public final class ConfigException extends ManagerException {
    private static final long serialVersionUID = -6574237262395076661L;

    /**
     * Construct a new ConfigException.
     *
     * @param e The parent exception.
     */
    public ConfigException(Throwable e) {
        super(e);
    }
}