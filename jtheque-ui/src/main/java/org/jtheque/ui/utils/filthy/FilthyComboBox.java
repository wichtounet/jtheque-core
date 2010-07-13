package org.jtheque.ui.utils.filthy;

import org.jtheque.ui.utils.components.Borders;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Component;

import static org.jtheque.ui.able.FilthyConstants.*;

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
 * A filthy combo box.
 *
 * @author Baptiste Wicht
 */
public final class FilthyComboBox extends JComboBox {
    /**
     * Construct a new <code>FilthyComboBox</code>.
     *
     * @param model The model to store data.
     */
    public FilthyComboBox(ComboBoxModel model) {
        super(model);

        setOpaque(false);
        setBackground(INPUT_COLOR);

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