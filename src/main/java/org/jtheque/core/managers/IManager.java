package org.jtheque.core.managers;

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
 * A manager module. It's a core module that manage a specific part of JTheque. All the managers are managed by
 * the Managers class.
 *
 * @author Baptiste Wicht
 */
public interface IManager {
    /**
     * Pre-init the manager. This operation is called before modules pre plug.
     */
    void preInit();

    /**
     * Init the manager. This operation is called after modules pre plug but before module plug.
     *
     * @throws ManagerException If an error occurs during the init process.
     */
    void init() throws ManagerException;

    /**
     * Close the manager.
     */
    void close();
}