package org.jtheque.core.utils;

import java.util.HashMap;
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
 * A simple properties cache.
 *
 * @author Baptiste Wicht
 */
public final class SimplePropertiesCache {
    private static final Map<String, Object> PROPERTIES = new HashMap<String, Object>(5);

    /**
     * Utility class, not instantiable.
     */
    private SimplePropertiesCache() {
        throw new AssertionError();
    }

    /**
     * Put the property.
     *
     * @param key   The key of the property.
     * @param value The value of the property.
     */
    public static void put(String key, Object value) {
        PROPERTIES.put(key, value);
    }

    /**
     * Return the value of the given property key.
     *
     * @param key The key of the property to search.
     * @param type The type of resource. 
     * @param <T> The type of resource.
     *
     * @return The value of the property.
     */
    public static <T> T get(String key, Class<T> type) {
        return type.cast(PROPERTIES.get(key));
    }
}