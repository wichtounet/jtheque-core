package org.jtheque.core.managers.view.impl.components.renderers;

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

import org.jdesktop.swingx.JXHyperlink;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.update.IUpdateManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.utils.OpenSiteLinkAction;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

/**
 * A renderer to display a module in a list.
 *
 * @author Baptiste Wicht
 */
public final class ModuleListRenderer extends JPanel implements ListCellRenderer {
    private final JLabel labelName;
    private final JLabel labelAuthor;

    private JLabel labelDate;
    private JLabel labelState;
    private JLabel labelCurrentVersion;
    private JLabel labelOnlineVersion;
    private JXHyperlink labelSite;
    private Component labelStateText;
    private Component labelDateText;
    private Component labelSiteText;
    private Component labelCurrentVersionText;
    private Component labelOnlineVersionText;

    private static final int TITLE_FONT_SIZE = 16;

    /**
     * Construct a new ModuleListRenderer.
     */
    public ModuleListRenderer() {
        super();

        PanelBuilder builder = new PanelBuilder(this);

        labelName = builder.add(new JLabel(), builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1));
        labelName.setFont(labelName.getFont().deriveFont(Font.BOLD, TITLE_FONT_SIZE));

        labelAuthor = builder.add(new JLabel(), builder.gbcSet(4, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1));

        addStateLabels(builder);
        addDateLabels(builder);
        addSiteLabels(builder);
        addCurrentVersionLabels(builder);
        addOnlineVersionLabels(builder);
    }

    /**
     * Add the online version label.
     *
     * @param builder The panel builder.
     */
    private void addOnlineVersionLabels(PanelBuilder builder) {
        labelOnlineVersionText = builder.addI18nLabel("modules.view.label.versions.online", builder.gbcSet(2, 2));
        labelOnlineVersion = builder.addLabel("", builder.gbcSet(3, 2, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 3, 1));
    }

    /**
     * Add the current version label.
     *
     * @param builder The panel builder.
     */
    private void addCurrentVersionLabels(PanelBuilder builder) {
        labelCurrentVersionText = builder.addI18nLabel("modules.view.label.versions.current", builder.gbcSet(0, 2));

        labelCurrentVersion = builder.add(new JLabel(), builder.gbcSet(1, 2));
    }

    /**
     * Add the sites version label.
     *
     * @param builder The panel builder.
     */
    private void addSiteLabels(PanelBuilder builder) {
        labelSiteText = builder.addI18nLabel("modules.view.label.site", builder.gbcSet(2, 1));

        labelSite = builder.add(new JXHyperlink(), builder.gbcSet(3, 1, GridBagUtils.NONE, GridBagUtils.BASELINE_LEADING, 3, 1));
    }

    /**
     * Add the state version label.
     *
     * @param builder The panel builder.
     */
    private void addStateLabels(PanelBuilder builder) {
        labelStateText = builder.addI18nLabel("modules.view.label.state", builder.gbcSet(2, 0));

        labelState = builder.add(new JLabel(), builder.gbcSet(3, 0));
    }

    /**
     * Add the date version label.
     *
     * @param builder The panel builder.
     */
    private void addDateLabels(PanelBuilder builder) {
        labelDateText = builder.addI18nLabel("modules.view.label.date", builder.gbcSet(0, 1));

        labelDate = builder.add(new JLabel(), builder.gbcSet(1, 1));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        updateUI(isSelected);
        updateInfos(value);

        return this;
    }

    /**
     * Update the UI.
     *
     * @param isSelected A boolean flag indicating if the current element is selected or not.
     */
    private void updateUI(boolean isSelected) {
        if (isSelected) {
            setBackground(Managers.getManager(IViewManager.class).getViewDefaults().getSelectedBackgroundColor());
            setChildsForeground(Managers.getManager(IViewManager.class).getViewDefaults().getSelectedForegroundColor());
        } else {
            setBackground(Managers.getManager(IViewManager.class).getViewDefaults().getBackgroundColor());
            setChildsForeground(Managers.getManager(IViewManager.class).getViewDefaults().getForegroundColor());
        }

        labelOnlineVersion.setBackground(getBackground());
    }

    /**
     * Update the informations with the module informations.
     *
     * @param value The module information object.
     */
    private void updateInfos(Object value) {
        ModuleContainer module = (ModuleContainer) value;

        labelName.setText(module.getName());
        labelAuthor.setText(Managers.getManager(ILanguageManager.class).getMessage("modules.view.label.author") + " : " +
                module.getAuthor());
        labelCurrentVersion.setText(module.getInfos().version());
        labelState.setText(module.getState().toString());

        setToolTipText(module.getDescription());

        setModuleDefinitionInfos(module);
    }

    /**
     * Update the informations with the module definition.
     *
     * @param module The module to get the informations from.
     */
    private void setModuleDefinitionInfos(ModuleContainer module) {
        if (module.getInfos().date() == null || module.getInfos().date().isEmpty()) {
            labelDate.setText("");
        } else {
            labelDate.setText(module.getInfos().date());
        }

        if (module.getInfos().url() == null || module.getInfos().url().isEmpty()) {
            labelSite.setAction(null);
        } else {
            labelSite.setAction(new OpenSiteLinkAction(module.getInfos().url()));
        }

        labelOnlineVersion.setText(Managers.getManager(IUpdateManager.class).getMostRecentVersion(module).getVersion());
    }

    /**
     * Set the foregound of the childs.
     *
     * @param color The foreground color.
     */
    private void setChildsForeground(Color color) {
        labelName.setForeground(color);
        labelAuthor.setForeground(color);
        labelDate.setForeground(color);
        labelSite.setForeground(color);
        labelCurrentVersion.setForeground(color);
        labelCurrentVersionText.setForeground(color);
        labelOnlineVersion.setForeground(color);
        labelOnlineVersionText.setForeground(color);
        labelDateText.setForeground(color);
        labelSiteText.setForeground(color);
        labelState.setForeground(color);
        labelStateText.setForeground(color);
    }
}