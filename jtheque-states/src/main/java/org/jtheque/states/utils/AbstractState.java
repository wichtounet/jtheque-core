package org.jtheque.states.utils;

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

import org.jtheque.states.Load;
import org.jtheque.states.Save;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Map;

/**
 * An abstract state. This state is not delegated and manage a simple map of properties. This class is not thread safe. 
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractState {
    private final Map<String, String> properties = CollectionUtils.newHashMap(10);

    /**
     * Set the properties of this state. This method is made to be called from the state service. It will be called only
     * once, at startup.
     *
     * @param properties The properties of this state.
     */
    @Load
    public void setProperties(Map<String, String> properties) {
        this.properties.putAll(properties);
    }

    /**
     * Return all the properties of the state. This method is made to be called from the state service. It will be
     * called only once, at application shutdown.
     *
     * @return The properties of the state.
     */
    @Save
    public Map<String, String> getProperties() {
        return CollectionUtils.copyOf(properties);
    }

    /**
     * Return the value of a property.
     *
     * @param key The key of the property.
     *
     * @return The value of the property with the given key or null if the property doesn't exist.
     */
    protected final String getProperty(String key) {
        return properties.get(key);
    }

    /**
     * Return the value of a property.
     *
     * @param key      The key of the property.
     * @param defaults The default value to use if the property doesn't exist.
     *
     * @return The value of the property with the given key or the given default value if the property doesn't exist.
     */
    protected final String getProperty(String key, String defaults) {
        String property = properties.get(key);

        if (property == null) {
            property = defaults;
        }

        return property;
    }

    /**
     * Set the property value of the state.
     *
     * @param key   The key of the property.
     * @param value The value of the property.
     */
    protected final void setProperty(String key, String value) {
        properties.put(key, value);
    }
}