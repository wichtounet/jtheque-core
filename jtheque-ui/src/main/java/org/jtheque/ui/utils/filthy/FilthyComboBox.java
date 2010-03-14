package org.jtheque.ui.utils.filthy;

import org.jtheque.ui.utils.Borders;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Component;

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
 * A filthy combo box.
 *
 * @author Baptiste Wicht
 */
public final class FilthyComboBox extends JComboBox implements Filthy {
    /**
     * Construct a new <code>FilthyComboBox</code>.
     *
     * @param model The model to store data.
     */
    public FilthyComboBox(ComboBoxModel model) {
        super(model);

        setOpaque(false);
        setBackground(inputColor);

        /* Doesn't work with Substance
            setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(resources.getColor("filthyInputBorderColor"), 2),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));*/

        UIManager.put("ComboBox.selectionBackground", new Color(0, 0, 0, 0));

        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) instanceof AbstractButton) {
                ((AbstractButton) getComponent(i)).setBorderPainted(false);
            }
        }

        setRenderer(new FilthyRenderer());
    }

    /**
     * A basic renderer for this combo box.
     *
     * @author Baptiste Wicht
     */
    private static final class FilthyRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            setBorder(Borders.createEmptyBorder(0, 0, 0, 0));
            setForeground(Color.white);
            setOpaque(false);

            return this;
        }
    }
}