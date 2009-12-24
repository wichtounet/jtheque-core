package org.jtheque.core.managers.event;

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
 * An event level.
 *
 * @author Baptiste Wicht
 */
public enum EventLevel {
    INFO(100, "event.levels.info"),
    WARN(50, "event.levels.warn"),
    ERROR(10, "event.levels.error");

    private final String key;
    private final int value;

    /**
     * Create a new Event Level.
     *
     * @param value The value.
     * @param key   The internationalization key.
     */
    EventLevel(int value, String key) {
        this.value = value;
        this.key = key;
    }

    /**
     * Return the internationalization key.
     *
     * @return The internationalization of the level.
     */
    public String getKey() {
        return key;
    }

    /**
     * Return the int value of the event level.
     *
     * @return The int value of the event level.
     */
    public int intValue() {
        return value;
    }

    /**
     * Return the EventLevel corresponding to the value.
     *
     * @param value The int value.
     * @return The EventLevel corresponding to the value.
     */
    public static EventLevel get(int value) {
        switch (value) {
            case 50:
                return WARN;
            case 10:
                return ERROR;
            default:
                return INFO;
        }
    }
}