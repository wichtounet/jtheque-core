package org.jtheque.ui.utils.builders;

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

import org.jtheque.i18n.InternationalizableContainer;
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
