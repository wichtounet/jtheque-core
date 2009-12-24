package org.jtheque.core.managers.view.impl.components.panel;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.components.IModulesPanelView;
import org.jtheque.core.managers.view.impl.components.model.ModuleListModel;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.LinedButtonBarBuilder;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import javax.swing.JList;
import javax.swing.JPanel;

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

/**
 * @author Baptiste Wicht
 */
public final class ModulesPanel extends JPanel implements IModulesPanelView {
    private JList modulesList;

    private Action enableModuleAction;
    private Action disableModuleAction;
    private Action uninstallModuleAction;
    private Action updateModuleAction;
    private Action installModuleAction;
    private Action loadModuleAction;
    private Action searchRepositoryAction;

    /**
     * Build the panel.
     */
    @PostConstruct
    public void build() {
        PanelBuilder builder = new PanelBuilder(this);

        builder.add(new KernelInfoPanel(), builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0));

        modulesList = builder.addList(new ModuleListModel(), new ModuleListRenderer(), builder.gbcSet(0, 1, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1.0, 1.0));
        modulesList.setVisibleRowCount(4);

        LinedButtonBarBuilder barBuilder = new LinedButtonBarBuilder(2);

        barBuilder.addActions(1, enableModuleAction, disableModuleAction, uninstallModuleAction, updateModuleAction);
        barBuilder.addActions(2, installModuleAction, loadModuleAction, searchRepositoryAction);

        builder.add(barBuilder.getPanel(), builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0));
    }

    @Override
    public ModuleContainer getSelectedModule() {
        return (ModuleContainer) modulesList.getSelectedValue();
    }

    @Override
    public void refresh() {
        Managers.getManager(IViewManager.class).refresh(modulesList);
    }

    /**
     * Set the action to launch to enable the module. This is not for use, this is for Spring injection.
     *
     * @param enableModuleAction The action.
     */
    public void setEnableModuleAction(Action enableModuleAction) {
        this.enableModuleAction = enableModuleAction;
    }

    /**
     * Set the action to launch to disable the module. This is not for use, this is for Spring injection.
     *
     * @param disableModuleAction The action.
     */
    public void setDisableModuleAction(Action disableModuleAction) {
        this.disableModuleAction = disableModuleAction;
    }

    /**
     * Set the action to launch to uninstall the module. This is not for use, this is for Spring injection.
     *
     * @param uninstallModuleAction The action.
     */
    public void setUninstallModuleAction(Action uninstallModuleAction) {
        this.uninstallModuleAction = uninstallModuleAction;
    }

    /**
     * Set the action to launch to update the module. This is not for use, this is for Spring injection.
     *
     * @param updateModuleAction The action.
     */
    public void setUpdateModuleAction(Action updateModuleAction) {
        this.updateModuleAction = updateModuleAction;
    }

    /**
     * Set the action to launch to install the module. This is not for use, this is for Spring injection.
     *
     * @param installModuleAction The action.
     */
    public void setInstallModuleAction(Action installModuleAction) {
        this.installModuleAction = installModuleAction;
    }

    /**
     * Set the action to launch to load the module. This is not for use, this is for Spring injection.
     *
     * @param loadModuleAction The action.
     */
    public void setLoadModuleAction(Action loadModuleAction) {
        this.loadModuleAction = loadModuleAction;
    }

    /**
     * Set the action to launch to search in the repository for modules. This is not for use, this is for Spring injection.
     *
     * @param searchRepositoryAction The action.
     */
    public void setSearchRepositoryAction(Action searchRepositoryAction) {
        this.searchRepositoryAction = searchRepositoryAction;
    }
}