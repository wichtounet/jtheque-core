package org.jtheque.core.managers.persistence.context;

import org.jtheque.core.managers.persistence.QueryMapper;
import org.jtheque.core.managers.persistence.able.Entity;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

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
 * A dao persistence context specification.
 *
 * @author Baptiste Wicht
 */
public interface IDaoPersistenceContext {
    /**
     * Return all the entities of a certain class.
     *
     * @param <T>    The entity class type.
     * @param table  The entity table.
     * @param mapper The row mapper.
     * @return A List sorted by entity displayable text containing all the entity of the class.
     */
    <T extends Entity> Collection<T> getSortedList(String table, ParameterizedRowMapper<T> mapper);

    /**
     * Return an entity of a specific ID.
     *
     * @param <T>    The entity class type.
     * @param id     The searched id.
     * @param table  The entity table.
     * @param mapper The row mapper.
     * @return The entity.
     */
    <T extends Entity> T getDataByID(String table, int id, ParameterizedRowMapper<T> mapper);

    /**
     * Delete an entity of a specific ID.
     *
     * @param id    The searched id.
     * @param table The entity table.
     * @return true if the object is deleted else false.
     */
    boolean delete(String table, int id);

    /**
     * Delete an Entity.
     *
     * @param d     The entity to delete.
     * @param table The entity table.
     * @return true if the object is deleted else false.
     */
    boolean delete(String table, Entity d);

    /**
     * Save or update an entity.
     *
     * @param entity The entity to save or update.
     * @param mapper The query mapper.
     * @return true if the object is saved else false.
     * @throws IllegalArgumentException if entity is null.
     */
    boolean saveOrUpdate(Entity entity, QueryMapper mapper);

    /**
     * Delete all the entities of a certain class.
     *
     * @param table The entity table.
     */
    void deleteAll(String table);
}