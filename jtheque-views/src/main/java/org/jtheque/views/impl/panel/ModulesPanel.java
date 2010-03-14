package org.jtheque.views.impl.panel;

import org.jtheque.modules.able.Module;
import org.jtheque.ui.IUIUtils;
import org.jtheque.ui.utils.ActionFactory;
import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.components.FilthyBackgroundPanel;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.ViewsServices;
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
public final class ModulesPanel extends FilthyBackgroundPanel implements IModulesPanelView {
    private final JList modulesList;

    /**
     * Construct a new ModulesPanel. 
     */
    public ModulesPanel(){
        super();

        PanelBuilder builder = new FilthyPanelBuilder(this);

        builder.add(new KernelInfoPanel(), builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0));

        modulesList = builder.addScrolledList(new ModuleListModel(), new ModuleListRenderer(), builder.gbcSet(0, 1, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1.0, 1.0));
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
        ViewsServices.get(IUIUtils.class).getDelegate().refresh(modulesList);
    }
}