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

import org.jtheque.modules.able.IModuleManager;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.ui.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewManager;
import org.jtheque.views.able.panel.IModuleView;

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
        IModuleView moduleView = ViewsServices.get(IViewManager.class).getViews().getModuleView();

        Module module = moduleView.getSelectedModule();

        if (module.getState() == ModuleState.DISABLED) {
            ViewsServices.get(IUIUtils.class).displayI18nText("error.module.not.enabled");
        } else {
            ViewsServices.get(IModuleManager.class).disableModule(module);
            moduleView.refreshList();

            ViewsServices.get(IUIUtils.class).displayI18nText("message.module.disabled");
        }
    }
}