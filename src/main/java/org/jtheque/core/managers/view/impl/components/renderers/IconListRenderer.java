package org.jtheque.core.managers.view.impl.components.renderers;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

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
