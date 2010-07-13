package org.jtheque.ui.utils.filthy;

import org.jtheque.ui.utils.components.Borders;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.border.Border;

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
 * A filthy renderer for this combo box.
 *
 * @author Baptiste Wicht
 */
public final class FilthyRenderer extends DefaultListCellRenderer {
    private final Color selectionBackground = new Color(170, 170, 170);
    private final Border border;

    /**
     * Construct a new FilthyRenderer.
     */
    public FilthyRenderer() {
        super();

        border = Borders.createEmptyBorder(0, 0, 0, 0);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        setBorder(border);
        setForeground(Color.white);
        setOpaque(true);

        if (isSelected) {
            setBackground(selectionBackground);
        } else {
            setBackground(INPUT_COLOR);
        }

        return this;
    }
}