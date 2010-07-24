package org.jtheque.views.impl.controllers;

import org.jtheque.core.able.ICore;
import org.jtheque.modules.able.IModuleDescription;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.views.able.IViews;
import org.jtheque.views.able.panel.ICollectionView;
import org.jtheque.views.able.panel.IRepositoryView;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

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
public class RepositoryController extends AbstractController<IRepositoryView> {
    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IModuleService moduleService;

    public RepositoryController() {
        super(IRepositoryView.class);
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(2);

        translations.put("repository.actions.expand", "expand");
        translations.put("repository.actions.install", "install");

        return translations;
    }

    /**
     * Expand the selected module.
     */
    private void expand() {
        getView().expandSelectedModule();
    }

    /**
     * install the selected module.
     */
    private void install() {
        IModuleDescription description = getView().getSelectedModule();

        if (description.getCoreVersion().isGreaterThan(ICore.VERSION)) {
            uiUtils.displayI18nText("error.module.version.core");
        } else {
            if (moduleService.isInstalled(description.getId())) {
                uiUtils.displayI18nText("message.repository.module.installed");
            } else {
                moduleService.install(description.getDescriptorURL());
            }
        }
    }
}