package org.jtheque.core.utils.ui;

import org.jdesktop.swingx.JXTree;
import org.jtheque.core.managers.view.impl.components.JThequeI18nLabel;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyComboBox;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyList;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyPanel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import java.awt.Color;
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
 * A filthy panel builder. It has the same functionality of <code>PanelBuilder</code> but when a filthy component
 * exists, the builder use it instead of the normal component.
 *
 * @author Baptiste Wicht
 */
public final class FilthyPanelBuilder extends PanelBuilder {
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
    public FilthyPanelBuilder(LayoutManager layout){
        super(new FilthyPanel(layout), false);
    }

	/**
	 * Construct a new FilthyPanelBuilder with a specific panel.
	 *
	 * @param panel The panel to build.
	 * @param layout Indicate if we must set a default layout on the panel or keep the existing one. 
	 */
    public FilthyPanelBuilder(JPanel panel, boolean layout) {
        super(panel, layout);
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
    void initJThequeDefaults() {
        getPanel().setOpaque(false);
        getPanel().setBorder(Borders.EMPTY_BORDER);
    }

    @Override
    public JList addList(ListModel model, ListCellRenderer renderer, Object constraints) {
        JList list = new FilthyList(model);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(10);
        list.setValueIsAdjusting(true);

        if (renderer != null) {
            list.setCellRenderer(renderer);
        }

        addScrolled(list, constraints);

        return list;
    }

    @Override
    public void addScrolled(JComponent view, Object constraints) {
        JScrollPane scrollPane = new JScrollPane(view);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(Borders.EMPTY_BORDER);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, constraints);
    }

    @Override
    public JComboBox addComboBox(DefaultComboBoxModel model, Object constraints) {
        return add(new FilthyComboBox(model), constraints);
    }

    @Override
    public JComboBox addComboBox(DefaultComboBoxModel model, ListCellRenderer renderer, Object constraints) {
        JComboBox combo = add(new FilthyComboBox(model), constraints);

        combo.setRenderer(renderer);

        return combo;
    }

    @Override
    public PanelBuilder addPanel(Object constraints) {
        PanelBuilder builder = new FilthyPanelBuilder();

        add(builder.getPanel(), constraints);

        return builder;
    }

    @Override
    public PanelBuilder addPanel(LayoutManager layout, Object constraints){
        PanelBuilder builder = new FilthyPanelBuilder(layout);

        add(builder.getPanel(), constraints);

        return builder;
    }

    @Override
    public JXTree addScrolledTree(TreeModel model, TreeCellRenderer renderer, Object constraints){
        JXTree tree = new JXTree(model);
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        tree.setOpaque(false);
        tree.setBorder(Borders.EMPTY_BORDER);
        tree.putClientProperty("JTree.lineStyle", "None");

        if(renderer != null){
            tree.setCellRenderer(renderer);
        }

        addScrolled(tree, constraints);

        return tree;
    }
}