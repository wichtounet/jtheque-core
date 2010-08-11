package org.jtheque.views.impl.controllers;

import org.jtheque.core.Core;
import org.jtheque.modules.ModuleDescription;
import org.jtheque.modules.ModuleService;
import org.jtheque.ui.Action;
import org.jtheque.ui.UIUtils;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.update.InstallationResult;
import org.jtheque.update.UpdateService;
import org.jtheque.views.panel.RepositoryView;

import javax.annotation.Resource;

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

/**
 * The controller for the repository view.
 *
 * @author Baptiste Wicht
 */
public class RepositoryController extends AbstractController<RepositoryView> {
    @Resource
    private UIUtils uiUtils;

    @Resource
    private ModuleService moduleService;

    @Resource
    private UpdateService updateService;

    /**
     * Construct a new RepositoryController. 
     */
    public RepositoryController() {
        super(RepositoryView.class);
    }

    /**
     * Expand the selected module.
     */
    @Action("repository.actions.expand")
    public void expand() {
        getView().expandSelectedModule();
    }

    /**
     * installFromRepository the selected module.
     */
    @Action("repository.actions.installFromRepository")
    public void install() {
        ModuleDescription description = getView().getSelectedModule();

        if (description.getCoreVersion().isGreaterThan(Core.VERSION)) {
            uiUtils.displayI18nText("error.module.version.core");
        } else {
            if (moduleService.isInstalled(description.getId())) {
                uiUtils.displayI18nText("message.repository.module.installed");
            } else {
                InstallationResult result = updateService.installModule(description.getDescriptorURL());

                moduleService.installFromRepository(result);
            }
        }
    }
}