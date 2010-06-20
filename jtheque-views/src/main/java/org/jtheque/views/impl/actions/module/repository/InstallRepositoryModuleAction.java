package org.jtheque.views.impl.actions.module.repository;

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

import org.jtheque.core.able.ICore;
import org.jtheque.modules.able.IModuleDescription;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.IViews;

import javax.annotation.Resource;

import java.awt.event.ActionEvent;

/**
 * Install the selected module.
 *
 * @author Baptiste Wicht
 */
public final class InstallRepositoryModuleAction extends JThequeAction {
    @Resource
    private IViews views;

    @Resource
    private IUIUtils utils;

    @Resource
    private IModuleService moduleService;

    /**
     * Construct a new InstallRepositoryModuleAction.
     */
    public InstallRepositoryModuleAction() {
        super("repository.actions.install");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IModuleDescription description = views.getRepositoryView().getSelectedModule();

        if (description.getCoreVersion().isGreaterThan(ICore.VERSION)) {
            utils.displayI18nText("error.module.version.core");
        } else {
            if (moduleService.isInstalled(description.getId())) {
                utils.displayI18nText("message.repository.module.installed");
            } else {
                moduleService.install(description.getDescriptorURL());
            }
        }
    }
}