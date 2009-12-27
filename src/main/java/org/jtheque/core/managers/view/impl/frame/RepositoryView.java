package org.jtheque.core.managers.view.impl.frame;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.module.IModuleManager;
import org.jtheque.core.managers.update.repository.ModuleDescription;
import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.core.managers.view.able.update.IRepositoryView;
import org.jtheque.core.managers.view.impl.actions.module.repository.ExpandRepositoryModuleAction;
import org.jtheque.core.managers.view.impl.actions.module.repository.InstallRepositoryModuleAction;
import org.jtheque.core.managers.view.impl.components.model.ModuleRepositoryListModel;
import org.jtheque.core.managers.view.impl.components.panel.ModulePanel;
import org.jtheque.core.managers.view.impl.components.renderers.ModuleRepositoryListRenderer;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingBuildedDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

/**
 * The view to display the repository of the application.
 *
 * @author Baptiste Wicht
 */
public final class RepositoryView extends SwingBuildedDialogView<IModel> implements IRepositoryView {
    private JList list;

    /**
     * Construct a new RepositoryView. 
     */
    public RepositoryView(){
        super();

        build();
    }

    @Override
    protected void initView(){
        setTitleKey("repository.view.title", Managers.getManager(IModuleManager.class).getRepository().getApplication());
        setResizable(false);
    }

    @Override
    protected void buildView(PanelBuilder builder){
        builder.addLabel(Managers.getManager(IModuleManager.class).getRepository().getTitle().toString(),
                builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));

        list = new JList();
        list.setModel(new ModuleRepositoryListModel());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new ModuleRepositoryListRenderer());
        list.setVisibleRowCount(5);

        builder.addScrolled(list, builder.gbcSet(0, 1, GridBagUtils.BOTH));

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL),
                new ExpandRepositoryModuleAction(), new InstallRepositoryModuleAction());
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
    }
}