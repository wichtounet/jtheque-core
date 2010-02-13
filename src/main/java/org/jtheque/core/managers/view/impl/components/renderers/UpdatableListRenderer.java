package org.jtheque.core.managers.view.impl.components.renderers;

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
import org.jtheque.core.managers.update.Updatable;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.ui.builders.FilthyPanelBuilder;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
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

    /**
     * Construct a new ModuleListRenderer.
     */
    public UpdatableListRenderer() {
        super();

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
            setBackground(Managers.getManager(IViewManager.class).getViewDefaults().getSelectedBackgroundColor());
            setChildsForeground(Managers.getManager(IViewManager.class).getViewDefaults().getSelectedForegroundColor());
        } else {
            setBackground(Managers.getManager(IViewManager.class).getViewDefaults().getBackgroundColor());
            setChildsForeground(Managers.getManager(IViewManager.class).getViewDefaults().getForegroundColor());
        }

        labelOnlineVersion.setBackground(getBackground());
        labelOnlineVersion.removeAll();

        Updatable updatable = (Updatable) value;

        labelName.setText(updatable.getName());
        labelCurrentVersion.setText(updatable.getVersion().getVersion());
        labelOnlineVersion.setText(Managers.getManager(IUpdateManager.class).getMostRecentVersion(updatable).getVersion());

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