package org.jtheque.cache.impl;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.jtheque.cache.able.ICacheService;
import org.jtheque.core.able.ICore;
import org.slf4j.LoggerFactory;

import org.jtheque.cache.able.CacheConfiguration;

import javax.annotation.PreDestroy;
import java.io.File;
import java.lang.reflect.Field;

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
 * A cache manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class CacheService implements ICacheService {
    private static final long TWO_MINUTES = 120;

    private final ICore core;

    /**
     * Create a new CacheService.
     *
     * @param core The core.
     */
    public CacheService(ICore core) {
        super();

        this.core = core;

        CacheManager manager = CacheManager.create();

        setDiskStorePath(manager, core.getFolders().getCacheFolder());
    }

    /**
     * Close the cache.
     */
    @PreDestroy
    public void close(){
        getCacheManager().shutdown();
    }

    /**
     * Return the EhCache CacheService instance.
     *
     * @return The EhCache CacheService instance.
     */
    private static CacheManager getCacheManager() {
        return CacheManager.getInstance();
    }

    @Override
    public void addCache(CacheConfiguration config) {
        Cache cache = new Cache(
                config.getName(),
                config.getMaxElementsInMemory(),
                MemoryStoreEvictionPolicy.LFU,
                config.isOverflowToDisk(),
                core.getFolders().getCacheFolder().getAbsolutePath(),
                config.isEternal(),
                config.getTimeToLiveSeconds(),
                config.getTimeToIdleSeconds(),
                config.isDiskPersistent(),
                TWO_MINUTES,
                null
        );

        getCacheManager().addCache(cache);
    }

    @Override
    public Cache getCache(String name) {
        return getCacheManager().getCache(name);
    }

    @Override
    public boolean cacheExists(String name) {
        return getCacheManager().cacheExists(name);
    }

    @Override
    public void removeCache(String name) {
        getCacheManager().removeCache(name);
    }

    /**
     * Set the disk store path of the caches by reflection.
     *
     * @param manager     The manager to configure.
     * @param cacheFolder The folder of the caches.
     */
    private void setDiskStorePath(Object manager, File cacheFolder) {
        try {
            Field m = manager.getClass().getDeclaredField("diskStorePath");

            m.setAccessible(true);

            m.set(manager, cacheFolder.getAbsolutePath());
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error("Unable to set disk store path for cache", e);
        }
    }
}