package org.jtheque.views.impl.components.renderers;

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
import org.jtheque.modules.able.Module;
import org.jtheque.ui.utils.actions.OpenSiteLinkAction;
import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.components.Borders;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ui.GridBagUtils;

import org.jdesktop.swingx.JXHyperlink;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import java.awt.Component;
import java.awt.Font;

/**
 * A renderer to display a module in a list.
 *
 * @author Baptiste Wicht
 */
public final class ModuleListRenderer extends JPanel implements ListCellRenderer {
    private final JLabel labelTitle;
    private final JLabel labelState;
    private final JLabel labelCurrentVersion;
    private final JLabel labelOnlineVersion;
    private final JXHyperlink labelSite;

    //Keeps fonts to quickly switch them
    private Font fontTitle;
    private Font fontTitleBold;
    private Font fontLabel;
    private Font fontLabelBold;

    private static final int TITLE_FONT_SIZE = 16;

    private final IUpdateService updateService;
    private final ILanguageService languageService;

    /**
     * Construct a new ModuleListRenderer.
     *
     * @param updateService   The update service.
     * @param languageService The language service.
     */
    public ModuleListRenderer(IUpdateService updateService, ILanguageService languageService) {
        super();

        this.updateService = updateService;
        this.languageService = languageService;

        I18nPanelBuilder builder = new FilthyPanelBuilder(this);
        builder.setBorder(Borders.createEmptyBorder(2, 2, 2, 10));

        labelTitle = builder.addLabel("", PanelBuilder.NORMAL, TITLE_FONT_SIZE,
                builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));

        labelState = builder.addLabel("", builder.gbcSet(0, 1));
        labelSite = builder.add(new JXHyperlink(), builder.gbcSet(1, 1));

        labelCurrentVersion = builder.addLabel("", builder.gbcSet(0, 2));
        labelOnlineVersion = builder.addLabel("", builder.gbcSet(1, 2));
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
            fontTitle = labelTitle.getFont();
            fontTitleBold = fontTitle.deriveFont(Font.BOLD);

            fontLabel = labelCurrentVersion.getFont();
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
        labelTitle.setFont(fontTitle);
        labelCurrentVersion.setFont(fontLabel);
        labelOnlineVersion.setFont(fontLabel);
        labelState.setFont(fontLabel);
    }

    /**
     * Update the informations with the module informations.
     *
     * @param value The module information object.
     */
    private void updateInfos(Object value) {
        Module module = (Module) value;

        labelTitle.setText(languageService.getMessage("modules.view.label.title", module.getName(), module.getAuthor()));
        labelState.setText(languageService.getMessage("modules.view.label.state", module.getDisplayState()));
        labelCurrentVersion.setText(languageService.getMessage("modules.view.label.versions.current", module.getVersion().getVersion()));
        labelOnlineVersion.setText(languageService.getMessage("modules.view.label.versions.online", updateService.getMostRecentVersion(module).getVersion()));

        if (StringUtils.isEmpty(module.getUrl())) {
            labelSite.setAction(null);
        } else {
            labelSite.setAction(new OpenSiteLinkAction(module.getUrl()));
        }

        setToolTipText(languageService.getMessage(module.getDescription()));
    }
}