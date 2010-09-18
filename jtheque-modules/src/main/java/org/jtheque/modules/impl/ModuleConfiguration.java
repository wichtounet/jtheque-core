package org.jtheque.modules.impl;

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

import org.jtheque.modules.Module;
import org.jtheque.states.State;
import org.jtheque.states.utils.AbstractConcurrentState;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.annotations.ThreadSafe;

import static org.jtheque.modules.ModuleState.DISABLED;
import static org.jtheque.modules.ModuleState.INSTALLED;

/**
 * A module configuration.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
@State(id = "jtheque-modules-configuration")
final class ModuleConfiguration extends AbstractConcurrentState {
    /**
     * Update the module state.
     *
     * @param module The module to update the state in the config.
     */
    void update(Module module) {
        setProperty(module.getId(), Boolean.toString(module.getState() != DISABLED));
    }

    /**
     * Remove the module.
     *
     * @param module The module to remove.
     */
    void remove(Module module) {
        removeProperty(module.getId());
    }

    /**
     * Set the initial state of the module.
     *
     * @param module The module.
     */
    void setInitialState(Module module) {
        module.setState(INSTALLED);

        if (StringUtils.isNotEmpty(getProperty(module.getId()))) {
            if (!Boolean.parseBoolean(getProperty(module.getId()))) {
                module.setState(DISABLED);
            }
        } else {
            update(module);
        }
    }
}