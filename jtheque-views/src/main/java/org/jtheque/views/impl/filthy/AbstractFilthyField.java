package org.jtheque.views.impl.filthy;

import org.jtheque.ui.utils.components.Borders;
import org.jtheque.ui.utils.filthy.Filthy;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.Color;

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
 * An abstract filthy field.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractFilthyField extends JPanel implements Filthy {
    /**
     * Construct a new AbstractFilthyField.
     */
    public AbstractFilthyField() {
        super();

        setLayout(new BorderLayout());
        setBackground(INPUT_COLOR);
        setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(INPUT_BORDER_COLOR, 2),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));

        initComponent();
    }

    /**
     * Init the component.
     */
    abstract void initComponent();

    /**
     * Make a component filthy.
     *
     * @param field The field to make filthy.
     */
    static void makeFilthy(JTextComponent field) {
        Color inputTextColor = FOREGROUND_COLOR;
        Color inputSelectionColor = FOREGROUND_COLOR;
        Color inputSelectedTextColor = BACKGROUND_COLOR;

        field.setOpaque(false);
        field.setBorder(Borders.EMPTY_BORDER);
        field.setForeground(inputTextColor);
        field.setSelectedTextColor(inputSelectedTextColor);
        field.setSelectionColor(inputSelectionColor);
        field.setCaretColor(inputSelectionColor);
        field.setFont(INPUT_FONT);
    }
}