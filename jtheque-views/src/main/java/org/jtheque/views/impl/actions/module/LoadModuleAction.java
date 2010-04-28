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
import org.jtheque.utils.StringUtils;
import org.jtheque.views.able.panel.IModuleView;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to load a module.
 *
 * @author Baptiste Wicht
 */
public final class LoadModuleAction extends JThequeAction {
    @Resource
    private IModuleView moduleView;

    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IModuleService moduleService;

    /**
     * Construct a new LoadModuleAction.
     */
    public LoadModuleAction() {
        super("modules.actions.load");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        Module module = moduleView.getSelectedModule();

        String error = moduleService.canModuleLaunched(module);

        if (StringUtils.isEmpty(error)) {
            moduleService.loadModule(module);
            moduleView.refreshList();
        } else {
            uiUtils.getDelegate().displayText(error);
        }
    }
}