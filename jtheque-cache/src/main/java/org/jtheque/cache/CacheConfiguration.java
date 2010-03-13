package org.jtheque.cache;

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
 * A cache configuration. It seems a configuration of an EHCache.
 *
 * @author Baptiste Wicht
 */
final class CacheConfiguration {
    private final String name;
    private int maxElementsInMemory;
    private boolean eternal;
    private boolean overflowToDisk;
    private boolean diskPersistent;
    private long timeToIdleSeconds;
    private long timeToLiveSeconds;

    private static final int DEFAULT_MAX_ELEMENTS_IN_MEMORY = 1000;

    /**
     * Construct a new CacheConfiguration.
     *
     * @param name     The name of the cache.
     * @param defaults Indicate if we must use the defaults values or not.
     */
    CacheConfiguration(String name, boolean defaults) {
        super();

        this.name = name;

        if (defaults) {
            maxElementsInMemory = DEFAULT_MAX_ELEMENTS_IN_MEMORY;
            eternal = false;
            overflowToDisk = false;
        }
    }

    /**
     * Return the maximum number of elements to keep in memory.
     *
     * @return The maximum number of elements to keep in memory.
     */
    public int getMaxElementsInMemory() {
        return maxElementsInMemory;
    }

    /**
     * Set the maximum number of elements to keep in memory.
     *
     * @param maxElementsInMemory The maximum number elements to keep in memory.
     */
    public void setMaxElementsInMemory(int maxElementsInMemory) {
        this.maxElementsInMemory = maxElementsInMemory;
    }

    /**
     * Indicate if the cache is eternal or not.
     *
     * @return true if the cache is eternal else false.
     */
    public boolean isEternal() {
        return eternal;
    }

    /**
     * Set if the cache is eternal or not.
     *
     * @param eternal A boolean flag indicating if the cache is eternal or not.
     */
    public void setEternal(boolean eternal) {
        this.eternal = eternal;
    }

    /**
     * Indicate if the cache must put the overflow to the disk or not.
     *
     * @return true if the cache put the overflow to the disk else false.
     */
    public boolean isOverflowToDisk() {
        return overflowToDisk;
    }

    /**
     * Set if overflow must go to the disk or not.
     *
     * @param overflowToDisk A boolean flag indicating if the overflow must go to disk.
     */
    public void setOverflowToDisk(boolean overflowToDisk) {
        this.overflowToDisk = overflowToDisk;
    }

    /**
     * Return the time after which we must idle the elements.
     *
     * @return The time after which we must idle the elements.
     */
    public long getTimeToIdleSeconds() {
        return timeToIdleSeconds;
    }

    /**
     * Set the time after which we must idle the elements.
     *
     * @param timeToIdleSeconds The image after which we must idle the elements.
     */
    public void setTimeToIdleSeconds(long timeToIdleSeconds) {
        this.timeToIdleSeconds = timeToIdleSeconds;
    }

    /**
     * Return the time to live of the elements.
     *
     * @return The time to live of elements.
     */
    public long getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }

    /**
     * Set the time to live of the elements.
     *
     * @param timeToLiveSeconds The time to live of the elements.
     */
    public void setTimeToLiveSeconds(long timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
    }

    /**
     * Indicate if the disk is persistent or not.
     *
     * @return true if the disk store is persistent else false.
     */
    public boolean isDiskPersistent() {
        return diskPersistent;
    }

    /**
     * Set if the disk store is persistent else false.
     *
     * @param diskPersistent A boolean flag indicating if the disk store has to be persistent or not.
     */
    public void setDiskPersistent(boolean diskPersistent) {
        this.diskPersistent = diskPersistent;
    }

    /**
     * Return the name of the cache.
     *
     * @return The name of the cache.
     */
    public String getName() {
        return name;
    }
}