package org.jtheque.ui.utils.builders;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTree;
import org.jtheque.i18n.able.Internationalizable;
import org.jtheque.i18n.able.InternationalizableContainer;
import org.jtheque.ui.utils.components.Borders;
import org.jtheque.utils.ui.ButtonBarBuilder;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.ui.utils.components.BorderUpdater;
import org.jtheque.ui.utils.components.JThequeCheckBox;
import org.jtheque.ui.utils.components.JThequeI18nLabel;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import java.awt.Color;
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
 * A panel builder.
 *
 * @author Baptiste Wicht
 */
public class JThequePanelBuilder extends BasicPanelBuilder implements I18nPanelBuilder {
    private InternationalizableContainer container;

    /**
     * Construct a new JThequePanelBuilder.
     */
    public JThequePanelBuilder() {
        super();
    }

    /**
     * Construct a new panel builder.
     *
     * @param panel The panel to build.
     */
    public JThequePanelBuilder(JPanel panel) {
        super(panel);
    }

    /**
     * Construct a new JThequePanelBuilder with a specified layout.
     *
     * @param layout The layout to set to the builded panel.
     */
    public JThequePanelBuilder(LayoutManager layout){
        super(layout);
    }

    /**
     * Construct a new JThequePanelBuilder.
     *
     * @param panel The panel to build.
     * @param layout If true set a default layout (GridBagLayout) to the builded panel.
     */
    public JThequePanelBuilder(JPanel panel, boolean layout){
        super(panel, layout);
    }

    @Override
    public void setInternationalizableContainer(InternationalizableContainer container) {
        this.container = container;
    }

    public InternationalizableContainer getContainer() {
        return container;
    }

    @Override
    void initDefaults() {
        getPanel().setBackground(Color.white);
        getPanel().setBorder(Borders.DIALOG_BORDER);
    }

    @Override
    public JComboBox addComboBox(DefaultComboBoxModel model, Object constraints) {
        JComboBox combo = new JComboBox(model);

        combo.setBackground(Color.white);

        return add(combo, constraints);
    }

    @Override
    public JCheckBox addI18nCheckBox(String key, Object constraints) {
        JThequeCheckBox checkBox = new JThequeCheckBox(key);

        addInternationalizable(checkBox);

        return add(checkBox, constraints);
    }

    @Override
    public JThequeI18nLabel addI18nLabel(String key, Object constraints) {
        return addI18nLabel(key, 0, constraints);
    }

    @Override
    public JThequeI18nLabel addI18nLabel(String key, int style, Object constraints) {
        JThequeI18nLabel label = new JThequeI18nLabel(key);

        addInternationalizable(label);

        applyStyle(style, label);

        return add(label, constraints);
    }

    @Override
    public JThequeI18nLabel addI18nLabel(String key, int style, float size, Object constraints) {
        JThequeI18nLabel label = new JThequeI18nLabel(key);

        addInternationalizable(label);

        applyStyle(style, label);

        label.setFont(label.getFont().deriveFont(size));

        return add(label, constraints);
    }

    @Override
    public JXTree addScrolledTree(TreeModel model, TreeCellRenderer renderer, Object constraints){
        JXTree tree = new JXTree(model);

        if(renderer != null){
            tree.setCellRenderer(renderer);
        }

        return addScrolled(tree, constraints);
    }

    @Override
    public void addI18nSeparator(String key, Object constraints) {
        I18nPanelBuilder separatorBuilder = addPanel(constraints);

        if(container != null){
            separatorBuilder.setInternationalizableContainer(container);
        }

        separatorBuilder.addI18nLabel(key, gbcSet(0, 0));
        separatorBuilder.add(new JSeparator(), gbcSet(1, 0, GridBagUtils.HORIZONTAL));
    }

    @Override
    public I18nPanelBuilder addPanel(Object constraints) {
        I18nPanelBuilder builder = new JThequePanelBuilder();

        if(container != null){
            builder.setInternationalizableContainer(container);
        }

        add(builder.getPanel(), constraints);

        return builder;
    }

    @Override
    public I18nPanelBuilder addPanel(LayoutManager layout, Object constraints) {
        I18nPanelBuilder builder = new JThequePanelBuilder(layout);

        if(container != null){
            builder.setInternationalizableContainer(container);
        }

        add(builder.getPanel(), constraints);

        return builder;
    }

    @Override
    public void setI18nTitleBorder(String key) {
        TitledBorder border = BorderFactory.createTitledBorder(key);

        addInternationalizable(new BorderUpdater(border, key));

        setBorder(border);
    }

    @Override
    public JButton addButton(Action action, Object constraints) {
        JButton button = add(new JButton(action), constraints);

        if(action instanceof Internationalizable){
            addInternationalizable((Internationalizable) action);
        }

        return button;
    }

    @Override
    public void addButtonBar(Object constraints, Action... actions) {
        ButtonBarBuilder builder = new ButtonBarBuilder();

        builder.addGlue();

        builder.addActions(actions);

        for(Action action : actions){
            if(action instanceof Internationalizable){
                addInternationalizable((Internationalizable) action);
            }
        }

        ((JComponent) builder.getPanel()).setOpaque(false);

        add(builder.getPanel(), constraints);
    }

    @Override
    public JTable addTable(TableModel model, Object constraints) {
        return add(createTable(model), constraints);
    }

    @Override
    public JTable addScrolledTable(TableModel model, Object constraints) {
        return addScrolled(createTable(model), constraints);
    }

    /**
     * Create a JXTable with JTheque defaults.
     *
     * @param model The model to use.
     *
     * @return The created JXTable. 
     */
    private static JXTable createTable(TableModel model) {
        JXTable table = new JXTable(model);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setVisibleRowCount(5);
        table.setColumnControlVisible(true);
        table.packAll();

        return table;
    }

    void addInternationalizable(Internationalizable internationalizable) {
        if(container != null){
            container.addInternationalizable(internationalizable);
        }
    }
}