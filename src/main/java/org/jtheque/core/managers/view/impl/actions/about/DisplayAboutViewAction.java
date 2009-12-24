package org.jtheque.core.managers.view.impl.actions.about;

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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import java.awt.event.ActionEvent;

/**
 * Action to display the about view.
 *
 * @author Baptiste Wicht
 */
public final class DisplayAboutViewAction extends JThequeAction {
    /**
     * Construct a new DisplayAboutViewAction.
     */
    public DisplayAboutViewAction() {
        super("about.actions.display", Managers.getCore().getApplication().getName());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Managers.getManager(IViewManager.class).displayAboutView();
    }
}