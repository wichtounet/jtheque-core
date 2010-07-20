package org.jtheque.ui.impl.components.filthy;

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

import org.jtheque.ui.able.components.TextField;

import javax.swing.JPasswordField;

/**
 * A filthy password field.
 *
 * @author Baptiste Wicht
 */
public final class FilthyPasswordField extends TextField {
    private JPasswordField passwordField;

    @Override
    protected void initComponent() {
        passwordField = new JPasswordField();

        makeFilthy(passwordField);

        add(passwordField);
    }
    
    @Override
    public String getText() {
        return new String(passwordField.getPassword());
    }

    @Override
    public void setText(String text) {
        passwordField.setText(text);
    }

    @Override
    public JPasswordField getField() {
        return passwordField;
    }
}