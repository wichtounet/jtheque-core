package org.jtheque.core.managers.persistence;

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

import org.jtheque.core.managers.ActivableManager;

/**
 * A persistence manager. It seems the manager who's responsible for the persistence of the work entities. This
 * manager provide methods to manage entities and to open secondary connection.
 *
 * @author Baptiste Wicht
 */
public interface IPersistenceManager extends ActivableManager {
    /**
     * Clear the database.
     */
    void clearDatabase();
}