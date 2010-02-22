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
import org.jtheque.core.managers.module.beans.ModuleState;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.update.IModuleView;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;

import java.awt.event.ActionEvent;

/**
 * An action to disable a module.
 *
 * @author Baptiste Wicht
 */
public final class DisableModuleAction extends JThequeAction {
    /**
     * Construct a new DisableModuleAction.
     */
    public DisableModuleAction() {
        super("modules.actions.desactivate");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        IModuleView moduleView = CoreUtils.getBean(IModuleView.class);

        ModuleContainer module = moduleView.getSelectedModule();

        if (module.getState() == ModuleState.DISABLED) {
            Managers.getManager(IViewManager.class).displayI18nText("error.module.not.enabled");
        } else {
            Managers.getManager(IModuleManager.class).disableModule(module);
            moduleView.refreshList();

            Managers.getManager(IViewManager.class).displayI18nText("message.module.disabled");
        }
    }
}