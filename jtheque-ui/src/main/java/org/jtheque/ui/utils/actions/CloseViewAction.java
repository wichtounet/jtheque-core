package org.jtheque.ui.utils.actions;

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

import org.jtheque.ui.able.IView;
import org.jtheque.ui.utils.actions.JThequeAction;

import java.awt.event.ActionEvent;

/**
 * A generic close view action.
 *
 * @author Baptiste Wicht
 */
public final class CloseViewAction extends JThequeAction {
    private IView view;

    /**
     * Construct a new CloseViewAction with a specific internationalization key.
     *
     * @param key The i18n key.
     */
    public CloseViewAction(String key) {
        super(key);
    }

    /**
     * Construct a new CloseViewAction with a specific internationalization key.
     *
     * @param key The i18n key.
     * @param view The view to close.
     */
    public CloseViewAction(String key, IView view) {
        super(key);

        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.closeDown();
    }

    /**
     * Set the view to close.
     *
     * @param view The view to close.
     */
    public void setView(IView view) {
        this.view = view;
    }
}