package org.jtheque.views.impl.actions.module;

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

import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.panel.IModuleView;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to uninstall a module.
 *
 * @author Baptiste Wicht
 */
public final class UninstallModuleAction extends JThequeAction {
    @Resource
    private IModuleView moduleView;

    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IModuleService moduleService;

    /**
     * Construct a new UninstallModuleAction.
     */
    public UninstallModuleAction() {
        super("modules.actions.uninstall");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Module module = moduleView.getSelectedModule();

        boolean confirm = uiUtils.askI18nUserForConfirmation(
                "dialogs.confirm.uninstall",
                "dialogs.confirm.uninstall.title");

        if (confirm) {
            moduleService.uninstallModule(module);
            moduleView.refreshList();
        }
    }
}