package org.jtheque.persistence.able;

import java.util.Collection;

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
