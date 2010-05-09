package org.jtheque.views.impl.filthy;

import org.jtheque.utils.ui.PaintUtils;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.Graphics;
import java.awt.Graphics2D;

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