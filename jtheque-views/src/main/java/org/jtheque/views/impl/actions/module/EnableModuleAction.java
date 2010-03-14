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

import org.jtheque.core.ICore;
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
 * An action to enable a module.
 *
 * @author Baptiste Wicht
 */
public final class EnableModuleAction extends JThequeAction {
    /**
     * Construct a new EnableModuleAction.
     */
    public EnableModuleAction() {
        super("modules.actions.activate");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        IModuleView moduleView = ViewsServices.get(IViewManager.class).getViews().getModuleView();

        Module module = moduleView.getSelectedModule();

        if (module.getState() == ModuleState.DISABLED) {
            if (module.getCoreVersion().isGreaterThan(ICore.VERSION)) {
                ViewsServices.get(IUIUtils.class).displayI18nText("modules.message.versionproblem");
            } else {
                ViewsServices.get(IModuleManager.class).enableModule(module);
                moduleView.refreshList();

                ViewsServices.get(IUIUtils.class).displayI18nText("message.module.enabled");
            }
        } else {
            ViewsServices.get(IUIUtils.class).displayI18nText("error.module.not.disabled");
        }
    }
}