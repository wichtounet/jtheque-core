package org.jtheque.views.impl.components.config;

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

import org.jtheque.core.ICore;
import org.jtheque.errors.JThequeError;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.i18n.Internationalizable;
import org.jtheque.ui.utils.ValidationUtils;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.utils.filthy.FilthyBuildedPanel;
import org.jtheque.ui.utils.filthy.IFilthyUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.components.ConfigTabComponent;
import org.jtheque.views.able.config.IAppearanceConfigView;
import org.jtheque.views.impl.filthy.FilthyRenderer;
import org.jtheque.views.impl.models.AvailableLanguagesComboBoxModel;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import java.util.Collection;

/**
 * A config panel for the appearance.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigAppearance extends FilthyBuildedPanel implements ConfigTabComponent, IAppearanceConfigView {
    private AvailableLanguagesComboBoxModel modelLanguages;
    private JCheckBox boxRetainSizeAndPosition;

    private final ICore core;
    private final ILanguageService languageService;

    public JPanelConfigAppearance(IFilthyUtils filthyUtils, ILanguageService languageService, ICore core) {
        super(filthyUtils, languageService);

        this.languageService = languageService;
        this.core = core;

        build();
    }

    @Override
    protected void buildView(I18nPanelBuilder builder) {
        I18nPanelBuilder internBuilder = builder.addPanel(builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START));
        internBuilder.setI18nTitleBorder("config.appearance.general.title");

        internBuilder.addI18nLabel("config.appearance.language", builder.gbcSet(0, 0));

        modelLanguages = new AvailableLanguagesComboBoxModel(core);

        internBuilder.addComboBox(modelLanguages, new FilthyRenderer(), builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL));

        boxRetainSizeAndPosition = internBuilder.addI18nCheckBox("config.appearance.size",
                builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1));

        fillAllFields();
    }

    @Override
    public String getTitleKey() {
        return "config.view.tab.appearance";
    }

    /**
     * Fill the all fields with the current configurations.
     */
    private void fillAllFields() {
        modelLanguages.setSelectedItem(languageService.getCurrentLanguage());
        boxRetainSizeAndPosition.setSelected(core.getConfiguration().retainSizeAndPositionOfWindow());
    }

    @Override
    public void apply() {
        languageService.setCurrentLanguage(modelLanguages.getSelectedLanguage());
        core.getConfiguration().setRetainSizeAndPositionOfWindow(boxRetainSizeAndPosition.isSelected());
    }

    @Override
    public void cancel() {
        fillAllFields();
    }

    @Override
    public void validate(Collection<JThequeError> errors) {
        ValidationUtils.rejectIfNothingSelected(modelLanguages, "config.appearance.language", errors);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void addInternationalizable(Internationalizable internationalizable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void refreshText(ILanguageService languageService) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}