package org.jtheque.core.utils.ui;

import org.jdesktop.swingx.JXTree;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.components.JThequeCheckBox;
import org.jtheque.core.managers.view.impl.components.JThequeI18nLabel;
import org.jtheque.utils.ui.ButtonBarBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

/**
 * A panel builder.
 *
 * @author Baptiste Wicht
 */
public class PanelBuilder {
    private final JPanel panel;

    private final GridBagUtils gbc;

    public static final int BOLD = 1;
    public static final int ITALIC = 2;

    /**
     * Construct a new PanelBuilder.
     */
    public PanelBuilder() {
        this(new JPanel(), true);
    }

    /**
     * Construct a new panel builder.
     *
     * @param panel The panel to build.
     */
    public PanelBuilder(JPanel panel) {
        this(panel, true);
    }

    /**
     * Construct a new PanelBuilder with a specified layout.
     *
     * @param layout The layout to set to the builded panel.
     */
    public PanelBuilder(LayoutManager layout){
        this(new JPanel(layout), false);
    }

    /**
     * Construct a new PanelBuilder.
     *
     * @param panel The panel to build.
     * @param layout If true set a default layout (GridBagLayout) to the builded panel.
     */
    public PanelBuilder(JPanel panel, boolean layout){
        super();

        if(layout){
            panel.setLayout(new GridBagLayout());
        }

        this.panel = panel;
        gbc = new GridBagUtils();

        initJThequeDefaults();
    }

    /**
     * Init the panel with the JTheque defaults.
     */
    void initJThequeDefaults() {
        panel.setBackground(Managers.getManager(IViewManager.class).getViewDefaults().getBackgroundColor());
        panel.setBorder(Borders.DIALOG_BORDER);
    }

    /**
     * Add a component to the panel with the specified constraints.
     *
     * @param component   The component to add.
     * @param constraints The constraints to use to add to the panel.
     * @param <T>         The type of component.
     * @return The added component.
     */
    public <T extends Component> T add(T component, Object constraints) {
        panel.add(component, constraints);

        return component;
    }

    /**
     * Add a combo box.
     *
     * @param model       The combo box model.
     * @param constraints The constraints to use to add to the panel.
     * @return The added JComboBox.
     */
    public JComboBox addComboBox(DefaultComboBoxModel model, Object constraints) {
        JComboBox combo = new JComboBox(model);

        combo.setBackground(Managers.getManager(IViewManager.class).getViewDefaults().getBackgroundColor());

        return add(combo, constraints);
    }

    /**
     * Add a combo box with a specific renderer.
     *
     * @param model       The combo box model.
     * @param renderer    The renderer to use.
     * @param constraints The constraints to use to add to the panel.
     * @return The added JComboBox.
     */
    public JComboBox addComboBox(DefaultComboBoxModel model, ListCellRenderer renderer, Object constraints) {
        JComboBox combo = addComboBox(model, constraints);

        combo.setRenderer(renderer);

        return combo;
    }

    /**
     * Add an internationalized check box.
     *
     * @param key         The internationalization key.
     * @param constraints The constraints to use to add to the panel.
     * @return The added JCheckBox.
     */
    public JCheckBox addI18nCheckBox(String key, Object constraints) {
        return add(new JThequeCheckBox(key), constraints);
    }

    /**
     * Add a <code>Jlist</code> with the specified model.
     *
     * @param model       The model to use.
     * @param renderer    The list renderer.
     * @param constraints The constraints to use to add to the panel.
     * @return The added JList.
     */
    public JList addList(ListModel model, ListCellRenderer renderer, Object constraints) {
        JList list = new JList(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(12);
        list.setValueIsAdjusting(true);

        if (renderer != null) {
            list.setCellRenderer(renderer);
        }

        addScrolled(list, constraints);

        return list;
    }

    /**
     * Add a button to the panel.
     *
     * @param action      The action to use with the button.
     * @param constraints The constraints to use to add to the panel.
     * @return The added button.
     */
    public JButton addButton(Action action, Object constraints) {
        return add(new JButton(action), constraints);
    }

    /**
     * Add a label to the panel.
     *
     * @param constraints The constraints to use to add to the panel.
     * 
     * @return The added label.
     */
    public JLabel addLabel(Object constraints) {
        return add(new JLabel(), constraints);
    }

    /**
     * Add a label to the panel.
     *
     * @param text        The text of the label.
     * @param constraints The constraints to use to add to the panel.
     * @return The added label.
     */
    public JLabel addLabel(String text, Object constraints) {
        return add(new JLabel(text), constraints);
    }

    /**
     * Add a label with a specific foreground to the panel.
     *
     * @param text        The text of the label.
     * @param foreground  The foreground color.
     * @param constraints The constraints to use to add to the panel.
     * @return The added label.
     */
    public JLabel addLabel(String text, Color foreground, Object constraints) {
        JLabel label = addLabel(text, constraints);

        label.setForeground(foreground);

        return label;
    }

    /**
     * Add an internationalized label to the panel.
     *
     * @param key         The i18n key.
     * @param constraints The constraints to use to add to the panel.
     * @return The added label.
     */
    public JThequeI18nLabel addI18nLabel(String key, Object constraints) {
        return addI18nLabel(key, 0, constraints);
    }

    /**
     * Add an internationalized label with a specified style to the panel.
     *
     * @param key         The i18n key.
     * @param constraints The constraints to use to add to the panel.
     * @param style       The font style.
     * @return The added label.
     */
    public JThequeI18nLabel addI18nLabel(String key, int style, Object constraints) {
        JThequeI18nLabel label = new JThequeI18nLabel(key);

        if ((style & BOLD) == BOLD) {
            label.setFont(label.getFont().deriveFont(Font.BOLD));
        }

        if ((style & ITALIC) == ITALIC) {
            label.setFont(label.getFont().deriveFont(Font.ITALIC));
        }

        return add(label, constraints);
    }

    /**
     * Add a scrolled text area.
     *
     * @param text        The text of the text area.
     * @param constraints The constraints to use to add to the panel.
     */
    public void addScrolledTextArea(String text, Object constraints) {
        addScrolled(new JTextArea(text), constraints);
    }

    /**
     * Add a JTree in a JScrollpane
     *
     * @param model The tree model.
     * @param renderer The tree renderer.
     * @param constraints The constraints to add the tree with.
     *
     * @return The added tree.
     */
    public JXTree addScrolledTree(TreeModel model, TreeCellRenderer renderer, Object constraints){
        JXTree tree = new JXTree(model);

        if(renderer != null){
            tree.setCellRenderer(renderer);
        }

        addScrolled(tree, constraints);

        return tree;
    }

    /**
     * Add a scrolled component.
     *
     * @param view        The view.
     * @param constraints The constraints to use to add to the panel.
     */
    public void addScrolled(JComponent view, Object constraints) {
        JScrollPane scrollPane = new JScrollPane(view);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, constraints);
    }

    /**
     * Add an internationalized separator.
     *
     * @param key         The title key.
     * @param constraints The constraints to use to add to the panel.
     */
    public void addI18nSeparator(String key, Object constraints) {
        PanelBuilder separatorBuilder = addPanel(constraints);

        separatorBuilder.addI18nLabel(key, gbcSet(0, 0));
        separatorBuilder.add(new JSeparator(), gbcSet(1, 0, GridBagUtils.HORIZONTAL));
    }

    /**
     * Add a button bar.
     *
     * @param constraints The constraints to use to add to the panel.
     * @param actions     The actions to add to the button bar.
     */
    public void addButtonBar(Object constraints, Action... actions) {
        ButtonBarBuilder builder = new ButtonBarBuilder();

        builder.addGlue();

        builder.addActions(actions);

        add(builder.getPanel(), constraints);
    }

    /**
     * Add a panel to the panel.
     *
     * @param constraints The constraints to use to add to the panel.
     * @return The builder of the intern panel builder.
     */
    public PanelBuilder addPanel(Object constraints) {
        PanelBuilder builder = new PanelBuilder();

        add(builder.getPanel(), constraints);

        return builder;
    }

    /**
     * Add a panel to the panel.
     *
     * @param layout The layout to use.
     * @param constraints The constraints to use to add to the panel.
     *
     * @return The builder of the intern panel builder.
     */
    public PanelBuilder addPanel(LayoutManager layout, Object constraints) {
        PanelBuilder builder = new PanelBuilder(layout);

        add(builder.getPanel(), constraints);

        return builder;
    }

    /**
     * Return the builded panel.
     *
     * @return The builded panel.
     */
    public JComponent getPanel() {
        return panel;
    }

    /**
     * Set the border of the panel.
     *
     * @param border The border to set to the panel. 
     */
    public void setBorder(Border border){
        panel.setBorder(border);
    }

    /* GridBagUtils delegate methods */

    /**
     * Set the default insets.
     *
     * @param defaultInsets The default insets.
     */
    public void setDefaultInsets(Insets defaultInsets) {
        gbc.setDefaultInsets(defaultInsets);
    }

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
    public Object gbcSet(int x, int y, int fill, int anchor, double weightx, double weighty) {
        return gbc.gbcSet(x, y, fill, anchor, weightx, weighty);
    }

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
    public Object gbcSet(int x, int y, int fill, int anchor, int width, int height, double weightx, double weighty) {
        return gbc.gbcSet(x, y, fill, anchor, width, height, weightx, weighty);
    }

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
    public Object gbcSet(int x, int y, int fill, int anchor, int width, int height, double weightx, double weighty, int ipadx, int ipady) {
        return gbc.gbcSet(x, y, fill, anchor, width, height, weightx, weighty, ipadx, ipady);
    }

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
    public Object gbcSet(int x, int y, int fill, int anchor, int width, int height) {
        return gbc.gbcSet(x, y, fill, anchor, width, height);
    }

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
    public Object gbcSet(int x, int y, int fill, int width, int height) {
        return gbc.gbcSet(x, y, fill, GridBagUtils.BASELINE_LEADING, width, height);
    }

    /**
     * Create a new layout constraints.
     *
     * @param x      The x position on the grid.
     * @param y      The y position on the grid.
     * @param fill   The fill constraint.
     * @param anchor The anchor constraint.
     * @return The layout constraints.
     */
    public Object gbcSet(int x, int y, int fill, int anchor) {
        return gbc.gbcSet(x, y, fill, anchor);
    }

    /**
     * Create a new layout constraints.
     *
     * @param x    The x position on the grid.
     * @param y    The y position on the grid.
     * @param fill The fill constraint.
     * @return The layout constraints.
     */
    public Object gbcSet(int x, int y, int fill) {
        return gbc.gbcSet(x, y, fill);
    }

    /**
     * Create a new layout constraints.
     *
     * @param x The x position on the grid.
     * @param y The y position on the grid.
     * @return The layout constraints.
     */
    public Object gbcSet(int x, int y) {
        return gbc.gbcSet(x, y);
    }
}