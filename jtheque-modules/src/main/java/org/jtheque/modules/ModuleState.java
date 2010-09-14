package org.jtheque.modules;

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
 * The state of a module.
 *
 * @author Baptiste Wicht
 */
public enum ModuleState {
    INSTALLED("modules.states.installed", 0),
    STARTED("modules.states.started", 1),
    DISABLED("modules.states.disabled", 2);

    private final String key;
    private final int value;

    /**
     * Construct a new ModuleState.
     *
     * @param key   The i18n key of the module state.
     * @param value The value of the state (used to write it to configuration files).
     */
    ModuleState(String key, int value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Return the i18n key.
     *
     * @return the i18n key.
     */
    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

    /**
     * Return the enum value from the value.
     *
     * @param value The value of the state.
     *
     * @return The enum value corresponding to the value.
     */
    public static ModuleState valueOf(int value) {
        for (ModuleState s : values()) {
            if (s.value == value) {
                return s;
            }
        }

        throw new IllegalArgumentException("The value is not a value of a state. ");
    }
}
