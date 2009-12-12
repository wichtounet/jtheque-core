package org.jtheque.core.managers.view.impl.components.panel;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.update.IUpdateManager;
import org.jtheque.core.managers.view.impl.actions.module.UpdateKernelAction;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;

/**
 * A panel to display information about the kernel.
 *
 * @author Baptiste Wicht
 */
public final class KernelInfoPanel extends JPanel {
    private static final long serialVersionUID = 2153940436750024480L;
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
        PanelBuilder builder = new PanelBuilder(this);

        builder.setDefaultInsets(new Insets(4, 4, 4, 4));

        Component kernelLabel = builder.addI18nLabel("modules.view.label.kernel", builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.LINE_START, 1, 3));
        kernelLabel.setFont(kernelLabel.getFont().deriveFont(Font.BOLD, TITLE_FONT_SIZE));

        builder.addI18nLabel("modules.view.label.versions.current", builder.gbcSet(1, 0));

        builder.addLabel(Managers.getCore().getCoreCurrentVersion().getVersion(), builder.gbcSet(1, 1));

        builder.addI18nLabel("modules.view.label.versions.online", builder.gbcSet(2, 0));

        builder.addLabel(Managers.getManager(IUpdateManager.class).getMostRecentVersion(Managers.getCore()).getVersion(), getForeground(),
                builder.gbcSet(2, 1));

        builder.addButton(new UpdateKernelAction(), builder.gbcSet(4, 0, GridBagUtils.NONE, GridBagUtils.LINE_START, 1, 3));
    }
}