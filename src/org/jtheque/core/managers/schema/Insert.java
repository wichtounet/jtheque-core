package org.jtheque.core.managers.schema;

import org.jtheque.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
 * An insert of HSQL Database file.
 *
 * @author Baptiste Wicht
 */
public final class Insert {
    private String table;
    private List<String> values;

    /**
     * Return the table of the insert.
     *
     * @return The table of the insert.
     */
    public String getTable() {
        return table;
    }

    /**
     * Set the table of the insert.
     *
     * @param table The table of the insert.
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * Return the String value at the specified index.
     *
     * @param index The index of the value to get.
     * @return the String value at the specified index or null if there is no values at this index.
     */
    private String get(int index) {
        return values.get(index);
    }

    /**
     * Return the string value at the position index.
     *
     * @param index The position of the value.
     * @return The value at this position else null if there is no value at this position.
     */
    public String getString(int index) {
        String value = get(index);

        if ("NULL".equalsIgnoreCase(value)) {
            value = "";
        }

        return value;
    }

    /**
     * Return the int value at the position index.
     *
     * @param index The position of the value.
     * @return The int value at this position else null if there is no value at this position.
     */
    public Integer getInt(int index) {
        String value = getString(index);

        if ("NULL".equalsIgnoreCase(value) || StringUtils.isEmpty(value)) {
            return null;
        }

        return Integer.parseInt(value);
    }

    /**
     * Return the boolean value at the position index.
     *
     * @param index The position of the value.
     * @return The boolean value at this position else null if there is no value at this position.
     */
    public Boolean getBoolean(int index) {
        String value = getString(index);

        if ("NULL".equalsIgnoreCase(value)) {
            return null;
        }

        return Boolean.parseBoolean(value);
    }

    /**
     * Set the values of the insert.
     *
     * @param values The values of the insert.
     */
    public void setValues(Collection<String> values) {
        this.values = new ArrayList<String>(values);
    }
}