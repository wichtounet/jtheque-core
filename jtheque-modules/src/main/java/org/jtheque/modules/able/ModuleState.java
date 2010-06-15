package org.jtheque.modules.able;

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
    STARTED("modules.states.started"),
    INSTALLED("modules.states.installed"),
    DISABLED("modules.states.disabled");

    private final String key;

    /**
     * Construct a new ModuleState.
     *
     * @param key The i18n key of the module state.
     */
    ModuleState(String key) {
        this.key = key;
    }

    /**
     * Return the enum value from the ordinal value.
     *
     * @param ordinal The ordinal value.
     *
     * @return The enum value corresponding to the ordinal value.
     */
    public static ModuleState valueOf(int ordinal) {
        ModuleState state = STARTED;

        for (ModuleState s : values()) {
            if (s.ordinal() == ordinal) {
                state = s;
                break;
            }
        }

        return state;
    }

    /**
     * Return the i18n key.
     *
     * @return the i18n key.
     */
    public String getKey() {
        return key;
    }
}
