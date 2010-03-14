package org.jtheque.persistence;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
 * Simple HashMap who doesn't support null value.
 *
 * @author Baptiste Wicht
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 */
final class CacheHashMap<K, V> extends HashMap<K, V> {
    /**
     * Create a new CacheHashMap.
     *
     * @param initialCapacity The initial capacity of the map.
     */
    CacheHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public V put(K key, V value) {
        if (value == null) {
            return null;
        }

        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        super.putAll(m);

        if (containsValue(null)) {
            clearNullValues();
        }
    }

    /**
     * Clear all entries who contains null values.
     */
    private void clearNullValues() {
        Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<K, V> entry = iterator.next();

            if (entry.getValue() == null) {
                iterator.remove();
            }
        }
    }
}
