package org.jtheque.states;

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