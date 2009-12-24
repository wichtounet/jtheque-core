package org.jtheque.core.managers.cache;

import net.sf.ehcache.Cache;
import org.jtheque.core.managers.ActivableManager;

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
 * A cache manager specification.
 *
 * @author Baptiste Wicht
 */
public interface ICacheManager extends ActivableManager {
    /**
     * Add a cache.
     *
     * @param cacheConfiguration The configuration of the cache.
     */
    void addCache(CacheConfiguration cacheConfiguration);

    /**
     * Return the cache with the specified name.
     *
     * @param name The name of the cache.
     * @return The cache with the specified name.
     */
    Cache getCache(String name);

    /**
     * Indicate if a cache exists with the specified name or not.
     *
     * @param name The name of the cache to search for.
     * @return true if the cache exists else false.
     */
    boolean cacheExists(String name);

    /**
     * Remove the cache with the specified name.
     *
     * @param name The name of the cache to remove.
     */
    void removeCache(String name);
}
