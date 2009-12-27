package org.jtheque.core.managers.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.jtheque.core.managers.AbstractActivableManager;
import org.jtheque.core.managers.AbstractManager;
import org.jtheque.core.managers.ManagerException;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.ILoggingManager;

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
public final class CacheManager extends AbstractActivableManager implements ICacheManager {
    private static final int TWO_MINUTES = 120;

    @Override
    public void preInit() {
        net.sf.ehcache.CacheManager manager = net.sf.ehcache.CacheManager.create();

        setDiskStorePath(manager, Managers.getCore().getFolders().getCacheFolder());
    }

    @Override
    public void close(){
        getCacheManager().shutdown();
    }

    /**
     * Return the EhCache CacheManager instance.
     *
     * @return The EhCache CacheManager instance.
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
                Managers.getCore().getFolders().getCacheFolder().getAbsolutePath(),
                config.isEternal(),
                config.getTimeToLiveSeconds(),
                config.getTimeToIdleSeconds(),
                config.isDiskPersistent(),
                TWO_MINUTES,
                null,
                null,
                config.getMaxElementsOnDisk(),
                20
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
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e, "Unable to set disk store path for cache");
        }
    }
}