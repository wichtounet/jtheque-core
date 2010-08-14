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

import org.jtheque.persistence.Entity;
import org.jtheque.utils.annotations.GuardedBy;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

/**
 * A generic data access object.
 *
 * @author Baptiste Wicht
 * @param <T> The class managed by the dao.
 */
@ThreadSafe
public abstract class CachedJDBCDao<T extends Entity> extends AbstractDao<T> {
    @GuardedInternally
    private final ConcurrentMap<Integer, T> cache = CollectionUtils.newConcurrentMap(50);

    @GuardedBy("this")
    private volatile boolean cacheEntirelyLoaded;

    private final Object clearAtomicityLock = new Object();
    private final Object deleteAtomicityLock = new Object();
    private final Object saveAtomicityLock = new Object();

    /**
     * Construct a new CachedJDBCDao.
     *
     * @param table The database Table.
     */
    protected CachedJDBCDao(String table) {
        super(table);
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
    protected final ConcurrentMap<Integer, T> getCache() {
        return cache;
    }

    /**
     * Load the DAO. Use the double-check locking idiom to load the cache if necessary only.
     */
    protected final void load() {
        boolean loaded = cacheEntirelyLoaded;

        if (!loaded) {
            synchronized (this) {
                loaded = cacheEntirelyLoaded;

                if (!loaded) {
                    loadCache();

                    cacheEntirelyLoaded = true;
                }
            }
        }
    }

    /**
     * Load the cache. Will only be called only. 
     */
    protected abstract void loadCache();

    /**
     * Fill the cache using the row mapper and the table name.
     */
    protected void defaultFillCache() {
        Collection<T> collections = getContext().getSortedList(getTable(), getRowMapper());

        for (T entity : collections) {
            cache.put(entity.getId(), entity);
        }
    }

    @Override
    public final T get(int id) {
        load();

        return cache.get(id);
    }

    @Override
    public boolean exists(int id) {
        load();

        return cache.containsKey(id);
    }

    @Override
    public boolean exists(T entity) {
        return exists(entity.getId());
    }

    @Override
    public void save(T entity) {
        synchronized (saveAtomicityLock) {
            getContext().saveOrUpdate(entity, getQueryMapper());

            cache.putIfAbsent(entity.getId(), entity);
        }

        fireDataChanged();
    }

    @Override
    public boolean delete(T entity) {
        return delete(entity.getId());
    }

    @Override
    public boolean delete(int id) {
        boolean deleted;

        synchronized (deleteAtomicityLock) {
            deleted = getContext().delete(getTable(), id);

            if (deleted) {
                cache.remove(id);
            }
        }

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
        synchronized (clearAtomicityLock) {
            getContext().deleteAll(getTable());

            cache.clear();
        }

        fireDataChanged();
    }

    /**
     * Indicate if the entity with the ID is in cache.
     *
     * @param i The ID of the entity.
     *
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
}