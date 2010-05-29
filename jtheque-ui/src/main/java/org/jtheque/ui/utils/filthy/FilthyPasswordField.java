package org.jtheque.ui.utils.filthy;

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

import javax.swing.JPasswordField;

/**
 * A filthy password field.
 *
 * @author Baptiste Wicht
 */
public final class FilthyPasswordField extends AbstractFilthyField {
    private JPasswordField passwordField;

    @Override
    void initComponent() {
        passwordField = new JPasswordField();

        makeFilthy(passwordField);

        add(passwordField);
    }

    /**
     * Return the entered password.
     *
     * @return The entered password.
     */
    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    /**
     * Set the password of the field.
     *
     * @param password The password.
     */
    public void setPassword(String password) {
        passwordField.setText(password);
    }

    /**
     * Return the real field of this filty component.
     *
     * @return The field of this filthy component.
     */
    public JPasswordField getField() {
        return passwordField;
    }
}