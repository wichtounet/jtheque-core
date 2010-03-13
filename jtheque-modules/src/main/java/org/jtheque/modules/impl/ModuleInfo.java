package org.jtheque.modules.impl;

import org.jtheque.modules.able.ModuleState;

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