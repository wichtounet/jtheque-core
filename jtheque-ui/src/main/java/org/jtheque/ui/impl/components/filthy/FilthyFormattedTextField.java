package org.jtheque.ui.impl.components.filthy;

import org.jtheque.ui.able.components.TextField;
import org.jtheque.utils.ui.PaintUtils;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatter;

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
public final class FilthyFormattedTextField extends TextField {
    private final JFormattedTextField textField;

    /**
     * Construct a new FilthyFormattedTextField.
     *
     * @param formatter The formatter for the display of the text field.
     */
    public FilthyFormattedTextField(DefaultFormatter formatter) {
        super();

        textField = new JFormattedTextField(formatter);

        initComponent();
    }

    @Override
    protected void initComponent() {
        if (textField != null) {
            makeFilthy(textField);

            add(textField);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        textField.setEnabled(enabled);
    }

    @Override
    public String getText() {
        return textField.getText();
    }

    @Override
    public void setText(String t) {
        textField.setText(t);
    }

    @Override
    public JTextField getField() {
        return textField;
    }

    @Override
    public void paint(Graphics g) {
        PaintUtils.initHints((Graphics2D) g);

        super.paint(g);
    }
}