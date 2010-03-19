package org.jtheque.views.impl.components.menu;

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
import org.jtheque.views.ViewsServices;

import javax.swing.JMenu;
import javax.swing.plaf.basic.BasicMenuUI;

/**
 * A JTheque menu.
 *
 * @author Baptiste Wicht
 */
public final class JThequeMenu extends JMenu implements Internationalizable {
    private final String key;

    /**
     * Construct a new JThequeMenu.
     *
     * @param key The internationalization key.
     */
    public JThequeMenu(String key) {
        super(ViewsServices.get(ILanguageService.class).getMessage(key));

        ViewsServices.get(ILanguageService.class).addInternationalizable(this);

        this.key = key;

        setUI(new BasicMenuUI());
    }

    @Override
    public void refreshText() {
        setText(ViewsServices.get(ILanguageService.class).getMessage(key));
    }
}