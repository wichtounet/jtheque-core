package org.jtheque.persistence.able;

import java.util.Collection;

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
public interface Dao<T extends Entity> {
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

    /**
     * Return all the entity managed by this dao.
     *
     * @return A List containing all the entities managed by the dao.
     */
    Collection<T> getAll();

    /**
     * Return the entity of a specific id.
     *
     * @param id The id for which we search the entity.
     * @return The Entity.
     */
    T get(int id);

    /**
     * Create an empty instance of the entity.
     *
     * @return An empty instance of the entity. 
     */
    T create();

    /**
     * Create the entity.
     *
     * @param entity The entity to create.
     */
    void create(T entity);

    /**
     * Save the entity.
     *
     * @param entity The entity to save.
     */
    void save(T entity);

    /**
     * Delete the entity.
     *
     * @param entity The entity to delete.
     * @return true if the object is deleted else false.
     */
    boolean delete(T entity);

    /**
     * Delete the entity with the specified ID.
     *
     * @param id The id of the entity to delete.
     * @return true if the entity has been deleted else false.
     */
    boolean delete(int id);

    /**
     * Return the table of this Dao.
     *
     * @return The table of this Dao.
     */
    String getTable();

    /**
     * Indicate if the simple entity exists or not.
     *
     * @param entity The entity to test for exists or not.
     *
     * @return <code>true</code> if the entity exists else <code>false</code>.
     */
    boolean exists(T entity);
}
