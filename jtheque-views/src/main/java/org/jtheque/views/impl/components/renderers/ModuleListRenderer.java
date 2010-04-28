package org.jtheque.views.impl.components.renderers;

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
import org.jtheque.modules.able.Module;
import org.jtheque.ui.utils.components.Borders;
import org.jtheque.ui.utils.actions.OpenSiteLinkAction;
import org.jtheque.ui.utils.builders.FilthyPanelBuilder;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.builders.PanelBuilder;
import org.jtheque.ui.utils.components.JThequeI18nLabel;
import org.jtheque.update.IUpdateService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ui.GridBagUtils;

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
    private final JThequeI18nLabel labelTitle;
    private final JThequeI18nLabel labelState;
    private final JThequeI18nLabel labelCurrentVersion;
    private final JThequeI18nLabel labelOnlineVersion;
    private final JXHyperlink labelSite;

    //Keeps fonts to quickly switch them
    private Font fontTitle;
    private Font fontTitleBold;
    private Font fontLabel;
    private Font fontLabelBold;

    private static final int TITLE_FONT_SIZE = 16;

    private final IUpdateService updateService;

    /**
     * Construct a new ModuleListRenderer.
     */
    public ModuleListRenderer(IUpdateService updateService) {
        super();
        this.updateService = updateService;

        I18nPanelBuilder builder = new FilthyPanelBuilder(this);
        builder.setBorder(Borders.createEmptyBorder(2, 2, 2, 10));

        labelTitle = builder.addI18nLabel("modules.view.label.title", PanelBuilder.NORMAL, TITLE_FONT_SIZE,
                builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));

        labelState = builder.addI18nLabel("modules.view.label.state", builder.gbcSet(0, 1));
        labelSite = builder.add(new JXHyperlink(), builder.gbcSet(1, 1));

        labelCurrentVersion = builder.addI18nLabel("modules.view.label.versions.current", builder.gbcSet(0, 2));
        labelOnlineVersion = builder.addI18nLabel("modules.view.label.versions.online", builder.gbcSet(1, 2));
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

        if(isSelected){
            setFonts(fontTitleBold, fontLabelBold);
        } else {
            setFonts(fontTitle, fontLabel);
        }
    }

    private void initFonts() {
        if(fontTitle == null){
            fontTitle = labelTitle.getFont();
            fontTitleBold = fontTitle.deriveFont(Font.BOLD);

            fontLabel = labelCurrentVersion.getFont();
            fontLabelBold = fontLabel.deriveFont(Font.BOLD);
        }
    }

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

        labelTitle.setTextKey("modules.view.label.title", module.getName(), module.getAuthor());
        labelState.setTextKey("modules.view.label.state", module.getState().toString());
        labelCurrentVersion.setTextKey("modules.view.label.versions.current", module.getVersion().getVersion());
        labelOnlineVersion.setTextKey("modules.view.label.versions.online", updateService.getMostRecentVersion(module).getVersion());

        if (StringUtils.isEmpty(module.getUrl())) {
            labelSite.setAction(null);
        } else {
            labelSite.setAction(new OpenSiteLinkAction(module.getUrl()));
        }

        setToolTipText(module.getDescription());
    }
}