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
import org.jtheque.ui.able.components.Borders;
import org.jtheque.ui.utils.actions.OpenSiteLinkAction;
import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.GridBagUtils;

import org.jdesktop.swingx.JXHyperlink;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import java.awt.Component;
import java.awt.Font;
import java.util.Map;

/**
 * A renderer to display a module in a list.
 *
 * @author Baptiste Wicht
 */
public final class ModuleListRenderer extends JPanel implements ListCellRenderer {
    private static final int TITLE_FONT_SIZE = 16;

    private Font fontTitle;
    private Font fontTitleBold;
    private Font fontLabel;
    private Font fontLabelBold;

    private final IUpdateService updateService;
    private final ILanguageService languageService;

    private final Map<Module, ModuleListPanel> panels = CollectionUtils.newHashMap(10);

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
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Module module = (Module) value;

        if(!panels.containsKey(module)){
            panels.put(module, new ModuleListPanel(module));
        }

        ModuleListPanel panel = panels.get(module);

        panel.initFonts();

        if (isSelected) {
            panel.setFonts(fontTitleBold, fontLabelBold);
        } else {
            panel.setFonts(fontTitle, fontLabel);
        }

        panel.updateInfos(module);

        return panel;
    }


    /**
     * A panel for a module in the renderer
     *
     * @author Baptiste Wicht
     */
    private final class ModuleListPanel extends JPanel {
        private final JLabel labelTitle;
        private final JLabel labelState;
        private final JLabel labelCurrentVersion;
        private final JLabel labelOnlineVersion;
        private final JXHyperlink labelSite;

        /**
         * Construct a new ModuleListPanel.
         *
         * @param module The module to get the informations from. 
         */
        private ModuleListPanel(Module module){
            super();

            I18nPanelBuilder builder = new FilthyPanelBuilder(this);
            builder.setBorder(Borders.createEmptyBorder(2, 2, 2, 10));

            labelTitle = builder.addLabel("", PanelBuilder.NORMAL, TITLE_FONT_SIZE,
                    builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));

            labelState = builder.addLabel("", builder.gbcSet(0, 1));
            labelSite = builder.add(new JXHyperlink(), builder.gbcSet(1, 1));

            labelCurrentVersion = builder.addLabel("", builder.gbcSet(0, 2));
            labelOnlineVersion = builder.addLabel("", builder.gbcSet(1, 2));

            labelTitle.setText(languageService.getMessage("modules.view.label.title", module.getName(), module.getAuthor()));
            labelCurrentVersion.setText(languageService.getMessage("modules.view.label.versions.current", module.getVersion()));
            labelOnlineVersion.setText(languageService.getMessage("modules.view.label.versions.online", updateService.getMostRecentVersion(module)));

            if (StringUtils.isEmpty(module.getUrl())) {
                labelSite.setAction(null);
            } else {
                labelSite.setAction(new OpenSiteLinkAction(module.getUrl()));
            }

            setToolTipText(languageService.getMessage(module.getDescription()));
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
         * Update the informations with the module informations.
         *
         * @param module The module information object.
         */
        private void updateInfos(Module module) {
            labelState.setText(languageService.getMessage("modules.view.label.state", module.getDisplayState()));
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
    }
}