package org.jtheque.views.impl.components.panel;

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

import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.modules.able.IModuleDescription;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.components.Borders;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.Resource;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

    private final IModuleDescription module;

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

    @Resource
    private IUpdateService updateService;

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

        module = (IModuleDescription) value;

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

        if (isSelected) {
            setFonts(fontTitleBold, fontLabelBold);
        } else {
            setFonts(fontTitle, fontLabel);
        }
    }

    /**
     * Init the fonts of the renderer.
     */
    private void initFonts() {
        if (fontTitle == null) {
            fontTitle = labelName.getFont();
            fontTitleBold = fontTitle.deriveFont(Font.BOLD);

            fontLabel = labelDescription.getFont();
            fontLabelBold = fontLabel.deriveFont(Font.BOLD);
        }
    }

    /**
     * Set the fonts of the labels.
     *
     * @param fontTitle THe font of the title.
     * @param fontLabel The font of the normal labels.
     */
    private void setFonts(Font fontTitle, Font fontLabel) {
        labelName.setFont(fontTitle);
        labelDescription.setFont(fontLabel);
    }

    /**
     * Expand the version.
     */
    public void expand() {
        onlineLabel.setText(languageService.getMessage(
                "repository.module.online",
                updateService.getMostRecentVersion(module).toString()));

        if (moduleService.isInstalled(module.getId())) {
            currentLabel.setText(languageService.getMessage(
                    "repository.module.current",
                    moduleService.getModuleById(module.getId()).getVersion().getVersion()));
        } else {
            currentLabel.setText(languageService.getMessage("repository.module.current.notinstalled"));
        }
    }
}
