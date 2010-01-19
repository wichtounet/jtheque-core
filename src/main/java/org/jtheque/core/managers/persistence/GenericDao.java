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

import org.jdesktop.swingx.event.WeakEventListenerList;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.core.managers.persistence.able.Entity;
import org.jtheque.core.managers.persistence.able.JThequeDao;
import org.jtheque.core.managers.persistence.context.IDaoPersistenceContext;
import org.jtheque.utils.collections.CollectionUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

/**
 * A generic data access object.
 *
 * @author Baptiste Wicht
 * @param <T> The class managed by the dao.
 */
public abstract class GenericDao<T extends Entity> implements JThequeDao {
    @Resource
    private IDaoPersistenceContext persistenceContext;

    private final String table;

    private final Map<Integer, T> cache;

    private boolean cacheEntirelyLoaded;

    private final WeakEventListenerList listenerList = new WeakEventListenerList();

    /**
     * Construct a new GenericDao.
     *
     * @param table The database Table.
     */
    protected GenericDao(String table) {
        super();

        this.table = table;

        cache = new CacheHashMap<Integer, T>(50);
    }

    /**
     * Return all the entity managed by this dao.
     *
     * @return A List containing all the entities managed by the dao.
     */
    protected final Collection<T> getAll() {
        load();

        return CollectionUtils.copyOf(cache.values());
    }

    /**
     * Return the cache of the DAO.
     *
     * @return The cache of the DAO.
     */
    protected final Map<Integer, T> getCache() {
        return cache;
    }

    /**
     * Load the DAO.
     */
    protected final void load() {
        if (!cacheEntirelyLoaded) {
            loadCache();
        }
    }

    /**
     * Load the cache.
     */
    protected abstract void loadCache();

    /**
     * Load the entity.
     *
     * @param i The ID of the entity.
     */
    protected abstract void load(int i);

    /**
     * Return the row mapper for the DAO.
     *
     * @return The row mapper for the DAO.
     */
    protected abstract ParameterizedRowMapper<T> getRowMapper();

    /**
     * Return the query mapper for the DAO.
     *
     * @return The query mapper for the DAO.
     */
    protected abstract QueryMapper getQueryMapper();

    /**
     * Return the entity of a specific id.
     *
     * @param id The id for which we search the entity.
     * @return The Entity.
     */
    protected final T get(int id) {
        if (isNotInCache(id)) {
            load(id);
        }

        return cache.get(id);
    }

    /**
     * Create the entity.
     *
     * @param entity The entity to create.
     */
    public void create(T entity) {
        persistenceContext.saveOrUpdate(entity, getQueryMapper());

        cache.put(entity.getId(), entity);

        fireDataChanged();
    }

    /**
     * Save the entity.
     *
     * @param entity The entity to save.
     */
    public void save(T entity) {
        persistenceContext.saveOrUpdate(entity, getQueryMapper());

        fireDataChanged();
    }

    /**
     * Delete the entity.
     *
     * @param entity The entity to delete.
     * @return true if the object is deleted else false.
     */
    public boolean delete(T entity) {
        boolean deleted = persistenceContext.delete(table, entity);

        if (deleted) {
            cache.remove(entity.getId());

            fireDataChanged();
        }

        return deleted;
    }

    /**
     * Delete the entity with the specified ID.
     *
     * @param id The id of the entity to delete.
     * @return true if the entity has been deleted else false.
     */
    public boolean delete(int id) {
        boolean deleted = persistenceContext.delete(table, id);

        if (deleted) {
            fireDataChanged();

            cache.remove(id);
        }

        return deleted;
    }

    /**
     * Delete all the entities.
     */
    @Override
    public final void clearAll() {
        persistenceContext.deleteAll(table);

        cache.clear();
    }

    /**
     * Indicate if the entity with the ID is in cache.
     *
     * @param i The ID of the entity.
     * @return true if an entity with the ID is in cache.
     */
    protected final boolean isNotInCache(int i) {
        return !cache.containsKey(i);
    }

    /**
     * Indicate if the cache is entirely loaded.
     *
     * @return true if the cache is entirely loaded else false.
     */
    final boolean isCacheEntirelyLoaded() {
        return cacheEntirelyLoaded;
    }

    /**
     * Set if the cache has been entirely loaded or not.
     */
    protected final void setCacheEntirelyLoaded() {
        cacheEntirelyLoaded = true;
    }

    @Override
    public final void addDataListener(DataListener listener) {
        listenerList.add(DataListener.class, listener);
    }

    @Override
    public final void removeDataListener(DataListener listener) {
        listenerList.remove(DataListener.class, listener);
    }

    /**
     * Avert the listeners that the data have changed.
     */
    final void fireDataChanged() {
        DataListener[] listeners = listenerList.getListeners(DataListener.class);

        for (DataListener listener : listeners) {
            listener.dataChanged();
        }
    }
}