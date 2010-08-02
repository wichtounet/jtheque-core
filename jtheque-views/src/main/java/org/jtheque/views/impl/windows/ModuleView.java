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

import org.jtheque.i18n.able.LanguageService;
import org.jtheque.modules.able.Module;
import org.jtheque.resources.able.IResource;
import org.jtheque.ui.able.Model;
import org.jtheque.ui.able.components.LayerTabbedPane;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.windows.dialogs.SwingFilthyBuildedDialogView;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.components.ModulesPanelView;
import org.jtheque.views.able.components.ResourcePanelView;

import javax.swing.JComponent;

/**
 * A view to display the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleView extends SwingFilthyBuildedDialogView<Model> implements org.jtheque.views.able.panel.ModuleView {
    private ModulesPanelView modulesPanel;
    private ResourcePanelView resourcePanel;

    @Override
    protected void initView() {
        setTitleKey("module.view.title");
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        LayerTabbedPane tabbed = new LayerTabbedPane(getService(LanguageService.class));

        modulesPanel = getBean(ModulesPanelView.class);
        resourcePanel = getBean(ResourcePanelView.class);

        tabbed.addInternationalizedTab("modules.view.tab.modules", (JComponent) modulesPanel);
        tabbed.addInternationalizedTab("modules.view.tab.resources", (JComponent) resourcePanel);

        builder.add(tabbed, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1.0, 1.0));
    }

    @Override
    public Module getSelectedModule() {
        return modulesPanel.getSelectedModule();
    }

    @Override
    public IResource getSelectedResource() {
        return resourcePanel.getSelectedResource();
    }

    @Override
    public void refreshList() {
        modulesPanel.refresh();
    }
}