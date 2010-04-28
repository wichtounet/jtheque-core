package org.jtheque.ui.utils.actions;

import org.jtheque.ui.able.IView;

import java.awt.event.ActionEvent;

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

/**
 * A generic display view action.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractDisplayViewAction extends JThequeAction {
    /**
     * Construct a new AbstractDisplayViewAction with a specific internationalization key.
     *
     * @param key The i18n key.
     */
    public AbstractDisplayViewAction(String key) {
        super(key);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getView().display();
    }

    protected abstract IView getView();
}