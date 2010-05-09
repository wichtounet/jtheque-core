package org.jtheque.ui.utils.builders;

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

import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.border.Border;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.LayoutManager;

public interface PanelBuilder {
    int NORMAL = 0;
    int BOLD = 1;
    int ITALIC = 2;

    /**
     * Add a component to the panel with the specified constraints.
     *
     * @param component   The component to add.
     * @param constraints The constraints to use to add to the panel.
     * @param <T>         The type of component.
     * @return The added component.
     */
    <T extends Component> T add(T component, Object constraints);

    /**
     * Add a scrolled component.
     *
     * @param view        The view.
     * @param constraints The constraints to use to add to the panel.
     *
     * @return The component added inside the JScrollPane.
     */
    <T extends Component> T addScrolled(T view, Object constraints);

    /**
     * Add a combo box.
     *
     * @param model       The combo box model.
     * @param constraints The constraints to use to add to the panel.
     * @return The added JComboBox.
     */
    JComboBox addComboBox(DefaultComboBoxModel model, Object constraints);

    /**
     * Add a combo box with a specific renderer.
     *
     * @param model       The combo box model.
     * @param renderer    The renderer to use.
     * @param constraints The constraints to use to add to the panel.
     * @return The added JComboBox.
     */
    JComboBox addComboBox(DefaultComboBoxModel model, ListCellRenderer renderer, Object constraints);

    /**
     * Add a <code>Jlist</code> with the specified model.
     *
     * @param model       The model to use.
     * @param constraints The constraints to use to add to the panel.
     *
     * @return The added JList.
     */
    JList addList(ListModel model, Object constraints);

    /**
     * Add a <code>Jlist</code> with the specified model.
     *
     * @param model       The model to use.
     * @param renderer    The list renderer.
     * @param constraints The constraints to use to add to the panel.
     *
     * @return The added JList.
     */
    JList addList(ListModel model, ListCellRenderer renderer, Object constraints);

    /**
     * Add a <code>Jlist</code> with the specified model into a JScrollpane and return the list.
     *
     * @param model       The model to use.
     * @param constraints The constraints to use to add to the panel.
     *
     * @return The added JList.
     */
    JList addScrolledList(ListModel model, Object constraints);

    /**
     * Add a <code>Jlist</code> with the specified model into a JScrollpane and return the list.
     *
     * @param model       The model to use.
     * @param renderer    The list renderer.
     * @param constraints The constraints to use to add to the panel.
     *
     * @return The added JList.
     */
    JList addScrolledList(ListModel model, ListCellRenderer renderer, Object constraints);

    /**
     * Add a button to the panel.
     *
     * @param action      The action to use with the button.
     * @param constraints The constraints to use to add to the panel.
     * @return The added button.
     */
    JButton addButton(Action action, Object constraints);

    /**
     * Add a button to the panel.
     *
     * @param text          The action to set on the button.
     * @param constraints   The constraints to use to add to the panel.
     * @return The added button.
     */
    JButton addButton(String text, Object constraints);

    /**
     * Add a label to the panel.
     *
     * @param constraints The constraints to use to add to the panel.
     *
     * @return The added label.
     */
    JLabel addLabel(Object constraints);

    /**
     * Add a label to the panel.
     *
     * @param text        The text of the label.
     * @param constraints The constraints to use to add to the panel.
     * @return The added label.
     */
    JLabel addLabel(String text, Object constraints);

    /**
     * Add a label with a specific foreground to the panel.
     *
     * @param text        The text of the label.
     * @param foreground  The foreground color.
     * @param constraints The constraints to use to add to the panel.
     * @return The added label.
     */
    JLabel addLabel(String text, Color foreground, Object constraints);

    /**
     * Add a label with a specified style to the panel.
     *
     * @param text         The text.
     * @param constraints The constraints to use to add to the panel.
     * @param style       The font style.
     * @return The added label.
     */
    JLabel addLabel(String text, int style, Object constraints);

    /**
     * Add a label with a specified style to the panel.
     *
     * @param text         The text.
     * @param constraints The constraints to use to add to the panel.
     * @param style       The font style.
     * @param size        The font size.
     *
     * @return The added label.
     */
    JLabel addLabel(String text, int style, float size, Object constraints);

    /**
     * Add a checkbox to the panel.
     *
     * @param text The text of the check box.
     * @param constraints The constraints to add to the panel.
     *
     * @return The added checkbox.
     */
    JCheckBox addCheckbox(String text, Object constraints);

    JTable addTable(TableModel model, Object constraints);
    JTable addScrolledTable(TableModel model, Object constraints);

    /**
     * Add a text area.
     *
     * @param constraints The constraints to use to add to the panel.
     *
     * @return The added text area.
     */
    JTextArea addTextArea(Object constraints);

    /**
     * Add a text area.
     *
     * @param text        The text of the text area.
     * @param constraints The constraints to use to add to the panel.
     *
     * @return The added text area.
     */
    JTextArea addTextArea(String text, Object constraints);

    /**
     * Add a scrolled text area.
     *
     * @param constraints The constraints to use to add to the panel.
     *
     * @return The added text area.
     */
    JTextArea addScrolledTextArea(Object constraints);

    /**
     * Add a scrolled text area.
     *
     * @param text        The text of the text area.
     * @param constraints The constraints to use to add to the panel.
     *
     * @return The added text area.
     */
    JTextArea addScrolledTextArea(String text, Object constraints);

    /**
     * Add a JTree in a JScrollpane
     *
     * @param model The tree model.
     * @param renderer The tree renderer.
     * @param constraints The constraints to add the tree with.
     *
     * @return The added tree.
     */
    JTree addScrolledTree(TreeModel model, TreeCellRenderer renderer, Object constraints);

    /**
     * Add a button bar.
     *
     * @param constraints The constraints to use to add to the panel.
     * @param actions     The actions to add to the button bar.
     */
    void addButtonBar(Object constraints, Action... actions);

    /**
     * Add a panel to the panel.
     *
     * @param constraints The constraints to use to add to the panel.
     * @return The builder of the intern panel builder.
     */
    PanelBuilder addPanel(Object constraints);

    /**
     * Add a panel to the panel.
     *
     * @param layout The layout to use.
     * @param constraints The constraints to use to add to the panel.
     *
     * @return The builder of the intern panel builder.
     */
    PanelBuilder addPanel(LayoutManager layout, Object constraints);

    /**
     * Return the builded panel.
     *
     * @return The builded panel.
     */
    JComponent getPanel();

    /**
     * Set the border of the panel.
     *
     * @param border The border to set to the panel.
     */
    void setBorder(Border border);

    /**
     * Set title border to the panel.
     *
     * @param text The text of the border.
     */
    void setTitleBorder(String text);

    /**
     * Set the default insets.
     *
     * @param defaultInsets The default insets.
     */
    void setDefaultInsets(Insets defaultInsets);

    /**
     * Create a new layout constraints.
     *
     * @param x       The x position on the grid.
     * @param y       The y position on the grid.
     * @param fill    The fill constraint.
     * @param anchor  The anchor constraint.
     * @param weightx The weight in x axis.
     * @param weighty The weight in y axis.
     * @return The layout constraints.
     */
    Object gbcSet(int x, int y, int fill, int anchor, double weightx, double weighty);

    /**
     * Create a new layout constraints.
     *
     * @param x       The x position on the grid.
     * @param y       The y position on the grid.
     * @param fill    The fill constraint.
     * @param anchor  The anchor constraint.
     * @param width   The col span.
     * @param height  The row span.
     * @param weightx The weight in x axis.
     * @param weighty The weight in y axis.
     * @return The layout constraints.
     */
    Object gbcSet(int x, int y, int fill, int anchor, int width, int height, double weightx, double weighty);

    /**
     * Configure and return the GridBagConstraints object.
     *
     * @param x       The x position of the component.
     * @param y       The y position of the component.
     * @param fill    The fill constraints.
     * @param anchor  The anchor of the component.
     * @param width   The col span.
     * @param height  The row span.
     * @param weightx The col fill weight.
     * @param weighty The row fill weight.
     * @param ipadx   The x internal padding width.
     * @param ipady   The y internal padding height.
     * @return The <code>GridBagConstraints</code> object.
     */
    Object gbcSet(int x, int y, int fill, int anchor, int width, int height, double weightx, double weighty, int ipadx, int ipady);

    /**
     * Create a new layout constraints.
     *
     * @param x      The x position on the grid.
     * @param y      The y position on the grid.
     * @param fill   The fill constraint.
     * @param anchor The anchor constraint.
     * @param width  The col span.
     * @param height The row span.
     * @return The layout constraints.
     */
    Object gbcSet(int x, int y, int fill, int anchor, int width, int height);

    /**
     * Create a new layout constraints.
     *
     * @param x      The x position on the grid.
     * @param y      The y position on the grid.
     * @param fill   The fill constraint.
     * @param width  The col span.
     * @param height The row span.
     * @return The layout constraints.
     */
    Object gbcSet(int x, int y, int fill, int width, int height);

    /**
     * Create a new layout constraints.
     *
     * @param x      The x position on the grid.
     * @param y      The y position on the grid.
     * @param fill   The fill constraint.
     * @param anchor The anchor constraint.
     * @return The layout constraints.
     */
    Object gbcSet(int x, int y, int fill, int anchor);

    /**
     * Create a new layout constraints.
     *
     * @param x    The x position on the grid.
     * @param y    The y position on the grid.
     * @param fill The fill constraint.
     * @return The layout constraints.
     */
    Object gbcSet(int x, int y, int fill);

    /**
     * Create a new layout constraints.
     *
     * @param x The x position on the grid.
     * @param y The y position on the grid.
     * @return The layout constraints.
     */
    Object gbcSet(int x, int y);
}
