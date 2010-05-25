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

import org.jtheque.core.able.application.Application;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.utils.collections.Filter;

/**
 * A filter for the modules to filter with the configuration.
 *
 * @author Baptiste Wicht
 */
final class ConfigurationFilter implements Filter<Module> {
    private final ModuleConfiguration configuration;
    private final Application application;

    /**
     * Construct a new configuration filter.
     *
     * @param configuration The current module configuration.
     * @param application The current application. 
     */
    ConfigurationFilter(ModuleConfiguration configuration, Application application) {
        super();

        this.configuration = configuration;
        this.application = application;
    }

    @Override
    public boolean accept(Module module) {
        if (configuration.containsModule(module)) {
            module.setState(configuration.getState(module.getId()));

            return true;
        } else if(application.isModuleDiscovery() || application.getModules().contains(module.getId())){
            module.setState(ModuleState.INSTALLED);
            configuration.add(module);

            return true;
        }

        return false;
    }
}
