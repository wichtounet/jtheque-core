package org.jtheque.ui.utils.builders;

import org.jtheque.ui.able.components.filthy.Filthy;
import org.jtheque.ui.utils.components.BorderUpdater;
import org.jtheque.ui.utils.components.Borders;
import org.jtheque.ui.utils.components.JThequeI18nLabel;

import org.jdesktop.swingx.JXTree;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

import java.awt.Color;
import java.awt.Component;
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
 * A filthy panel builder. It has the same functionality of <code>JThequePanelBuilder</code> but when a filthy component
 * exists, the builder use it instead of the normal component.
 *
 * @author Baptiste Wicht
 */
public final class FilthyPanelBuilder extends JThequePanelBuilder {
    /**
     * Construct a new FilthyPanelBuilder for a new panel.
     */
    public FilthyPanelBuilder() {
        super();
    }

    /**
     * Construct a new FilthyPanelBuilder for a specific panel.
     *
     * @param panel The panel to build.
     */
    public FilthyPanelBuilder(JPanel panel) {
        super(panel);
    }

    /**
     * Construct a new FilthyPanelBuilder with a specific layout.
     *
     * @param layout The layout to set to the builded panel.
     */
    public FilthyPanelBuilder(LayoutManager layout) {
        super(Filthy.newPanel(layout), false);
    }

    /**
     * Construct a new FilthyPanelBuilder with a specific panel.
     *
     * @param panel  The panel to build.
     * @param layout Indicate if we must set a default layout on the panel or keep the existing one.
     */
    public FilthyPanelBuilder(JPanel panel, boolean layout) {
        super(panel, layout);
    }

    @Override
    void initDefaults() {
        getPanel().setOpaque(false);
        getPanel().setBorder(Borders.EMPTY_BORDER);
    }

    @Override
    public JLabel addLabel(String text, int style, float size, Object constraints) {
        JLabel label = super.addLabel(text, style, size, constraints);

        label.setForeground(Color.white);

        return label;
    }

    @Override
    public JLabel addLabel(Object constraints) {
        JLabel label = super.addLabel(constraints);

        label.setForeground(Color.white);

        return label;
    }

    @Override
    public JLabel addLabel(String text, Color foreground, Object constraints) {
        JLabel label = super.addLabel(text, foreground, constraints);

        label.setForeground(Color.white);

        return label;
    }

    @Override
    public JLabel addLabel(String text, int style, Object constraints) {
        JLabel label = super.addLabel(text, style, constraints);

        label.setForeground(Color.white);

        return label;
    }

    @Override
    public JLabel addLabel(String text, Object constraints) {
        JLabel label = super.addLabel(text, constraints);

        label.setForeground(Color.white);

        return label;
    }

    @Override
    public JThequeI18nLabel addI18nLabel(String key, Object constraints) {
        JThequeI18nLabel label = super.addI18nLabel(key, constraints);

        label.setForeground(Color.white);

        return label;
    }

    @Override
    public JThequeI18nLabel addI18nLabel(String key, int style, Object constraints) {
        JThequeI18nLabel label = super.addI18nLabel(key, style, constraints);

        label.setForeground(Color.white);

        return label;
    }

    @Override
    public JThequeI18nLabel addI18nLabel(String key, int style, float size, Object constraints) {
        JThequeI18nLabel label = super.addI18nLabel(key, style, size, constraints);

        label.setForeground(Color.white);

        return label;
    }

    @Override
    public JList addScrolledList(ListModel model, ListCellRenderer renderer, Object constraints) {
        JList list = Filthy.newList(model);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(10);

        if (renderer != null) {
            list.setCellRenderer(renderer);
        }

        addScrolled(list, constraints);

        return list;
    }

    @Override
    public <T extends Component> T addScrolled(T view, Object constraints) {
        JScrollPane scrollPane = new JScrollPane(view);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(Borders.EMPTY_BORDER);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, constraints);

        return view;
    }

    @Override
    public JComboBox addComboBox(ComboBoxModel model, Object constraints) {
        return add(Filthy.newComboBox(model), constraints);
    }

    @Override
    public JComboBox addComboBox(ComboBoxModel model, ListCellRenderer renderer, Object constraints) {
        JComboBox combo = add(Filthy.newComboBox(model), constraints);

        combo.setRenderer(renderer);

        return combo;
    }

    @Override
    public I18nPanelBuilder addPanel(Object constraints) {
        I18nPanelBuilder builder = new FilthyPanelBuilder();

        if (getContainer() != null) {
            builder.setInternationalizableContainer(getContainer());
        }

        add(builder.getPanel(), constraints);

        return builder;
    }

    @Override
    public I18nPanelBuilder addPanel(LayoutManager layout, Object constraints) {
        I18nPanelBuilder builder = new FilthyPanelBuilder(layout);

        if (getContainer() != null) {
            builder.setInternationalizableContainer(getContainer());
        }

        add(builder.getPanel(), constraints);

        return builder;
    }

    @Override
    public JXTree addScrolledTree(TreeModel model, TreeCellRenderer renderer, Object constraints) {
        JXTree tree = new JXTree(model);
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        tree.setOpaque(false);
        tree.setBorder(Borders.EMPTY_BORDER);
        tree.putClientProperty("JTree.lineStyle", "None");

        if (renderer != null) {
            tree.setCellRenderer(renderer);
        }

        return addScrolled(tree, constraints);
    }

    @Override
    public void setTitleBorder(String text) {
        TitledBorder border = BorderFactory.createTitledBorder(text);

        border.setTitleColor(Color.white);

        setBorder(border);
    }

    @Override
    public void setI18nTitleBorder(String key) {
        TitledBorder border = BorderFactory.createTitledBorder(key);

        border.setTitleColor(Color.white);

        addInternationalizable(new BorderUpdater(border, key));

        setBorder(border);
    }

    @Override
    public JCheckBox addI18nCheckBox(String key, Object constraints) {
        JCheckBox checkBox = super.addI18nCheckBox(key, constraints);

        checkBox.setForeground(Color.white);
        checkBox.setOpaque(false);

        return checkBox;
    }
}