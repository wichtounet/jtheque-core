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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.module.IModuleManager;
import org.jtheque.core.managers.update.repository.ModuleDescription;
import org.jtheque.core.managers.view.able.update.IRepositoryView;
import org.jtheque.core.managers.view.impl.components.model.ModuleRepositoryListModel;
import org.jtheque.core.managers.view.impl.components.panel.ModuleRepositoryListRenderer;
import org.jtheque.core.managers.view.impl.components.panel.ModuleRepositoryListRenderer.ModulePanel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.Action;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

/**
 * The view to display the repository of the application.
 *
 * @author Baptiste Wicht
 */
public final class RepositoryView extends SwingDialogView implements IRepositoryView {
    private JList list;

    private Action expandAction;
    private Action installAction;

    @Resource
    private IModuleManager moduleManager;

    /**
     * Construct a new <code>ModuleView</code>.
     *
     * @param frame The parent frame.
     */
    public RepositoryView(Frame frame) {
        super(frame);
    }

    /**
     * Build the view.
     */
    @PostConstruct
    public void build() {
        setTitle(getMessage("repository.view.title", moduleManager.getRepository().getApplication()));
        setContentPane(buildContentPane());
        setResizable(false);
        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build the content pane.
     *
     * @return The content pane.
     */
    private Container buildContentPane() {
        PanelBuilder builder = new PanelBuilder();

        builder.addLabel(moduleManager.getRepository().getTitle().toString(), builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL));

        list = new JList();
        list.setModel(new ModuleRepositoryListModel());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new ModuleRepositoryListRenderer());
        list.setVisibleRowCount(5);

        builder.addScrolled(list, builder.gbcSet(0, 1, GridBagUtils.BOTH));

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL), expandAction, installAction);

        return builder.getPanel();
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

    @Override
    protected void validate(Collection<JThequeError> errors) {
        //Nothing to validate
    }

    /**
     * Set the action to expand a module information. This is not for use, only for Spring Injection.
     *
     * @param expandAction The action.
     */
    public void setExpandAction(Action expandAction) {
        this.expandAction = expandAction;
    }

    /**
     * Set the action to install a module. This is not for use, only for Spring Injection.
     *
     * @param installAction The action.
     */
    public void setInstallAction(Action installAction) {
        this.installAction = installAction;
    }
}