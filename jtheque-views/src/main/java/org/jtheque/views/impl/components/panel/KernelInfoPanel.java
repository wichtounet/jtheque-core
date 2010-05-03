package org.jtheque.views.impl.components.panel;

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

import org.jtheque.core.ICore;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.filthy.BuildedPanel;
import org.jtheque.update.IUpdateService;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.impl.actions.module.UpdateKernelAction;

import javax.swing.JPanel;
import java.awt.Insets;

/**
 * A panel to display information about the kernel.
 *
 * @author Baptiste Wicht
 */
public final class KernelInfoPanel extends BuildedPanel {
    private static final int TITLE_FONT_SIZE = 16;
    private final IUpdateService updateService;

    public KernelInfoPanel(ILanguageService languageService, IUpdateService updateService) {
        super(languageService);

        this.updateService = updateService;

        build();
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        builder.setDefaultInsets(new Insets(4, 4, 4, 4));

        builder.addI18nLabel("modules.view.label.kernel", PanelBuilder.BOLD, TITLE_FONT_SIZE,
                builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.LINE_START, 1, 3));

        builder.addI18nLabel("modules.view.core.version.current", builder.gbcSet(1, 0));

        builder.addLabel(ICore.VERSION.getVersion(), builder.gbcSet(1, 1));

        builder.addI18nLabel("modules.view.core.version.online", builder.gbcSet(2, 0));

        builder.addLabel(updateService.getMostRecentCoreVersion().getVersion(), getForeground(), builder.gbcSet(2, 1));

        builder.addButton(new UpdateKernelAction(), builder.gbcSet(4, 0, GridBagUtils.NONE, GridBagUtils.LINE_START, 1, 3));
    }
}