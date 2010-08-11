package org.jtheque.persistence.utils;

import org.jtheque.persistence.Entity;

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
 * An utility class for the entity.
 *
 * @author Baptiste Wicht
 */
public final class EntityUtils {
    /**
     * This is an utility class, not instanciable.
     */
    private EntityUtils() {
        super();
    }

    /**
     * Search in a collection to found a specific id.
     *
     * @param list The list to search in.
     * @param id   The id to find.
     *
     * @return true if the id is in the list else false.
     */
    public static boolean containsID(Iterable<? extends Entity> list, int id) {
        for (Entity d : list) {
            if (d.getId() == id) {
                return true;
            }
        }

        return false;
    }

    /**
     * Search the Iterable for an Entity with the given temporary id.
     *
     * @param list The Iterable to search on.
     * @param id   The temporary id to search for.
     * @param <T>  The type of entity.
     *
     * @return The entity with the given temporary id if there is one otherwise {@code null}
     */
    public static <T extends Entity> T getByTemporaryId(Iterable<T> list, int id) {
        for (T d : list) {
            if (d.getTemporaryContext().getId() == id) {
                return d;
            }
        }

        return null;
    }
}