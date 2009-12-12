package org.jtheque.core.managers.module;

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

import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.utils.collections.Filter;

/**
 * A filter for the modules to filter with the configuration.
 *
 * @author Baptiste Wicht
 */
final class ConfigurationFilter implements Filter<ModuleContainer> {
    private final ModuleConfiguration configuration;

    /**
     * Construct a new configuration filter.
     *
     * @param configuration The current module configuration.
     */
    ConfigurationFilter(ModuleConfiguration configuration) {
        super();

        this.configuration = configuration;
    }

    @Override
    public boolean accept(ModuleContainer module) {
        if (configuration.containsModule(module)) {
            module.setState(configuration.getState(module.getId()));

            return true;
        }

        return false;
    }
}
