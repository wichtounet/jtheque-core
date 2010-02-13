package org.jtheque.core.utils.ui.builders;

import org.jdesktop.swingx.JXTree;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.impl.components.JThequeCheckBox;
import org.jtheque.core.managers.view.impl.components.JThequeI18nLabel;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyComboBox;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyList;
import org.jtheque.core.managers.view.impl.components.filthy.FilthyPanel;
import org.jtheque.core.utils.ui.BorderUpdater;
import org.jtheque.core.utils.ui.Borders;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
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
    void initDefaults() {
        getPanel().setOpaque(false);
        getPanel().setBorder(Borders.EMPTY_BORDER);
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
    public JList addScrolledList(ListModel model, ListCellRenderer renderer, Object constraints) {
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
    public I18nPanelBuilder addPanel(Object constraints) {
        I18nPanelBuilder builder = new FilthyPanelBuilder();

        add(builder.getPanel(), constraints);

        return builder;
    }

    @Override
    public I18nPanelBuilder addPanel(LayoutManager layout, Object constraints){
        I18nPanelBuilder builder = new FilthyPanelBuilder(layout);

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
        TitledBorder border = BorderFactory.createTitledBorder(Managers.getManager(ILanguageManager.class).getMessage(key));

        border.setTitleColor(Color.white);

        Managers.getManager(ILanguageManager.class).addInternationalizable(new BorderUpdater(border, key));

        setBorder(border);
    }

    @Override
    public JCheckBox addI18nCheckBox(String key, Object constraints) {
        JCheckBox checkBox = new JThequeCheckBox(key);

        checkBox.setForeground(Color.white);
        checkBox.setOpaque(false);

        return add(checkBox, constraints);
    }
}