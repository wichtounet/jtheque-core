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

import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.update.able.Updatable;
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
     *
     * @param updateService The update service.
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