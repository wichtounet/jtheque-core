package org.jtheque.core.managers.view.impl.components.menu;

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

import javax.swing.JMenu;
import javax.swing.plaf.basic.BasicMenuUI;

/**
 * A JTheque menu.
 *
 * @author Baptiste Wicht
 */
public final class JThequeMenu extends JMenu implements Internationalizable {
    private static final long serialVersionUID = 7026652221740733889L;

    private final String key;

    /**
     * Construct a new JThequeMenu.
     *
     * @param key The internationalization key.
     */
    public JThequeMenu(String key) {
        super(Managers.getManager(ILanguageManager.class).getMessage(key));

        Managers.getManager(ILanguageManager.class).addInternationalizable(this);

        this.key = key;

        setUI(new BasicMenuUI());
    }

    @Override
    public void refreshText() {
        setText(Managers.getManager(ILanguageManager.class).getMessage(key));
    }
}