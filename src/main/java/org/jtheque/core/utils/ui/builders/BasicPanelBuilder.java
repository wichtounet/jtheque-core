package org.jtheque.core.utils.ui.builders;

import org.jtheque.utils.ui.ButtonBarBuilder;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;

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
    public BasicPanelBuilder(LayoutManager layout){
        super(layout);
    }

    /**
     * Construct a new JThequePanelBuilder.
     *
     * @param panel The panel to build.
     * @param layout If true set a default layout (GridBagLayout) to the builded panel.
     */
    public BasicPanelBuilder(JPanel panel, boolean layout){
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
     * @param model The model of the list.
     * @param renderer The renderer. Can be null.
     *
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

    /**
     * Apply the specified style to the component.
     *
     * @param style The font style to apply.
     * @param component The component to apply the style on.
     *
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
    public JComboBox addComboBox(DefaultComboBoxModel model, Object constraints) {
        return add(new JComboBox(model), constraints);
    }

    @Override
    public JComboBox addComboBox(DefaultComboBoxModel model, ListCellRenderer renderer, Object constraints) {
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
    public JTree addScrolledTree(TreeModel model, TreeCellRenderer renderer, Object constraints){
        JTree tree = new JTree(model);

        if(renderer != null){
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
}