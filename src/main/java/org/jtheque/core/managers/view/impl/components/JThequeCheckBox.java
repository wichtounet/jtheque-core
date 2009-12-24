package org.jtheque.core.managers.view.impl.components;

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
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.language.Internationalizable;
import org.jtheque.core.managers.view.able.IViewManager;

import javax.swing.JCheckBox;

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

        setBackground(Managers.getManager(IViewManager.class).getViewDefaults().getBackgroundColor());

        textKey = key;

        setText(Managers.getManager(ILanguageManager.class).getMessage(key));

        Managers.getManager(ILanguageManager.class).addInternationalizable(this);
    }

    @Override
    public void refreshText() {
        setText(Managers.getManager(ILanguageManager.class).getMessage(textKey));
    }
}