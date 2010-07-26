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

/**
 * A simple utility class to create filthy components.
 *
 * @author Baptiste Wicht
 */
public final class Filthy {
    private static final FilthyRenderer LIST_RENDERER = new FilthyRenderer();

    /**
     * Utility class, not instantiable.
     */
    private Filthy() {
        throw new AssertionError();
    }

    /**
     * Construct a new list.
     *
     * @param model The model of the list.
     *
     * @return The new list.
     */
    public static JList newList(ListModel model) {
        return new FilthyList(model);
    }

    /**
     * Construct a new combo box.
     *
     * @param model The model of the combo box.
     *
     * @return The new combo box.
     */
    public static JComboBox newComboBox(ComboBoxModel model) {
        return new FilthyComboBox(model);
    }

    /**
     * Construct a new Filthy panel. The used layout manager will be a FlowLayout.
     *
     * @return A new filthy panel.
     */
    public static JPanel newPanel() {
        return new FilthyPanel();
    }

    /**
     * Create a new filthy panel using the given layout manager.
     *
     * @param layoutManager The layout manager to apply.
     *
     * @return A new filthy panel with the given layout manager.
     */
    public static JPanel newPanel(LayoutManager layoutManager) {
        return new FilthyPanel(layoutManager);
    }

    /**
     * Create a new card panel.
     *
     * @param <T> The type of component in the card panel.
     *
     * @return A new card panel.
     */
    public static <T extends JComponent> CardPanel<T> newCardPanel() {
        return new FilthyCardPanel<T>();
    }

    /**
     * Create a new text field.
     *
     * @return A new text field.
     */
    public static TextField newTextField() {
        return new FilthyTextField();
    }

    /**
     * Create a new text field.
     *
     * @param columns The columns of the field.
     *
     * @return A new text field with the given number of columns.
     */
    public static TextField newTextField(int columns) {
        return new FilthyTextField(columns);
    }

    /**
     * Create a new password field.
     *
     * @return A new password field.
     */
    public static TextField newPasswordField() {
        return new FilthyPasswordField();
    }

    /**
     * Create a new formatted text field.
     *
     * @param formatter The formatter to use.
     *
     * @return The formatted text field.
     */
    public static TextField newFormattedField(DefaultFormatter formatter) {
        return new FilthyFormattedTextField(formatter);
    }

    /**
     * Create a new list renderer.
     *
     * @return A new list renderer.
     */
    public static ListCellRenderer newListRenderer() {
        return LIST_RENDERER;
    }

    /**
     * Create a new file chooser.
     *
     * @return A new file chooser.
     */
    public static FileChooser newFileChooser() {
        return new FilthyFileChooserPanel();
    }

    /**
     * Create a new file chooser.
     *
     * @param label Indicate if we must display the label of the file chooser or not.
     *
     * @return A new file chooser.
     */
    public static FileChooser newFileChooser(boolean label) {
        return new FilthyFileChooserPanel(label);
    }
}