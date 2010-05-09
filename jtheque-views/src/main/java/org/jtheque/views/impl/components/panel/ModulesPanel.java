package org.jtheque.views.impl.components.panel;

import org.jtheque.i18n.ILanguageService;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.ui.utils.actions.ActionFactory;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.filthy.FilthyBuildedPanel;
import org.jtheque.ui.utils.filthy.IFilthyUtils;
import org.jtheque.update.IUpdateService;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.components.IModulesPanelView;
import org.jtheque.views.impl.actions.module.DisableModuleAction;
import org.jtheque.views.impl.actions.module.EnableModuleAction;
import org.jtheque.views.impl.actions.module.InstallModuleAction;
import org.jtheque.views.impl.actions.module.LoadModuleAction;
import org.jtheque.views.impl.actions.module.UninstallModuleAction;
import org.jtheque.views.impl.actions.module.UpdateModuleAction;
import org.jtheque.views.impl.components.renderers.ModuleListRenderer;
import org.jtheque.views.impl.models.ModuleListModel;

import javax.swing.JList;

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
 * A panel to display all the modules. 
 *
 * @author Baptiste Wicht
 */
public final class ModulesPanel extends FilthyBuildedPanel implements IModulesPanelView {
    private JList modulesList;

    private final IUpdateService updateService;
    private final IModuleService moduleService;
    private final ILanguageService languageService;

    /**
     * Construct a new ModulesPanel. 
     */
    public ModulesPanel(IUpdateService updateService, IModuleService moduleService, ILanguageService languageService, IFilthyUtils utils){
        super(utils, languageService);

        this.updateService = updateService;
        this.moduleService = moduleService;
        this.languageService = languageService;

        build();
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        builder.add(new KernelInfoPanel(languageService, updateService), 
                builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0));

        modulesList = builder.addScrolledList(new ModuleListModel(moduleService), new ModuleListRenderer(updateService),
                builder.gbcSet(0, 1, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1.0, 1.0));
        modulesList.setVisibleRowCount(4);

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0),
                new EnableModuleAction(), new DisableModuleAction(), new UninstallModuleAction(), new UpdateModuleAction());

        builder.addButtonBar(builder.gbcSet(0, 3, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0),
                new InstallModuleAction(), new LoadModuleAction(), ActionFactory.createDisplayViewAction("modules.actions.repository", "repositoryView"));
    }

    @Override
    public Module getSelectedModule() {
        return (Module) modulesList.getSelectedValue();
    }

    @Override
    public void refresh() {
        SwingUtils.refresh(modulesList);
    }
}