package org.jtheque.views.impl.components.panel;

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

import org.jtheque.i18n.ILanguageService;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.impl.ModuleDescription;
import org.jtheque.ui.utils.components.Borders;
import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.update.versions.VersionsFile;
import org.jtheque.update.versions.VersionsFileReader;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.Resource;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

/**
 * A module panel.
 *
 * @author Baptiste Wicht
 */
public final class ModulePanel extends JPanel {
    private final JLabel labelName;
    private final JLabel labelDescription;
    private final JLabel onlineLabel;
    private final JLabel currentLabel;

    private final ModuleDescription module;

    //Keeps fonts to quickly switch them
    private Font fontTitle;
    private Font fontTitleBold;
    private Font fontLabel;
    private Font fontLabelBold;

    private static final int TITLE_FONT_SIZE = 16;

    @Resource
    private IModuleService moduleService;

    @Resource
    private ILanguageService languageService;

    /**
     * Construct a new ModulePanel.
     *
     * @param value      The value.
     * @param isSelected A boolean tag indicating if the module is selected or not.
     */
    public ModulePanel(Object value, boolean isSelected) {
        super();

        PanelBuilder builder = new FilthyPanelBuilder(this);
        builder.setBorder(Borders.createEmptyBorder(2, 2, 2, 10));

        module = (ModuleDescription) value;

        labelName = builder.addLabel(module.getName(),
                PanelBuilder.NORMAL, TITLE_FONT_SIZE, builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 1.0));
        labelDescription = builder.addLabel(module.getDescription().toString(), builder.gbcSet(0, 1));

        onlineLabel = builder.addLabel("...", builder.gbcSet(0, 3));
        currentLabel = builder.addLabel("...", builder.gbcSet(0, 4));

        updateUI(isSelected);
    }

    /**
     * Update the UI.
     *
     * @param isSelected A boolean flag indicating if the current element is selected or not.
     */
    public void updateUI(boolean isSelected) {
        initFonts();

        if(isSelected){
            setFonts(fontTitleBold, fontLabelBold);
        } else {
            setFonts(fontTitle, fontLabel);
        }
    }

    private void initFonts() {
        if(fontTitle == null){
            fontTitle = labelName.getFont();
            fontTitleBold = fontTitle.deriveFont(Font.BOLD);

            fontLabel = labelDescription.getFont();
            fontLabelBold = fontLabel.deriveFont(Font.BOLD);
        }
    }

    private void setFonts(Font fontTitle, Font fontLabel) {
        labelName.setFont(fontTitle);
        labelDescription.setFont(fontLabel);
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

        onlineLabel.setText(languageService.getMessage(
                "repository.module.online",
                file.getMostRecentVersion().getStringVersion()));

        if (moduleService.isInstalled(module.getId())) {
            currentLabel.setText(languageService.getMessage(
                    "repository.module.current",
                    moduleService.getModuleById(module.getId()).getVersion().getVersion()));
        } else {
            currentLabel.setText(languageService.getMessage("repository.module.current.notinstalled"));
        }
    }
}
