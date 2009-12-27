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

import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.update.Updatable;
import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.core.managers.view.able.components.IModulesPanelView;
import org.jtheque.core.managers.view.able.components.IUpdatablesPanelView;
import org.jtheque.core.managers.view.able.update.IModuleView;
import org.jtheque.core.managers.view.impl.components.LayerTabbedPane;
import org.jtheque.core.managers.view.impl.components.panel.ModulesPanel;
import org.jtheque.core.managers.view.impl.components.panel.UpdatablesPanel;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingBuildedDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JComponent;

/**
 * A view to display the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleView extends SwingBuildedDialogView<IModel> implements IModuleView {
    private IModulesPanelView modulesPanel;
    private IUpdatablesPanelView updatablesPanel;

    /**
     * Construct a new ModuleView. 
     */
    public ModuleView(){
        super();

        build();
    }

    @Override
    protected void initView(){
        setTitleKey("module.view.title");
    }

    @Override
    protected void buildView(PanelBuilder builder){
        LayerTabbedPane tabbed = new LayerTabbedPane();

        modulesPanel = new ModulesPanel();
        updatablesPanel = new UpdatablesPanel();

        tabbed.addInternationalizedTab("modules.view.tab.modules", (JComponent) modulesPanel);
        tabbed.addInternationalizedTab("modules.view.tab.updatables", (JComponent) updatablesPanel);

        builder.add(tabbed, builder.gbcSet(0, 0, GridBagUtils.BOTH));
    }

    @Override
    public ModuleContainer getSelectedModule() {
        return modulesPanel.getSelectedModule();
    }

    @Override
    public Updatable getSelectedUpdatable() {
        return updatablesPanel.getSelectedUpdatable();
    }

    @Override
    public void refreshList() {
        modulesPanel.refresh();
    }
}