package org.jtheque.ui.impl.components.filthy;

import org.jtheque.ui.able.components.TextField;

import javax.swing.JTextField;

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
 * A filthy text field.
 *
 * @author Baptiste Wicht
 */
public final class FilthyTextField extends TextField {
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
    protected void initComponent() {
        textField = new JTextField();

        makeFilthy(textField);

        add(textField);
    }

    @Override
    public JTextField getField() {
        return textField;
    }
}
