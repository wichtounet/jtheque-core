package org.jtheque.core.managers.view.impl.components.panel;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.components.IModulesPanelView;
import org.jtheque.core.managers.view.impl.actions.ActionFactory;
import org.jtheque.core.managers.view.impl.actions.module.DisableModuleAction;
import org.jtheque.core.managers.view.impl.actions.module.EnableModuleAction;
import org.jtheque.core.managers.view.impl.actions.module.InstallModuleAction;
import org.jtheque.core.managers.view.impl.actions.module.LoadModuleAction;
import org.jtheque.core.managers.view.impl.actions.module.UninstallModuleAction;
import org.jtheque.core.managers.view.impl.actions.module.UpdateModuleAction;
import org.jtheque.core.managers.view.impl.components.model.ModuleListModel;
import org.jtheque.core.managers.view.impl.components.renderers.ModuleListRenderer;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.LinedButtonBarBuilder;

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
    private final JList modulesList;

    /**
     * Construct a new ModulesPanel. 
     */
    public ModulesPanel(){
        super();

        PanelBuilder builder = new JThequePanelBuilder(this);

        builder.add(new KernelInfoPanel(), builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0));

        modulesList = builder.addScrolledList(new ModuleListModel(), new ModuleListRenderer(), builder.gbcSet(0, 1, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1.0, 1.0));
        modulesList.setVisibleRowCount(4);

        LinedButtonBarBuilder barBuilder = new LinedButtonBarBuilder(2);

        barBuilder.addActions(1, new EnableModuleAction(), new DisableModuleAction(), new UninstallModuleAction(), new UpdateModuleAction());
        barBuilder.addActions(2, new InstallModuleAction(), new LoadModuleAction(), ActionFactory.createDisplayViewAction("modules.actions.repository", "repositoryView"));

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
}