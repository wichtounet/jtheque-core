package org.jtheque.persistence.utils;

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

import org.jtheque.persistence.able.Entity;

import java.util.Collection;

/**
 * A generic data access object.
 *
 * @author Baptiste Wicht
 * @param <T> The class managed by the dao.
 */
public abstract class SimpleJDBCDao<T extends Entity> extends AbstractDao<T> {
    /**
     * Construct a new CachedJDBCDao.
     *
     * @param table The database Table.
     */
    protected SimpleJDBCDao(String table) {
        super(table);
    }

    @Override
    public final Collection<T> getAll() {
        return getContext().getSortedList(getTable(), getRowMapper());
    }

    @Override
    public final T get(int id) {
        return getContext().getDataByID(getTable(), id, getRowMapper());
    }

    @Override
    public void create(T entity) {
        getContext().saveOrUpdate(entity, getQueryMapper());

        fireDataChanged();
    }

    @Override
    public void save(T entity) {
        getContext().saveOrUpdate(entity, getQueryMapper());

        fireDataChanged();
    }

    @Override
    public boolean delete(T entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        boolean deleted = getContext().delete(getTable(), id);

        if (deleted) {
            fireDataChanged();
        }

        return deleted;
    }

    /**
     * Delete all the entities.
     */
    @Override
    public final void clearAll() {
        getContext().deleteAll(getTable());
    }
}