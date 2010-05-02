package org.jtheque.views.impl.actions.messages;

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

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.windows.IMessageView;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to display the previous message.
 *
 * @author Baptiste Wicht
 */
public final class DisplayPreviousMessageAction extends JThequeAction {
    private IMessageView messageView;

    /**
     * Construct a new DisplayPreviousMessageAction.
     * @param messageView
     */
    public DisplayPreviousMessageAction(IMessageView messageView) {
        super("messages.actions.display.previous");
        
        this.messageView = messageView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        messageView.previous();
    }
}