package org.jtheque.events;

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
     *
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