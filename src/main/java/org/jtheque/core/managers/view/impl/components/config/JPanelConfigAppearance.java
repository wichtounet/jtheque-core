package org.jtheque.core.managers.view.impl.components.config;

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
import org.jtheque.core.managers.core.CoreConfiguration;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.config.IAppearanceConfigView;
import org.jtheque.core.managers.view.impl.components.model.AvailableLanguagesComboBoxModel;
import org.jtheque.core.utils.ui.Borders;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.ValidationUtils;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.util.Collection;

/**
 * A config panel for the appearance.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigAppearance extends JPanel implements ConfigTabComponent, IAppearanceConfigView {
    private final AvailableLanguagesComboBoxModel modelLanguages;
    private final JCheckBox boxRetainSizeAndPosition;

    /**
     * Construct a new JPanelConfigAppearance.
     */
    public JPanelConfigAppearance() {
        super();

        PanelBuilder builder = new PanelBuilder(this);

        PanelBuilder builder1 = builder.addPanel(builder.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START));
        builder1.getPanel().setBorder(Borders.createTitledBorder("config.appearance.general.title"));

        builder1.addI18nLabel("config.appearance.language", builder.gbcSet(0, 0));

        modelLanguages = new AvailableLanguagesComboBoxModel();

        builder1.addComboBox(modelLanguages, builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL));

        boxRetainSizeAndPosition = builder1.addI18nCheckBox("config.appearance.size",
                builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1));
        
        fillAllFields();
    }

    @Override
    public String getTitle() {
        return Managers.getManager(ILanguageManager.class).getMessage("config.view.tab.appearance");
    }

    /**
     * Fill the all fields with the current configurations.
     */
    private void fillAllFields() {
        modelLanguages.setSelectedItem(Managers.getManager(ILanguageManager.class).getCurrentLanguage());
        boxRetainSizeAndPosition.setSelected(Managers.getCore().getConfiguration().retainSizeAndPositionOfWindow());
    }

    @Override
    public void apply() {
        CoreConfiguration configuration = Managers.getCore().getConfiguration();

        Managers.getManager(ILanguageManager.class).setCurrentLanguage(modelLanguages.getSelectedLanguage());
        configuration.setRetainSizeAndPositionOfWindow(boxRetainSizeAndPosition.isSelected());
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