package org.jtheque.ui.utils.builders;

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

import org.jtheque.i18n.able.InternationalizableContainer;
import org.jtheque.ui.utils.components.JThequeI18nLabel;

import javax.swing.JCheckBox;
import java.awt.LayoutManager;

public interface I18nPanelBuilder extends PanelBuilder {
    void setInternationalizableContainer(InternationalizableContainer container);

    /**
     * Add an internationalized check box.
     *
     * @param key         The internationalization key.
     * @param constraints The constraints to use to add to the panel.
     * @return The added JCheckBox.
     */
    JCheckBox addI18nCheckBox(String key, Object constraints);

    /**
     * Add an internationalized label to the panel.
     *
     * @param key         The i18n key.
     * @param constraints The constraints to use to add to the panel.
     * @return The added label.
     */
    JThequeI18nLabel addI18nLabel(String key, Object constraints);

    /**
     * Add an internationalized label with a specified style to the panel.
     *
     * @param key         The i18n key.
     * @param constraints The constraints to use to add to the panel.
     * @param style       The font style.
     * @return The added label.
     */
    JThequeI18nLabel addI18nLabel(String key, int style, Object constraints);

    /**
     * Add an internationalized label with a specified style to the panel.
     *
     * @param key         The i18n key.
     * @param constraints The constraints to use to add to the panel.
     * @param style       The font style.
     * @param size        The font size
     *
     * @return The added label.
     */
    JThequeI18nLabel addI18nLabel(String key, int style, float size, Object constraints);

    /**
     * Add an internationalized separator.
     *
     * @param key         The title key.
     * @param constraints The constraints to use to add to the panel.
     */
    void addI18nSeparator(String key, Object constraints);

    @Override
    I18nPanelBuilder addPanel(Object constraints);

    @Override
    I18nPanelBuilder addPanel(LayoutManager layout, Object constraints);

    /**
     * Set a i18n titled border.
     *
     * @param key The internationalization key.
     */
    void setI18nTitleBorder(String key);
}
