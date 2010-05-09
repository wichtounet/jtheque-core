package org.jtheque.views.impl.windows;

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
import org.jtheque.modules.impl.ModuleDescription;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.panel.IRepositoryView;
import org.jtheque.views.impl.actions.module.repository.ExpandRepositoryModuleAction;
import org.jtheque.views.impl.actions.module.repository.InstallRepositoryModuleAction;
import org.jtheque.views.impl.components.panel.ModulePanel;
import org.jtheque.views.impl.components.renderers.ModuleRepositoryListRenderer;
import org.jtheque.views.impl.models.ModuleRepositoryListModel;

import javax.annotation.Resource;
import javax.swing.JList;

/**
 * The view to display the repository of the application.
 *
 * @author Baptiste Wicht
 */
public final class RepositoryView extends SwingFilthyBuildedDialogView<IModel> implements IRepositoryView {
    private JList list;

    @Resource
    private IModuleService moduleService;

    @Override
    protected void initView(){
        setTitleKey("repository.view.title", moduleService.getRepository().getApplication());
        setResizable(false);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder){
        builder.addLabel(moduleService.getRepository().getTitle().toString(), PanelBuilder.BOLD, 18.0f,
                builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));

        list = builder.addScrolledList(new ModuleRepositoryListModel(moduleService), new ModuleRepositoryListRenderer(), builder.gbcSet(0, 1, GridBagUtils.BOTH));

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL),
                new ExpandRepositoryModuleAction(this), 
                new InstallRepositoryModuleAction());
    }

    @Override
    public ModuleDescription getSelectedModule() {
        return (ModuleDescription) list.getSelectedValue();
    }

    @Override
    public void expandSelectedModule() {
        ModulePanel renderer = (ModulePanel)
                list.getCellRenderer().getListCellRendererComponent(
                        list, list.getSelectedValue(),
                        list.getSelectedIndex(), true, true);

        renderer.expand();

        SwingUtils.refresh(list);
    }
}