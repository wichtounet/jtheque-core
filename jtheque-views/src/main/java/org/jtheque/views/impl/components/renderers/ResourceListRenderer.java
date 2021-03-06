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

import org.jtheque.resources.Resource;
import org.jtheque.ui.components.Borders;
import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import java.awt.Component;
import java.awt.Font;

/**
 * A renderer to display a resource in a list.
 *
 * @author Baptiste Wicht
 */
public final class ResourceListRenderer extends JPanel implements ListCellRenderer {
    private final JLabel labelName;
    private final JLabel labelCurrentVersion;
    private final JLabel labelCurrentVersion1;

    private static final int TITLE_FONT_SIZE = 16;

    /**
     * Construct a new ResourceListRenderer.
     */
    public ResourceListRenderer() {
        super();

        I18nPanelBuilder builder = new FilthyPanelBuilder(this);
        builder.setBorder(Borders.createEmptyBorder(2, 2, 2, 10));

        labelName = builder.addLabel("", PanelBuilder.BOLD, TITLE_FONT_SIZE, builder.gbcSet(0, 0,
                GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE, 0, 1, 1.0, 0.0));

        labelCurrentVersion1 = builder.addI18nLabel("modules.view.label.versions.current", builder.gbcSet(0, 1));
        labelCurrentVersion = builder.addLabel(builder.gbcSet(1, 1));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            setChildsFont(getFont().deriveFont(Font.BOLD));
        } else {
            setChildsFont(getFont().deriveFont(Font.PLAIN));
        }

        Resource resource = (Resource) value;

        labelName.setText(resource.getId());
        labelCurrentVersion.setText(resource.getVersion().getVersion());

        return this;
    }

    /**
     * Set the font of the childs.
     *
     * @param font The font of the childs.
     */
    private void setChildsFont(Font font) {
        labelName.setFont(font);
        labelCurrentVersion.setFont(font);
        labelCurrentVersion1.setFont(font);
    }
}