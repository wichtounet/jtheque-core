package org.jtheque.ui.utils.components;

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

import org.jtheque.i18n.ILanguageService;
import org.jtheque.i18n.Internationalizable;

import javax.swing.JCheckBox;
import java.awt.Color;

/**
 * A internationalizable checkbox.
 *
 * @author Baptiste Wicht
 */
public final class JThequeCheckBox extends JCheckBox implements Internationalizable {
    private final String textKey;

    /**
     * Construct a new JThequeCheckBox.
     *
     * @param key The internationalization key.
     */
    public JThequeCheckBox(String key) {
        super();

        setBackground(Color.white);

        textKey = key;
    }

    @Override
    public void refreshText(ILanguageService languageService) {
        setText(languageService.getMessage(textKey));
    }
}