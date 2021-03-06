package org.jtheque.views.impl.components.panel;

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

import org.jtheque.core.Core;
import org.jtheque.i18n.LanguageService;
import org.jtheque.ui.FilthyUtils;
import org.jtheque.ui.utils.builded.FilthyBuildedPanel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.updates.UpdateService;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.panel.ModuleView;

import java.awt.Insets;

/**
 * A panel to display information about the kernel.
 *
 * @author Baptiste Wicht
 */
public final class KernelInfoPanel extends FilthyBuildedPanel {
    private static final int TITLE_FONT_SIZE = 16;

    private final UpdateService updateService;
    private final ModuleView moduleView;

    /**
     * Construct a new KernelInfoPanel.
     *
     * @param languageService The language service.
     * @param filthyUtils     The filthy utils.
     * @param updateService   The update service.
     * @param moduleView      The module view.
     */
    public KernelInfoPanel(LanguageService languageService, FilthyUtils filthyUtils, UpdateService updateService,
                           ModuleView moduleView) {
        super(filthyUtils, languageService);

        this.updateService = updateService;
        this.moduleView = moduleView;

        build();

        setOpaque(false);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        builder.setDefaultInsets(new Insets(4, 4, 4, 4));

        builder.addI18nLabel("modules.view.label.kernel", PanelBuilder.BOLD, TITLE_FONT_SIZE,
                builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.LINE_START, 1, 3));

        builder.addI18nLabel("modules.view.core.version.current", builder.gbcSet(1, 0));

        builder.addLabel(Core.VERSION.getVersion(), builder.gbcSet(1, 1));

        builder.addI18nLabel("modules.view.core.version.online", builder.gbcSet(2, 0));

        Version version = updateService.getMostRecentCoreVersion();
        if (version == null) {
            builder.addLabel("", getForeground(), builder.gbcSet(2, 1));
        } else {
            builder.addLabel(version.getVersion(), getForeground(), builder.gbcSet(2, 1));
        }

        builder.addButton(moduleView.getAction("modules.actions.update.kernel"),
                builder.gbcSet(4, 0, GridBagUtils.NONE, GridBagUtils.LINE_START, 1, 3));
    }
}