package org.jtheque.core.managers.view.impl.actions.module;

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
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.update.IModuleView;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to uninstall a module.
 *
 * @author Baptiste Wicht
 */
public final class UninstallModuleAction extends JThequeAction {
    /**
     * Construct a new UninstallModuleAction.
     */
    public UninstallModuleAction() {
        super("modules.actions.uninstall");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IModuleView moduleView = CoreUtils.getBean("moduleView");

        ModuleContainer module = moduleView.getSelectedModule();

        boolean confirm = Managers.getManager(IViewManager.class).askI18nUserForConfirmation(
                "dialogs.confirm.uninstall",
                "dialogs.confirm.uninstall.title");

        if (confirm) {
            Managers.getManager(IModuleManager.class).uninstallModule(module);

            moduleView.refreshList();
        }
    }
}