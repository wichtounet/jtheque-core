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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.core.Core;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.update.IUpdateManager;
import org.jtheque.core.managers.view.able.IViewManager;
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
        Version neededVersion = new Version(module.getInfos().core());
        Version currentVersion = Core.getInstance().getCoreCurrentVersion();

        if (!currentVersion.equals(neededVersion)) {
            if (neededVersion.isGreaterThan(currentVersion)) {
                Managers.getManager(IViewManager.class).displayI18nText("error.module.core.version.greater");

                return false;
            } else if (!currentVersion.equals(neededVersion) && Core.getInstance().isNotCompatibleWith(neededVersion)) {
                return verifyForUpdate(module);
            }
        }

        return true;
    }

    /**
     * Verify if the module has update.
     *
     * @param module The module to verify for update.
     * @return true if we must update the module else false.
     */
    private static boolean verifyForUpdate(ModuleContainer module) {
        if (Managers.getManager(IUpdateManager.class).isUpToDate(module)) {
            Managers.getManager(IViewManager.class).displayI18nText("error.module.core.version.compatibility.no");

            return false;
        } else {
            return !userWantToUpdate(module);
        }
    }

    /**
     * Ask the user if he want to update the module or not.
     *
     * @param module The module to ask the user for update.
     * @return true if the user want to update the module else false.
     */
    private static boolean userWantToUpdate(ModuleContainer module) {
        boolean update = Managers.getManager(IViewManager.class).askI18nUserForConfirmation(
                "error.module.core.version.compatibility.yes",
                "error.module.core.version.compatibility.yes.title");

        if (update) {
            Managers.getManager(IUpdateManager.class).updateToMostRecentVersion(module);
        } else {
            return true;
        }

        return false;
    }
}
