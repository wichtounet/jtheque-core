package org.jtheque.views.impl.components.panel;

import org.jtheque.i18n.able.LanguageService;
import org.jtheque.modules.able.ModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.ui.able.FilthyUtils;
import org.jtheque.ui.utils.builded.OSGIFilthyBuildedPanel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.update.able.UpdateService;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.components.ModulesPanelView;
import org.jtheque.views.able.panel.ModuleView;
import org.jtheque.views.impl.components.renderers.ModuleListRenderer;
import org.jtheque.views.impl.models.ModuleListModel;

import javax.swing.JList;

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

/**
 * A panel to display all the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModulesPanel extends OSGIFilthyBuildedPanel implements ModulesPanelView {
    private JList modulesList;

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        UpdateService updateService = getService(UpdateService.class);
        LanguageService languageService = getService(LanguageService.class);
        ModuleService moduleService = getService(ModuleService.class);

        ModuleView moduleView = getBean(ModuleView.class);

        builder.add(new KernelInfoPanel(languageService, getService(FilthyUtils.class), updateService,
                moduleView),
                builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0));

        modulesList = builder.addScrolledList(
                new ModuleListModel(moduleService),
                new ModuleListRenderer(updateService, languageService),
                builder.gbcSet(0, 1, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 1.0, 1.0));
        modulesList.setVisibleRowCount(4);

        builder.addButtonBar(builder.gbcSet(0, 2, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0),
                moduleView.getAction("modules.actions.stop"),
                moduleView.getAction("modules.actions.start"),
                moduleView.getAction("modules.actions.activate"),
                moduleView.getAction("modules.actions.desactivate"));

        builder.addButtonBar(builder.gbcSet(0, 3, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 1.0, 0.0),
                moduleView.getAction("modules.actions.url.new"),
                moduleView.getAction("modules.actions.file.new"),
                moduleView.getAction("modules.actions.uninstall"),
                moduleView.getAction("modules.actions.update"),
                moduleView.getAction("modules.actions.repository"));
    }

    @Override
    public Module getSelectedModule() {
        return (Module) modulesList.getSelectedValue();
    }

    @Override
    public void refresh() {
        SwingUtils.refresh(modulesList);
    }
}