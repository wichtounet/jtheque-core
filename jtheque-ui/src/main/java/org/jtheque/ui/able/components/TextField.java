package org.jtheque.ui.able.components;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.text.JTextComponent;

import java.awt.BorderLayout;
import java.awt.Color;

import static org.jtheque.ui.able.components.filthy.FilthyConstants.*;

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

public abstract class TextField extends JPanel {
    /**
     * Construct a new AbstractFilthyField.
     */
    public TextField() {
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
    protected abstract void initComponent();

    /**
     * Make a component filthy.
     *
     * @param field The field to make filthy.
     */
    protected static void makeFilthy(JTextComponent field) {
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

    /**
     * Return the entered text.
     *
     * @return The entered text.
     */
    public abstract String getText();

    /**
     * Set the text.
     *
     * @param t The text.
     */
    public abstract void setText(String t);

    /**
     * Return the text field.
     *
     * @return The text field.
     */
    public abstract JTextField getField();
}