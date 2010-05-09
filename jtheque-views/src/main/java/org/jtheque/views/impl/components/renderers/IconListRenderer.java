package org.jtheque.views.impl.components.renderers;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

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

/**
 * A list renderer with an icon for each item.
 *
 * @author Baptiste Wicht
 */
public final class IconListRenderer extends DefaultListCellRenderer {
    /**
     * Construct a new IconListRenderer.
     *
     * @param labelIcon The label icon.
     */
    public IconListRenderer(Icon labelIcon){
        super();

        setOpaque(false);
        setIcon(labelIcon);
        setForeground(Color.white);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        if (isSelected){
            setFont(getFont().deriveFont(Font.BOLD));
        } else {
            setFont(getFont().deriveFont(Font.PLAIN));
        }

        setText(value.toString());

        return this;
    }
}
