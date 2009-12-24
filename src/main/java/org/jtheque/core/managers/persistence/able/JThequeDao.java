package org.jtheque.core.managers.persistence.able;

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
 * A JTheque DAO.
 *
 * @author Baptiste Wicht
 */
public interface JThequeDao {
    /**
     * Clear all the content of the Dao. All the instances will be removed
     * and all the physical data too.
     */
    void clearAll();

    /**
     * Add a data listener to dao.
     *
     * @param listener The listener to add.
     */
    void addDataListener(DataListener listener);

    /**
     * Remove a data listener from the dao.
     *
     * @param listener The listener to remove.
     */
    void removeDataListener(DataListener listener);
}
