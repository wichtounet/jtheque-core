package org.jtheque.io;

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