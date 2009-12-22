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

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.plaf.basic.BasicMenuItemUI;

/**
 * A JTheque menu item.
 *
 * @author Baptiste Wicht
 */
public final class JThequeMenuItem extends JMenuItem {
    /**
     * Construct a new JThequeMenuItem.
     *
     * @param action The action of the menu item.
     */
    public JThequeMenuItem(Action action) {
        super(action);

        setUI(new BasicMenuItemUI());
    }
}