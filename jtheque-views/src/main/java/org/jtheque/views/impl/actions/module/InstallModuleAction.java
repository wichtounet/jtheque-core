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
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.panel.IModuleView;

import javax.annotation.Resource;

import java.awt.event.ActionEvent;
import java.io.File;

/**
 * An action to install a module.
 *
 * @author Baptiste Wicht
 */
public final class InstallModuleAction extends JThequeAction {
    private final IUIUtils uiUtils;
    private final IModuleService moduleService;

    /**
     * Construct a new InstallModuleAction.
     *
     * @param moduleService The module service.
     * @param uiUtils The UI Utils. 
     */
    public InstallModuleAction(IModuleService moduleService, IUIUtils uiUtils) {
        super("modules.actions.new");

        this.moduleService = moduleService;
        this.uiUtils = uiUtils;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String path = SwingUtils.chooseFile(new SimpleFilter("JAR File (*.jar)", "jar"));

        if (path != null) {
            File file = new File(path);

            boolean installed = moduleService.installModule(file);

            if (installed) {
                uiUtils.displayI18nText("message.module.installed");
            } else {
                uiUtils.displayI18nText("error.module.not.installed");
            }
        }
    }
}