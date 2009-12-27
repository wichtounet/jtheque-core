package org.jtheque.core.managers.view.impl.actions.module.repository;

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
import org.jtheque.core.managers.module.IModuleManager;
import org.jtheque.core.managers.update.repository.ModuleDescription;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.update.IRepositoryView;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;

import javax.annotation.Resource;
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
        ModuleDescription description = CoreUtils.<IRepositoryView>getBean("repositoryView").getSelectedModule();

        if (description.getCoreVersion().isGreaterThan(Managers.getCore().getCoreCurrentVersion())) {
            Managers.getManager(IViewManager.class).displayI18nText("error.module.version.core");
        } else {
            if (Managers.getManager(IModuleManager.class).isInstalled(description.getId())) {
                Managers.getManager(IViewManager.class).displayI18nText("message.repository.module.installed");
            } else {
                Managers.getManager(IModuleManager.class).install(description.getVersionsFileURL());
            }
        }
    }
}