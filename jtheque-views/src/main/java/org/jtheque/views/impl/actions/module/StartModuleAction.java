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
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.utils.ui.edt.SimpleTask;
import org.jtheque.views.able.panel.IModuleView;

import java.awt.event.ActionEvent;

/**
 * An action to load a module.
 *
 * @author Baptiste Wicht
 */
public final class StartModuleAction extends JThequeAction {
    private final IModuleView moduleView;
    private final IUIUtils uiUtils;
    private final IModuleService moduleService;

    /**
     * Construct a new StartModuleAction.
     *
     * @param moduleService The module service.
     * @param uiUtils       The UI Utils.
     * @param moduleView    The module view.
     */
    public StartModuleAction(IModuleService moduleService, IUIUtils uiUtils, IModuleView moduleView) {
        super("modules.actions.start");

        this.moduleService = moduleService;
        this.uiUtils = uiUtils;
        this.moduleView = moduleView;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        final Module module = moduleView.getSelectedModule();

        String error = moduleService.canModuleLaunched(module);

        if (StringUtils.isEmpty(error)) {
            SwingUtils.execute(new SimpleTask(){
                @Override
                public void run() {
                    moduleView.startWait();
                }
            });

            new Thread(new StartModuleRunnable(module), "Module loader").start();
        } else {
            uiUtils.getDelegate().displayText(error);
        }
    }

    private class StartModuleRunnable implements Runnable {
        private final Module module;

        private StartModuleRunnable(Module module) {
            this.module = module;
        }

        @Override
        public void run() {
            moduleService.startModule(module);

            SwingUtils.execute(new SimpleTask() {
                @Override
                public void run() {
                    moduleView.refreshList();
                    moduleView.stopWait();
                }
            });
        }
    }
}