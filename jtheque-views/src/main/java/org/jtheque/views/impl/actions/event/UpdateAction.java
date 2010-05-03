package org.jtheque.views.impl.actions.event;

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.windows.IEventView;

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
 * An action to org.jtheque.update the event view.
 *
 * @author Baptiste Wicht
 */
public final class UpdateAction extends JThequeAction {
    private final IEventView eventView;

    /**
     * Construct a new UpdateAction.
     * 
     * @param eventView The log view.
     */
    public UpdateAction(IEventView eventView) {
        super("log.view.actions.update");

        this.eventView = eventView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        eventView.refresh();
    }
}