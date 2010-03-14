package org.jtheque.views.impl.filthy;

import org.jtheque.utils.ui.PaintUtils;

import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
 * A filthy text field.
 *
 * @author Baptiste Wicht
 */
public final class FilthyTextField extends AbstractFilthyField {
    private JTextField textField;

    /**
     * Construct a new <code>FilthyTextField</code>.
     */
    public FilthyTextField() {
        this(10);
    }

    /**
     * Construct a new <code>FilthyTextField</code>.
     *
     * @param columns The number of columns.
     */
    public FilthyTextField(int columns) {
        super();

        textField.setColumns(columns);
    }

    @Override
    void initComponent() {
        textField = new JTextField();
        
        makeFilthy(textField);

        add(textField);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        textField.setEnabled(enabled);
    }

    /**
     * Return the entered text.
     *
     * @return The entered text.
     */
    public String getText() {
        return textField.getText();
    }

    /**
     * Set the text.
     *
     * @param t The text.
     */
    public void setText(String t) {
        textField.setText(t);
    }

    /**
     * Return the text field.
     *
     * @return The text field.
     */
    public JTextField getField() {
        return textField;
    }

    @Override
    public void paint(Graphics g) {
        PaintUtils.initHints((Graphics2D) g);

        super.paint(g);
    }
}
