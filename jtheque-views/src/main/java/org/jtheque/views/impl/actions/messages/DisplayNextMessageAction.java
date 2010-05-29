package org.jtheque.views.impl.actions.messages;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.windows.IMessageView;

import java.awt.event.ActionEvent;

/**
 * An action to display the next message.
 *
 * @author Baptiste Wicht
 */
public final class DisplayNextMessageAction extends JThequeAction {
    private final IMessageView messageView;

    /**
     * Construct a new DisplayNextMessageAction.
     *
     * @param messageView The message view.
     */
    public DisplayNextMessageAction(IMessageView messageView) {
        super("messages.actions.display.next");
        this.messageView = messageView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        messageView.next();
    }
}