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

import org.jtheque.core.able.ICore;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.ui.able.IFilthyUtils;
import org.jtheque.ui.utils.builded.FilthyBuildedPanel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.impl.actions.module.UpdateKernelAction;

import java.awt.Insets;

/**
 * A panel to display information about the kernel.
 *
 * @author Baptiste Wicht
 */
public final class KernelInfoPanel extends FilthyBuildedPanel {
    private static final int TITLE_FONT_SIZE = 16;
    private final IUpdateService updateService;

    /**
     * Construct a new KernelInfoPanel.
     *
     * @param languageService The language service.
     * @param filthyUtils     The filthy utils.
     * @param updateService   The update service.
     */
    public KernelInfoPanel(ILanguageService languageService, IFilthyUtils filthyUtils, IUpdateService updateService) {
        super(filthyUtils, languageService);

        this.updateService = updateService;

        build();

        setOpaque(false);
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        builder.setDefaultInsets(new Insets(4, 4, 4, 4));

        builder.addI18nLabel("modules.view.label.kernel", PanelBuilder.BOLD, TITLE_FONT_SIZE,
                builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.LINE_START, 1, 3));

        builder.addI18nLabel("modules.view.core.version.current", builder.gbcSet(1, 0));

        builder.addLabel(ICore.VERSION.getVersion(), builder.gbcSet(1, 1));

        builder.addI18nLabel("modules.view.core.version.online", builder.gbcSet(2, 0));

        Version version = updateService.getMostRecentCoreVersion();
        if (version == null) {
            builder.addLabel("", getForeground(), builder.gbcSet(2, 1));
        } else {
            builder.addLabel(version.getVersion(), getForeground(), builder.gbcSet(2, 1));
        }

        builder.addButton(new UpdateKernelAction(), builder.gbcSet(4, 0, GridBagUtils.NONE, GridBagUtils.LINE_START, 1, 3));
    }
}