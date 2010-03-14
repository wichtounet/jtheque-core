package org.jtheque.views.impl.actions.event;

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewManager;

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
 * An action to update the event view.
 *
 * @author Baptiste Wicht
 */
public final class UpdateAction extends JThequeAction {
    /**
     * Construct a new UpdateAction.
     */
    public UpdateAction() {
        super("log.view.actions.update");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ViewsServices.get(IViewManager.class).getViews().getLogView().refresh();
    }
}