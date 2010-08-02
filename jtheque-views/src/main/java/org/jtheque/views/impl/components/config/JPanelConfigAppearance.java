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

import org.jtheque.core.able.Core;
import org.jtheque.i18n.able.LanguageService;
import org.jtheque.ui.able.constraints.Constraint;
import org.jtheque.ui.able.constraints.Constraints;
import org.jtheque.ui.utils.builded.OSGIFilthyBuildedPanel;
import org.jtheque.ui.utils.builders.I18nPanelBuilder;
import org.jtheque.ui.able.components.filthy.Filthy;
import org.jtheque.ui.utils.models.SimpleListModel;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.views.able.components.ConfigTabComponent;
import org.jtheque.views.able.config.AppearanceConfigView;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import java.util.Map;

/**
 * A config panel for the appearance.
 *
 * @author Baptiste Wicht
 */
public final class JPanelConfigAppearance extends OSGIFilthyBuildedPanel implements ConfigTabComponent, AppearanceConfigView {
    private SimpleListModel<String> modelLanguages;
    private JCheckBox boxRetainSizeAndPosition;

    @Override
    protected void buildView(I18nPanelBuilder parent) {
        I18nPanelBuilder builder = parent.addPanel(parent.gbcSet(0, 0, GridBagUtils.HORIZONTAL, GridBagUtils.FIRST_LINE_START));
        builder.setI18nTitleBorder("config.appearance.general.title");

        builder.addI18nLabel("config.appearance.language", parent.gbcSet(0, 0));

        modelLanguages = new SimpleListModel<String>(getService(Core.class).getPossibleLanguages());

        builder.addComboBox(modelLanguages, Filthy.newListRenderer(), parent.gbcSet(1, 0, GridBagUtils.HORIZONTAL));

        boxRetainSizeAndPosition = builder.addI18nCheckBox("config.appearance.size",
                parent.gbcSet(0, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 2, 1));

        fillAllFields();
    }

    @Override
    public Map<Object, Constraint> getConstraints() {
        Map<Object, Constraint> constraints = CollectionUtils.newHashMap(1);

        constraints.put(modelLanguages, Constraints.atLeastOne("config.appearance.language"));

        return constraints;
    }

    @Override
    public String getTitleKey() {
        return "config.view.tab.appearance";
    }

    /**
     * Fill the all fields with the current configurations.
     */
    private void fillAllFields() {
        modelLanguages.setSelectedItem(getService(LanguageService.class).getCurrentLanguage());
        boxRetainSizeAndPosition.setSelected(getService(Core.class).getConfiguration().retainSizeAndPositionOfWindow());
    }

    @Override
    public void apply() {
        getService(LanguageService.class).setCurrentLanguage(modelLanguages.getSelectedItem());
        getService(Core.class).getConfiguration().setRetainSizeAndPositionOfWindow(boxRetainSizeAndPosition.isSelected());
    }

    @Override
    public void cancel() {
        fillAllFields();
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}