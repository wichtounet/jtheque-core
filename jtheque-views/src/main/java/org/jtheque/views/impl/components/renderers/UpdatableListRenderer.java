package org.jtheque.views.impl.components.renderers;

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

import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.update.IUpdateService;
import org.jtheque.update.Updatable;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.Color;
import java.awt.Component;

/**
 * A renderer to display a module in a list.
 *
 * @author Baptiste Wicht
 */
public final class UpdatableListRenderer extends JPanel implements ListCellRenderer {
    private final JLabel labelName;
    private final JLabel labelCurrentVersion;
    private final JLabel labelOnlineVersion;
    private final JLabel labelCurrentVersion1;
    private final JLabel labelOnlineVersion1;

    private static final int TITLE_FONT_SIZE = 16;

    private final IUpdateService updateService;

    /**
     * Construct a new ModuleListRenderer.
     */
    public UpdatableListRenderer(IUpdateService updateService) {
        super();

        this.updateService = updateService;

        I18nPanelBuilder builder = new FilthyPanelBuilder(this);

        labelName = builder.addLabel("", PanelBuilder.BOLD, TITLE_FONT_SIZE, builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE, 2, 1));

        labelCurrentVersion1 = builder.addI18nLabel("modules.view.label.versions.current", builder.gbcSet(0, 1));
        labelCurrentVersion = builder.addLabel(builder.gbcSet(1, 1));

        labelOnlineVersion1 = builder.addI18nLabel("modules.view.label.versions.online", builder.gbcSet(0, 2));
        labelOnlineVersion = builder.addLabel(builder.gbcSet(1, 2));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            setBackground(Color.blue);
            setChildsForeground(Color.white);
        } else {
            setBackground(Color.white);
            setChildsForeground(Color.white);
        }

        labelOnlineVersion.setBackground(getBackground());
        labelOnlineVersion.removeAll();

        Updatable updatable = (Updatable) value;

        labelName.setText(updatable.getName());
        labelCurrentVersion.setText(updatable.getVersion().getVersion());
        labelOnlineVersion.setText(updateService.getMostRecentVersion(updatable).getVersion());

        return this;
    }

    /**
     * Set the foreground of the childs.
     *
     * @param color The foreground color.
     */
    private void setChildsForeground(Color color) {
        labelName.setForeground(color);
        labelCurrentVersion.setForeground(color);
        labelCurrentVersion1.setForeground(color);
        labelOnlineVersion.setForeground(color);
        labelOnlineVersion1.setForeground(color);
    }
}