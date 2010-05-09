package org.jtheque.persistence;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
