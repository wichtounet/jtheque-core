package org.jtheque.persistence;

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
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * A generic data access object.
 *
 * @author Baptiste Wicht
 * @param <T> The class managed by the dao.
 */
public abstract class CachedJDBCDao<T extends Entity> extends AbstractDao<T> {
    private final Map<Integer, T> cache;

    private boolean cacheEntirelyLoaded;

    /**
     * Construct a new CachedJDBCDao.
     *
     * @param table The database Table.
     */
    protected CachedJDBCDao(String table) {
        super(table);

        cache = new CacheHashMap<Integer, T>(50);
    }

    @Override
    public final Collection<T> getAll() {
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

    @Override
    public final T get(int id) {
        if (isNotInCache(id)) {
            load(id);
        }

        return cache.get(id);
    }

    @Override
    public void create(T entity) {
        getContext().saveOrUpdate(entity, getQueryMapper());

        cache.put(entity.getId(), entity);

        fireDataChanged();
    }

    @Override
    public void save(T entity) {
        getContext().saveOrUpdate(entity, getQueryMapper());

        fireDataChanged();
    }

    @Override
    public boolean delete(T entity) {
        boolean deleted = getContext().delete(getTable(), entity);

        if (deleted) {
            cache.remove(entity.getId());

            fireDataChanged();
        }

        return deleted;
    }

    @Override
    public boolean delete(int id) {
        boolean deleted = getContext().delete(getTable(), id);

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
        getContext().deleteAll(getTable());

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
    protected final boolean isCacheEntirelyLoaded() {
        return cacheEntirelyLoaded;
    }

    /**
     * Set if the cache has been entirely loaded or not.
     */
    protected final void setCacheEntirelyLoaded() {
        cacheEntirelyLoaded = true;
    }

}