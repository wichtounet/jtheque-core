package org.jtheque.ui.able.components.filthy;

import org.jtheque.ui.able.components.CardPanel;
import org.jtheque.ui.able.components.FileChooser;
import org.jtheque.ui.able.components.TextField;
import org.jtheque.ui.impl.components.filthy.FilthyCardPanel;
import org.jtheque.ui.impl.components.filthy.FilthyComboBox;
import org.jtheque.ui.impl.components.filthy.FilthyFileChooserPanel;
import org.jtheque.ui.impl.components.filthy.FilthyFormattedTextField;
import org.jtheque.ui.impl.components.filthy.FilthyList;
import org.jtheque.ui.impl.components.filthy.FilthyPanel;
import org.jtheque.ui.impl.components.filthy.FilthyPasswordField;
import org.jtheque.ui.impl.components.filthy.FilthyRenderer;
import org.jtheque.ui.impl.components.filthy.FilthyTextField;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.text.DefaultFormatter;

import java.awt.LayoutManager;

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

public class Filthy {
    private Filthy() {
        throw new AssertionError();
    }

    public static JList newList(ListModel model){
        return new FilthyList(model);
    }

    public static JComboBox newComboBox(ComboBoxModel model) {
        return new FilthyComboBox(model);
    }

    public static JPanel newPanel() {
        return new FilthyPanel();
    }

    public static JPanel newPanel(LayoutManager layoutManager) {
        return new FilthyPanel(layoutManager);
    }

    public static <T extends JComponent> CardPanel<T> newCardPanel() {
        return new FilthyCardPanel<T>();
    }

    public static TextField newTextField() {
        return new FilthyTextField();
    }

    public static TextField newTextField(int columns) {
        return new FilthyTextField(columns);
    }

    public static TextField newPasswordField() {
        return new FilthyPasswordField();
    }

    public static TextField newFormattedField(DefaultFormatter formatter) {
        return new FilthyFormattedTextField(formatter);
    }

    public static ListCellRenderer newListRenderer() {
        return new FilthyRenderer();
    }

    public static FileChooser newFileChooserPanel() {
        return new FilthyFileChooserPanel();
    }

    public static FileChooser newFileChooserPanel(boolean label) {
        return new FilthyFileChooserPanel(label);
    }
}