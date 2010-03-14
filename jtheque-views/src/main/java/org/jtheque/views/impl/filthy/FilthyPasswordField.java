package org.jtheque.views.impl.filthy;

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
    public void setPassword(String password){
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