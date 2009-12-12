package org.jtheque.core.utils.db;

import org.jtheque.core.managers.persistence.able.Entity;

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
     * @return true if the id is in the list else false.
     */
    public static boolean containsID(Iterable<? extends Entity> list, int id) {
        boolean found = false;

        for (Entity d : list) {
            if (d.getId() == id) {
                found = true;
                break;
            }
        }

        return found;
    }
}