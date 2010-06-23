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
import org.jtheque.modules.able.ModuleState;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.panel.IModuleView;

import java.awt.event.ActionEvent;

/**
 * An action to disable a module.
 *
 * @author Baptiste Wicht
 */
public final class DisableModuleAction extends JThequeAction {
    private final IModuleView moduleView;
    private final IUIUtils uiUtils;
    private final IModuleService moduleService;

    /**
     * Construct a new DisableModuleAction.
     *
     * @param moduleService The module service.
     * @param uiUtils       The UI Utils.
     * @param moduleView    The module view.
     */
    public DisableModuleAction(IModuleService moduleService, IUIUtils uiUtils, IModuleView moduleView) {
        super("modules.actions.desactivate");

        this.moduleService = moduleService;
        this.uiUtils = uiUtils;
        this.moduleView = moduleView;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        Module module = moduleView.getSelectedModule();

        if (module.getState() == ModuleState.DISABLED) {
            uiUtils.displayI18nText("error.module.not.enabled");
        } else if (module.getState() == ModuleState.STARTED) {
            uiUtils.displayI18nText("error.module.started");
        } else {
            moduleService.disableModule(module);
            moduleView.refreshList();

            uiUtils.displayI18nText("message.module.disabled");
        }
    }
}