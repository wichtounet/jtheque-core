package org.jtheque.views.impl.actions.module;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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