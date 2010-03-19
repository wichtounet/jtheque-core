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
import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.update.IUpdateService;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.impl.actions.module.UpdateKernelAction;

import javax.swing.JPanel;
import java.awt.Insets;

/**
 * A panel to display information about the kernel.
 *
 * @author Baptiste Wicht
 */
public final class KernelInfoPanel extends JPanel {
    private static final int TITLE_FONT_SIZE = 16;

    /**
     * Construct a new KernelInfoPanel.
     */
    public KernelInfoPanel() {
        super();

        build();
    }

    /**
     * Build the GUI.
     */
    private void build() {
        I18nPanelBuilder builder = new FilthyPanelBuilder(this);

        builder.setDefaultInsets(new Insets(4, 4, 4, 4));

        builder.addI18nLabel("modules.view.label.kernel", PanelBuilder.BOLD, TITLE_FONT_SIZE,
                builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.LINE_START, 1, 3));

        builder.addI18nLabel("modules.view.core.version.current", builder.gbcSet(1, 0));

        builder.addLabel(ICore.VERSION.getVersion(), builder.gbcSet(1, 1));

        builder.addI18nLabel("modules.view.core.version.online", builder.gbcSet(2, 0));

        builder.addLabel(ViewsServices.get(IUpdateService.class).getMostRecentVersion(ViewsServices.get(ICore.class)).getVersion(), getForeground(),
                builder.gbcSet(2, 1));

        builder.addButton(new UpdateKernelAction(), builder.gbcSet(4, 0, GridBagUtils.NONE, GridBagUtils.LINE_START, 1, 3));
    }
}