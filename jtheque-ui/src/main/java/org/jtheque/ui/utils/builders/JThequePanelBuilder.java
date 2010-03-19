package org.jtheque.ui.utils.builders;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTree;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.ui.utils.components.Borders;
import org.jtheque.utils.ui.ButtonBarBuilder;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.ui.ViewsUtilsServices;
import org.jtheque.ui.utils.components.BorderUpdater;
import org.jtheque.ui.utils.components.JThequeCheckBox;
import org.jtheque.ui.utils.components.JThequeI18nLabel;
import org.jtheque.ui.utils.components.JThequeLabel;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
public class JThequePanelBuilder extends BasicPanelBuilder implements I18nPanelBuilder {
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
        return add(new JThequeCheckBox(key), constraints);
    }

    @Override
    public JLabel addLabel(Object constraints) {
        return add(new JThequeLabel(""), constraints);
    }

    @Override
    public JLabel addLabel(String text, Object constraints) {
        return add(new JThequeLabel(text), constraints);
    }

    @Override
    public JLabel addLabel(String text, Color foreground, Object constraints) {
        JLabel label = addLabel(text, constraints);

        label.setForeground(foreground);

        return label;
    }

    @Override
    public JLabel addLabel(String text, int style, Object constraints) {
        JLabel label = addLabel(text, constraints);

        applyStyle(style,label);

        return label;
    }

    @Override
    public JLabel addLabel(String text, int style, float size, Object constraints) {
        JLabel label = addLabel(text, style, constraints);

        label.setFont(label.getFont().deriveFont(size));

        return label;
    }

    @Override
    public JThequeI18nLabel addI18nLabel(String key, Object constraints) {
        return addI18nLabel(key, 0, constraints);
    }

    @Override
    public JThequeI18nLabel addI18nLabel(String key, int style, Object constraints) {
        JThequeI18nLabel label = new JThequeI18nLabel(key);

        applyStyle(style, label);

        return add(label, constraints);
    }

    @Override
    public JThequeI18nLabel addI18nLabel(String key, int style, float size, Object constraints) {
        JThequeI18nLabel label = new JThequeI18nLabel(key);

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

        separatorBuilder.addI18nLabel(key, gbcSet(0, 0));
        separatorBuilder.add(new JSeparator(), gbcSet(1, 0, GridBagUtils.HORIZONTAL));
    }

    @Override
    public I18nPanelBuilder addPanel(Object constraints) {
        I18nPanelBuilder builder = new JThequePanelBuilder();

        add(builder.getPanel(), constraints);

        return builder;
    }

    @Override
    public I18nPanelBuilder addPanel(LayoutManager layout, Object constraints) {
        I18nPanelBuilder builder = new JThequePanelBuilder(layout);

        add(builder.getPanel(), constraints);

        return builder;
    }

    @Override
    public void setI18nTitleBorder(String key) {
        TitledBorder border = BorderFactory.createTitledBorder(ViewsUtilsServices.get(ILanguageService.class).getMessage(key));

        ViewsUtilsServices.get(ILanguageService.class).addInternationalizable(new BorderUpdater(border, key));

        setBorder(border);
    }

    @Override
    public void addButtonBar(Object constraints, Action... actions) {
        ButtonBarBuilder builder = new ButtonBarBuilder();

        builder.addGlue();

        builder.addActions(actions);

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
}