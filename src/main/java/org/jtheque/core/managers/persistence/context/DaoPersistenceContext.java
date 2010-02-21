package org.jtheque.core.managers.persistence.context;

import org.jtheque.core.managers.persistence.Query;
import org.jtheque.core.managers.persistence.QueryMapper;
import org.jtheque.core.managers.persistence.able.Entity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
 * The persistence context for DAOs.
 *
 * @author Baptiste Wicht
 */
public final class DaoPersistenceContext implements IDaoPersistenceContext {
    @Resource
    private SimpleJdbcTemplate jdbcTemplate;

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

            return jdbcTemplate.update(query.getSqlQuery(), query.getParameters()) > 0;
        } else {
            Query query = mapper.constructInsertQuery(entity);

            int inserts = jdbcTemplate.update(query.getSqlQuery(), query.getParameters());

            entity.setId(jdbcTemplate.queryForInt("SELECT IDENTITY()"));

            return inserts > 0;
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