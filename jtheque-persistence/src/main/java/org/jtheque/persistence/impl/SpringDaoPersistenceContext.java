package org.jtheque.persistence.impl;

import org.jtheque.persistence.DaoPersistenceContext;
import org.jtheque.persistence.Entity;
import org.jtheque.persistence.Query;
import org.jtheque.persistence.QueryMapper;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
 * The persistence context for DAOs. This persistence context use a JDBC Template from Spring to perform operations on
 * the database.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class SpringDaoPersistenceContext implements DaoPersistenceContext {
    @GuardedInternally
    private final SimpleJdbcTemplate jdbcTemplate;

    private final Object idCoherencyLock = new Object();

    /**
     * Construct a new SpringDaoPersistenceContext.
     *
     * @param jdbcTemplate The JDBC template to use.
     */
    public SpringDaoPersistenceContext(SimpleJdbcTemplate jdbcTemplate) {
        super();

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T extends Entity> Collection<T> getSortedList(String table, RowMapper<T> mapper) {
        List<T> data = jdbcTemplate.query("SELECT * FROM " + table, mapper);

        Collections.sort(data);

        return data;
    }

    @Override
    public <T extends Entity> T getDataByID(String table, int id, RowMapper<T> mapper) {
        List<T> results = jdbcTemplate.query("SELECT * FROM " + table + " WHERE ID = ?", mapper, id);

        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    @Override
    public boolean delete(String table, int id) {
        return jdbcTemplate.update("DELETE FROM " + table + " WHERE ID = ?", id) > 0;
    }

    @Override
    public boolean delete(String table, Entity d) {
        return delete(table, d.getId());
    }

    @Override
    public boolean saveOrUpdate(Entity entity, QueryMapper mapper) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        if (entity.isSaved()) {
            Query query = mapper.constructUpdateQuery(entity);

            return jdbcTemplate.update(query.getSQLQuery(), query.getParameters()) > 0;
        } else {
            Query query = mapper.constructInsertQuery(entity);

            //Must be atomic to have the good ID
            synchronized (idCoherencyLock) {
                int inserts = jdbcTemplate.update(query.getSQLQuery(), query.getParameters());

                entity.setId(jdbcTemplate.queryForInt("SELECT IDENTITY()"));

                return inserts > 0;
            }
        }
    }

    @Override
    public void deleteAll(String table) {
        jdbcTemplate.update("DELETE FROM " + table);
    }

    @Override
    public SimpleJdbcTemplate getTemplate() {
        return jdbcTemplate;
    }
}