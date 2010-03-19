package org.jtheque.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.jtheque.core.ICore;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.lang.reflect.Field;

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
 * A cache manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class CacheService implements ICacheService {
    private static final long TWO_MINUTES = 120;

    private final ICore core;

    public CacheService(ICore core) {
        super();

        this.core = core;
    }

    @PostConstruct
    public void preInit() {
        net.sf.ehcache.CacheManager manager = net.sf.ehcache.CacheManager.create();

        setDiskStorePath(manager, core.getFolders().getCacheFolder());
    }

    @PreDestroy
    public void close(){
        getCacheManager().shutdown();
    }

    /**
     * Return the EhCache CacheService instance.
     *
     * @return The EhCache CacheService instance.
     */
    private static net.sf.ehcache.CacheManager getCacheManager() {
        return net.sf.ehcache.CacheManager.getInstance();
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