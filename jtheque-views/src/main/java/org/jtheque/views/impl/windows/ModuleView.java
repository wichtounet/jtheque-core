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

import org.jtheque.i18n.ILanguageService;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.ui.able.IModel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.filthy.IFilthyUtils;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.update.IUpdateService;
import org.jtheque.update.Updatable;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.components.IModulesPanelView;
import org.jtheque.views.able.components.IUpdatablesPanelView;
import org.jtheque.views.able.panel.IModuleView;
import org.jtheque.views.impl.components.LayerTabbedPane;
import org.jtheque.views.impl.components.panel.ModulesPanel;
import org.jtheque.views.impl.components.panel.UpdatablesPanel;

import javax.swing.JComponent;

/**
 * A view to display the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleView extends SwingFilthyBuildedDialogView<IModel> implements IModuleView {
    private IModulesPanelView modulesPanel;
    private IUpdatablesPanelView updatablesPanel;

    @Override
    protected void initView(){
        setTitleKey("module.view.title");
    }

    @Override
    protected void buildView(I18nPanelBuilder builder){
        LayerTabbedPane tabbed = new LayerTabbedPane(getService(ILanguageService.class));

        modulesPanel = new ModulesPanel(getService(IUpdateService.class), getService(IModuleService.class),
                getService(ILanguageService.class), getService(IFilthyUtils.class));
        
        updatablesPanel = new UpdatablesPanel(getService(IUpdateService.class), getService(ILanguageService.class), getService(IFilthyUtils.class));

        tabbed.addInternationalizedTab("modules.view.tab.modules", (JComponent) modulesPanel);
        tabbed.addInternationalizedTab("modules.view.tab.updatables", (JComponent) updatablesPanel);

        builder.add(tabbed, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1.0, 1.0));
    }

    @Override
    public Module getSelectedModule() {
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