package org.jtheque.core.managers.view.impl.components.filthy;

import org.jtheque.utils.ui.PaintUtils;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
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
 * A filthy formatted text field.
 *
 * @author Baptiste Wicht
 */
public final class FilthyFormattedTextField extends AbstractFilthyField {
    private final JFormattedTextField textField;

    /**
     * Construct a new FilthyFormattedTextField.
     *
     * @param formatter The formatter for the display of the text field.
     */
    public FilthyFormattedTextField(MaskFormatter formatter) {
        super();

        textField = new JFormattedTextField(formatter);

        initComponent();
    }

    /**
     * Construct a new FilthyFormattedTextField with a certain formatter.
     *
     * @param formatter The number formatter to use to format the field.
     */
    public FilthyFormattedTextField(NumberFormatter formatter) {
        super();

        textField = new JFormattedTextField(formatter);

        initComponent();
    }

    @Override
    void initComponent() {
        if(textField != null){
            makeFilthy(textField);

            add(textField);
        }
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
    public JFormattedTextField getField() {
        return textField;
    }

    @Override
    public void paint(Graphics g) {
        PaintUtils.initHints((Graphics2D) g);

        super.paint(g);
    }
}