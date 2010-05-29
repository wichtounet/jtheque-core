package org.jtheque.modules.impl;

import org.jtheque.modules.able.ModuleState;

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
 * A module information.
 *
 * @author Baptiste Wicht
 */
final class ModuleInfo {
    private String moduleId;
    private ModuleState state;

    /**
     * Construct a new ModuleInfo with a specified id.
     *
     * @param id The id of the module.
     */
    ModuleInfo(String id) {
        super();

        moduleId = id;
    }

    /**
     * Set the state of the module.
     *
     * @param state The new state of the module.
     */
    public void setState(ModuleState state) {
        this.state = state;
    }

    /**
     * Return the state of the module.
     *
     * @return The state of the module.
     */
    public ModuleState getState() {
        return state;
    }

    /**
     * Set the module name.
     *
     * @param moduleId The name of the module.
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * Return the module name.
     *
     * @return The name of the module.
     */
    public String getModuleId() {
        return moduleId;
    }
}