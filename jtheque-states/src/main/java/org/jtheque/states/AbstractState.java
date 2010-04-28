package org.jtheque.states;

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

import org.jtheque.utils.collections.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * An abstract state.
 *
 * @author Baptiste Wicht
 */
@State(id = "jtheque-core-config")
public abstract class AbstractState {
    private final Map<String, String> properties = new HashMap<String, String>(10);

    @Load
    public void setProperties(Map<String, String> properties){
        this.properties.putAll(properties);
    }

    @Save
    public Map<String, String> getProperties() {
        return CollectionUtils.copyOf(properties);
    }

    protected final String getProperty(String key) {
        return properties.get(key);
    }

    protected final String getProperty(String key, String defaults) {
        String property = properties.get(key);

        if (property == null) {
            property = defaults;
        }

        return property;
    }

    protected final void setProperty(String key, String value) {
        properties.put(key, value);
    }
}