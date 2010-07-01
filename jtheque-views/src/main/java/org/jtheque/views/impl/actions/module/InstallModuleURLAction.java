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
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.SwingUtils;

import java.awt.event.ActionEvent;
import java.io.File;

/**
 * An action to install a module.
 *
 * @author Baptiste Wicht
 */
public final class InstallModuleURLAction extends JThequeAction {
    private final IModuleService moduleService;
    private final IUIUtils utils;

    /**
     * Construct a new InstallModuleFileAction.
     *
     * @param moduleService The module service.
     * @param utils         The ui utils.
     */
    public InstallModuleURLAction(IModuleService moduleService, IUIUtils utils) {
        super("modules.actions.url.new");

        this.moduleService = moduleService;
        this.utils = utils;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String url = utils.askI18nText("dialogs.modules.install.url");

        if (StringUtils.isNotEmpty(url)) {
            moduleService.install(url);
        }
    }
}