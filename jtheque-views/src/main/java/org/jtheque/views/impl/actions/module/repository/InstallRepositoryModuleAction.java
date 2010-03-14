package org.jtheque.views.impl.actions.module.repository;

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
import org.jtheque.modules.able.IModuleManager;
import org.jtheque.modules.impl.ModuleDescription;
import org.jtheque.ui.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewManager;

import java.awt.event.ActionEvent;

/**
 * Install the selected module.
 *
 * @author Baptiste Wicht
 */
public final class InstallRepositoryModuleAction extends JThequeAction {
    /**
     * Construct a new InstallRepositoryModuleAction.
     */
    public InstallRepositoryModuleAction() {
        super("repository.actions.install");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ModuleDescription description = ViewsServices.get(IViewManager.class).getViews().getRepositoryView().getSelectedModule();

        if (description.getCoreVersion().isGreaterThan(ICore.VERSION)) {
            ViewsServices.get(IUIUtils.class).displayI18nText("error.module.version.core");
        } else {
            if (ViewsServices.get(IModuleManager.class).isInstalled(description.getId())) {
                ViewsServices.get(IUIUtils.class).displayI18nText("message.repository.module.installed");
            } else {
                ViewsServices.get(IModuleManager.class).install(description.getVersionsFileURL());
            }
        }
    }
}