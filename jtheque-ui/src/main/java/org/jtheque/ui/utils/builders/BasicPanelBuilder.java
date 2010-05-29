package org.jtheque.ui.utils.builders;

import org.jtheque.utils.ui.ButtonBarBuilder;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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
 * A basic PanelBuilder. It seems a panel with no internationalization and that works only with basic swing
 * components.
 *
 * @author Baptiste Wicht
 */
public class BasicPanelBuilder extends AbstractPanelBuilder {
    /**
     * Construct a new JThequePanelBuilder.
     */
    public BasicPanelBuilder() {
        super();
    }

    /**
     * Construct a new panel builder.
     *
     * @param panel The panel to build.
     */
    public BasicPanelBuilder(JPanel panel) {
        super(panel);
    }

    /**
     * Construct a new JThequePanelBuilder with a specified layout.
     *
     * @param layout The layout to set to the builded panel.
     */
    public BasicPanelBuilder(LayoutManager layout) {
        super(layout);
    }

    /**
     * Construct a new JThequePanelBuilder.
     *
     * @param panel  The panel to build.
     * @param layout If true set a default layout (GridBagLayout) to the builded panel.
     */
    public BasicPanelBuilder(JPanel panel, boolean layout) {
        super(panel, layout);
    }

    @Override
    void initDefaults() {
        //Nothing by default
    }

    @Override
    public <T extends Component> T addScrolled(T component, Object constraints) {
        JScrollPane scrollPane = new JScrollPane(component);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, constraints);

        return component;
    }

    @Override
    public JList addList(ListModel model, Object constraints) {
        return addList(model, null, constraints);
    }

    @Override
    public JList addList(ListModel model, ListCellRenderer renderer, Object constraints) {
        return add(createJList(model, renderer), constraints);
    }

    @Override
    public JList addScrolledList(ListModel model, Object constraints) {
        return addScrolledList(model, null, constraints);
    }

    @Override
    public JList addScrolledList(ListModel model, ListCellRenderer renderer, Object constraints) {
        return addScrolled(createJList(model, renderer), constraints);
    }

    /**
     * Create a JList with the specified parameters.
     *
     * @param model    The model of the list.
     * @param renderer The renderer. Can be null.
     * @return The created JList.
     */
    private static JList createJList(ListModel model, ListCellRenderer renderer) {
        JList list = new JList(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(12);
        list.setValueIsAdjusting(true);

        if (renderer != null) {
            list.setCellRenderer(renderer);
        }

        return list;
    }

    @Override
    public JButton addButton(Action action, Object constraints) {
        return add(new JButton(action), constraints);
    }

    @Override
    public JButton addButton(String text, Object constraints) {
        return add(new JButton(text), constraints);
    }

    @Override
    public JLabel addLabel(Object constraints) {
        return add(new JLabel(), constraints);
    }

    @Override
    public JLabel addLabel(String text, Object constraints) {
        return add(new JLabel(text), constraints);
    }

    @Override
    public JLabel addLabel(String text, Color foreground, Object constraints) {
        JLabel label = addLabel(text, constraints);

        label.setForeground(foreground);

        return label;
    }

    @Override
    public JLabel addLabel(String text, int style, Object constraints) {
        JLabel label = new JLabel(text);

        applyStyle(style, label);

        return add(label, constraints);
    }

    @Override
    public JLabel addLabel(String text, int style, float size, Object constraints) {
        JLabel label = addLabel(text, style, constraints);

        label.setFont(label.getFont().deriveFont(size));

        return label;
    }

    /**
     * Apply the specified style to the component.
     *
     * @param style     The font style to apply.
     * @param component The component to apply the style on.
     * @see PanelBuilder#BOLD
     * @see PanelBuilder#ITALIC
     */
    static void applyStyle(int style, Component component) {
        if ((style & BOLD) == BOLD) {
            component.setFont(component.getFont().deriveFont(Font.BOLD));
        }

        if ((style & ITALIC) == ITALIC) {
            component.setFont(component.getFont().deriveFont(Font.ITALIC));
        }
    }

    @Override
    public JComboBox addComboBox(ComboBoxModel model, Object constraints) {
        return add(new JComboBox(model), constraints);
    }

    @Override
    public JComboBox addComboBox(ComboBoxModel model, ListCellRenderer renderer, Object constraints) {
        JComboBox combo = addComboBox(model, constraints);

        combo.setRenderer(renderer);

        return combo;
    }

    @Override
    public JTextArea addTextArea(Object constraints) {
        return add(new JTextArea(), constraints);
    }

    @Override
    public JTextArea addTextArea(String text, Object constraints) {
        return add(new JTextArea(text), constraints);
    }

    @Override
    public JTextArea addScrolledTextArea(Object constraints) {
        return addScrolled(new JTextArea(), constraints);
    }

    @Override
    public JTextArea addScrolledTextArea(String text, Object constraints) {
        return addScrolled(new JTextArea(text), constraints);
    }

    @Override
    public JTree addScrolledTree(TreeModel model, TreeCellRenderer renderer, Object constraints) {
        JTree tree = new JTree(model);

        if (renderer != null) {
            tree.setCellRenderer(renderer);
        }

        return addScrolled(tree, constraints);
    }

    @Override
    public void addButtonBar(Object constraints, Action... actions) {
        ButtonBarBuilder builder = new ButtonBarBuilder();

        builder.addGlue();

        builder.addActions(actions);

        add(builder.getPanel(), constraints);
    }

    @Override
    public PanelBuilder addPanel(Object constraints) {
        PanelBuilder builder = new BasicPanelBuilder();

        add(builder.getPanel(), constraints);

        return builder;
    }

    @Override
    public PanelBuilder addPanel(LayoutManager layout, Object constraints) {
        PanelBuilder builder = new BasicPanelBuilder(layout);

        add(builder.getPanel(), constraints);

        return builder;
    }

    @Override
    public void setTitleBorder(String text) {
        TitledBorder border = BorderFactory.createTitledBorder(text);

        setBorder(border);
    }

    @Override
    public JCheckBox addCheckbox(String text, Object constraints) {
        return add(new JCheckBox(text), constraints);
    }

    @Override
    public JTable addTable(TableModel model, Object constraints) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return add(table, constraints);
    }

    @Override
    public JTable addScrolledTable(TableModel model, Object constraints) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return addScrolled(table, constraints);
    }
}