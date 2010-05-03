package org.jtheque.views.impl.actions.event;

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.IViews;

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

public class DisplayLogsViewAction extends JThequeAction {
    private final IViews views;

    public DisplayLogsViewAction(IViews views) {
        super("log.view.actions.display");

        this.views = views;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        views.getEventView().display();
    }
}