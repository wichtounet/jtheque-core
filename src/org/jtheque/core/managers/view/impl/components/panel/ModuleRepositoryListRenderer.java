package org.jtheque.core.managers.view.impl.components.panel;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.module.IModuleManager;
import org.jtheque.core.managers.update.repository.ModuleDescription;
import org.jtheque.core.managers.update.versions.VersionsFile;
import org.jtheque.core.managers.update.versions.VersionsFileReader;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.ViewDefaults;
import org.jtheque.core.utils.ui.PanelBuilder;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

/**
 * A renderer to display a module in a list.
 *
 * @author Baptiste Wicht
 */
public final class ModuleRepositoryListRenderer implements ListCellRenderer {
    private final Map<Integer, ModulePanel> panels;

    /**
     * Construct a new ModuleListRenderer.
     */
    public ModuleRepositoryListRenderer() {
        super();

        panels = new HashMap<Integer, ModulePanel>(10);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (panels.get(index) == null) {
            panels.put(index, new ModulePanel(value, isSelected));
        } else {
            panels.get(index).setSelected(isSelected);
        }

        return panels.get(index);
    }

    /**
     * A module panel.
     *
     * @author Baptiste Wicht
     */
    public static final class ModulePanel extends JPanel {
        private final JLabel labelName;
        private final JLabel labelDescription;
        private final JLabel onlineLabel;
        private final JLabel currentLabel;

        private final ModuleDescription module;

        private static final int TITLE_FONT_SIZE = 16;

        /**
         * Construct a new ModulePanel.
         *
         * @param value      The value.
         * @param isSelected A boolean tag indicating if the module is selected or not.
         */
        public ModulePanel(Object value, boolean isSelected) {
            super();

            PanelBuilder builder = new PanelBuilder(this);

            module = (ModuleDescription) value;

            labelName = builder.add(new JLabel(module.getName()), builder.gbcSet(0, 0));
            labelName.setFont(labelName.getFont().deriveFont(Font.BOLD, TITLE_FONT_SIZE));

            labelDescription = builder.add(new JLabel(module.getDescription().toString()), builder.gbcSet(0, 1));

            onlineLabel = builder.addLabel("...", builder.gbcSet(0, 3));
            currentLabel = builder.addLabel("...", builder.gbcSet(0, 4));

            setSelected(isSelected);
        }

        /**
         * Set selected.
         *
         * @param isSelected A boolean tag indicating if the module is selected or not.
         */
        public void setSelected(boolean isSelected) {
            ViewDefaults defaults = Managers.getManager(IViewManager.class).getViewDefaults();

            if (isSelected) {
                setColors(defaults.getSelectedBackgroundColor(), defaults.getSelectedForegroundColor());
            } else {
                setColors(defaults.getBackgroundColor(), defaults.getForegroundColor());
            }
        }

        /**
         * Set the colors of the renderer.
         *
         * @param background The background color.
         * @param foreground The foreground color.
         */
        private void setColors(Color background, Color foreground) {
            setBackground(background);

            labelName.setBackground(background);
            labelDescription.setBackground(background);
            onlineLabel.setBackground(background);
            currentLabel.setBackground(background);

            labelName.setForeground(foreground);
            labelDescription.setForeground(foreground);
            onlineLabel.setForeground(foreground);
            currentLabel.setForeground(foreground);
        }

        /**
         * Expand the version.
         */
        public void expand() {
            VersionsFile file = new VersionsFileReader().readURL(module.getVersionsFileURL());

            onlineLabel.setText(Managers.getManager(ILanguageManager.class).getMessage(
                    "repository.module.online",
                    file.getMostRecentVersion().getStringVersion()));

            if (Managers.getManager(IModuleManager.class).isInstalled(module.getId())) {
                currentLabel.setText(Managers.getManager(ILanguageManager.class).getMessage(
                        "repository.module.current",
                        Managers.getManager(IModuleManager.class).getModuleById(module.getId()).getInfos().version()));
            } else {
                currentLabel.setText(Managers.getManager(ILanguageManager.class).getMessage("repository.module.current.notinstalled"));
            }
        }
    }
}