package org.jtheque.modules.utils;

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

import org.jtheque.utils.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A cache for the resource of the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleResourceCache {
    private static final Map<String, Map<Class<?>, Set<Object>>> CACHE = new HashMap<String, Map<Class<?>, Set<Object>>>(8);

    /**
     * Utility class, not instantiable.
     */
    private ModuleResourceCache() {
        super();
    }

    /**
     * Add the resources to the cache.
     *
     * @param id           The id of the module.
     * @param resourceType The type of resource.
     * @param resource     The resource to add.
     * @param <T>          The type of resource.
     */
    public static <T> void addResource(String id, Class<T> resourceType, T resource) {
        if (StringUtils.isNotEmpty(id)) {
            if (!CACHE.containsKey(id)) {
                CACHE.put(id, new HashMap<Class<?>, Set<Object>>(8));
            }

            Map<Class<?>, Set<Object>> resources = CACHE.get(id);

            if (!resources.containsKey(resourceType)) {
                resources.put(resourceType, new HashSet<Object>(5));
            }

            resources.get(resourceType).add(resource);
        }
    }

    /**
     * Return all the resources of the given type for the given module.
     *
     * @param id           The id of the module.
     * @param resourceType The resource type.
     * @param <T>          The type of resource.
     *
     * @return A Set containing all the resources of the given type for the given module.
     */
    public static <T> Set<T> getResource(String id, Class<T> resourceType) {
        if (CACHE.containsKey(id) && CACHE.get(id).containsKey(resourceType)) {
            return (Set<T>) CACHE.get(id).get(resourceType);
        }

        return Collections.emptySet();
    }

    /**
     * Remove the module from the cache.
     *
     * @param id The id of the module to remove from the cache.
     */
    public static void removeModule(String id) {
        CACHE.remove(id);
    }

    /**
     * Remove all the resource of the given type for the given resource.
     *
     * @param id           The module id.
     * @param resourceType The resource type.
     * @param <T>          The type of resource.
     */
    public static <T> void removeResourceOfType(String id, Class<T> resourceType) {
        if (CACHE.containsKey(id)) {
            CACHE.get(id).remove(resourceType);
        }
    }
}