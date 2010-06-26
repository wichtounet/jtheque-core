package org.jtheque.views.impl.components.renderers;

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

import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.views.impl.components.panel.ModulePanel;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * A renderer to display a module in a list.
 *
 * @author Baptiste Wicht
 */
public final class ModuleRepositoryListRenderer implements ListCellRenderer {
    private final Map<Integer, ModulePanel> panels;
    private final IModuleService moduleService;
    private final ILanguageService languageService;
    private final IUpdateService updateService;

    /**
     * Construct a new ModuleListRenderer.
     */
    public ModuleRepositoryListRenderer(IModuleService moduleService, ILanguageService languageService,
                                        IUpdateService updateService) {
        super();

        this.moduleService = moduleService;
        this.languageService = languageService;
        this.updateService = updateService;

        panels = new HashMap<Integer, ModulePanel>(10);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (panels.containsKey(index)) {
            panels.get(index).updateUI(isSelected);
        } else {
            panels.put(index, new ModulePanel(value, isSelected, moduleService, languageService, updateService));
        }

        return panels.get(index);
    }

}