package org.jtheque.views.impl.components.config;

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

import org.jtheque.core.ICore;
import org.jtheque.errors.JThequeError;
import org.jtheque.i18n.ILanguageService;
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
    protected void buildView(I18nPanelBuilder parent) {
        I18nPanelBuilder builder = parent.addPanel(parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START));
        builder.setI18nTitleBorder("config.appearance.general.title");

        builder.addI18nLabel("config.appearance.language", parent.gbcSet(0, 0));

        modelLanguages = new AvailableLanguagesComboBoxModel(core);

        builder.addComboBox(modelLanguages, new FilthyRenderer(), parent.gbcSet(1, 0, GridBagUtils.HORIZONTAL));

        boxRetainSizeAndPosition = builder.addI18nCheckBox("config.appearance.size",
                parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1));

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
}