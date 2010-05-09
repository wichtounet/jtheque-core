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

public final class ModuleResourceCache {
    private static final Map<String, Map<Class<?>, Set<Object>>> CACHE = new HashMap<String, Map<Class<?>, Set<Object>>>(8);

    private ModuleResourceCache() {
        super();
    }

    public static <T> void addResource(String id, Class<T> resourceType, T resource){
        if(StringUtils.isNotEmpty(id)){
            if(!CACHE.containsKey(id)){
                CACHE.put(id, new HashMap<Class<?>, Set<Object>>(8));
            }

            Map<Class<?>, Set<Object>> resources = CACHE.get(id);

            if(!resources.containsKey(resourceType)){
                resources.put(resourceType, new HashSet<Object>(5));
            }

            resources.get(resourceType).add(resource);
        }
    }

    public static <T> Set<T> getResource(String id, Class<T> resourceType){
        if(CACHE.containsKey(id) && CACHE.get(id).containsKey(resourceType)){
            return (Set<T>) CACHE.get(id).get(resourceType);
        }

        return Collections.emptySet();
    }

    public static void removeModule(String id){
        CACHE.remove(id);
    }

    public static <T> void removeResourceOfType(String id, Class<T> resourceType){
        if(CACHE.containsKey(id)){
            CACHE.get(id).remove(resourceType);
        }
    }
}