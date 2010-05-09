package org.jtheque.cache;

import net.sf.ehcache.Cache;

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
 * A cache manager specification.
 *
 * @author Baptiste Wicht
 */
public interface ICacheService {
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
