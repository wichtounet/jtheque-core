package org.jtheque.views.impl.actions.messages;

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.IViews;

import java.awt.event.ActionEvent;

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

/**
 * An action to display the messages view.
 *
 * @author Baptiste Wicht
 */
public class DisplayMessagesViewAction extends JThequeAction {
    private final IViews views;

    /**
     * Construct a new DisplayMessagesViewAction.
     *
     * @param views The views. 
     */
    public DisplayMessagesViewAction(IViews views) {
        super("messages.actions.display");

        this.views = views;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        views.getMessagesView().display();
    }
}