package org.jtheque.views.impl.windows;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
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
        builder.addLabel(moduleService.getRepository().getTitle().toString(), PanelBuilder.BOLD, 18f,
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