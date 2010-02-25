package org.jtheque.core.utils;

import java.util.HashMap;
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

public class SimplePropertiesCache {
    private static final Map<String, String> PROPERTIES = new HashMap<String, String>(5);

    private SimplePropertiesCache() {
        super();
    }

    public static String put(String key, String value) {
        return PROPERTIES.put(key, value);
    }

    public static String get(String key) {
        return PROPERTIES.get(key);
    }
}