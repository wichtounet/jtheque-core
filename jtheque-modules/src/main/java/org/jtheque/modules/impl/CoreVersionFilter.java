package org.jtheque.modules.impl;

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

import org.jtheque.core.ICore;
import org.jtheque.ui.IUIUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.Filter;

/**
 * A filter for the modules to filter with the version of the module and the core.
 *
 * @author Baptiste Wicht
 */
final class CoreVersionFilter implements Filter<ModuleContainer> {
    @Override
    public boolean accept(ModuleContainer module) {
        Version neededVersion = module.getCoreVersion();
        Version currentVersion = ModulesServices.get(ICore.class).getCoreCurrentVersion();

        if (neededVersion != null && !currentVersion.equals(neededVersion)) {
            if (neededVersion.isGreaterThan(currentVersion)) {
                ModulesServices.get(IUIUtils.class).displayI18nText("error.module.core.version.greater");

                return false;
            } else if (!currentVersion.equals(neededVersion) && ModulesServices.get(ICore.class).isNotCompatibleWith(neededVersion)) {
                ModulesServices.get(IUIUtils.class).displayI18nText("error.module.core.version.compatibility.no");

                return false;
            }
        }

        return true;
    }
}
