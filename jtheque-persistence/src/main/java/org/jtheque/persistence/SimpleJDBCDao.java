package org.jtheque.persistence;

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