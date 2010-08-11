package org.jtheque.persistence;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

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
 * A simple persistence context for the DAO. This class is an helper providing generic database operations.
 *
 * @author Baptiste Wicht
 */
public interface DaoPersistenceContext {
    /**
     * Return all the entities of a certain class.
     *
     * @param <T>    The entity class type.
     * @param table  The entity table.
     * @param mapper The row mapper.
     *
     * @return A List sorted by entity displayable text containing all the entity of the class.
     */
    <T extends Entity> Collection<T> getSortedList(String table, RowMapper<T> mapper);

    /**
     * Return an entity of a specific ID.
     *
     * @param <T>    The entity class type.
     * @param id     The searched id.
     * @param table  The entity table.
     * @param mapper The row mapper.
     *
     * @return The entity.
     */
    <T extends Entity> T getDataByID(String table, int id, RowMapper<T> mapper);

    /**
     * Delete an entity of a specific ID.
     *
     * @param id    The searched id.
     * @param table The entity table.
     *
     * @return true if the object is deleted else false.
     */
    boolean delete(String table, int id);

    /**
     * Delete an Entity.
     *
     * @param d     The entity to delete.
     * @param table The entity table.
     *
     * @return true if the object is deleted else false.
     */
    boolean delete(String table, Entity d);

    /**
     * Save or update an entity.
     *
     * @param entity The entity to save or update.
     * @param mapper The query mapper.
     *
     * @return true if the object is saved else false.
     *
     * @throws IllegalArgumentException if entity is null.
     */
    boolean saveOrUpdate(Entity entity, QueryMapper mapper);

    /**
     * Delete all the entities of a certain class.
     *
     * @param table The entity table.
     */
    void deleteAll(String table);

    /**
     * Return the spring JDBC template.
     *
     * @return The Spring JDBC template.
     */
    SimpleJdbcTemplate getTemplate();
}