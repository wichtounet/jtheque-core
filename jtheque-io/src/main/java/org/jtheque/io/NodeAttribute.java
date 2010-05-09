package org.jtheque.io;

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
 * A node state attribute.
 *
 * @author Baptiste Wicht
 */
public final class NodeAttribute {
    private String key;
    private String value;

    /**
     * Construct a new NodeAttribute.
     *
     * @param key   The key of the attribute.
     * @param value The value of the attribute.
     */
    public NodeAttribute(String key, String value) {
        super();

        this.key = key;
        this.value = value;
    }

    /**
     * Return the key of the attribute.
     *
     * @return The key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Set the key of the attribute.
     *
     * @param key The key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Return the value of the attribute.
     *
     * @return The value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value of the attribute.
     *
     * @param value The value.
     */
    public void setValue(String value) {
        this.value = value;
    }
}