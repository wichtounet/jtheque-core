package org.jtheque.views.impl.controllers;

import org.jtheque.ui.able.Action;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.views.able.windows.IMessageView;

import java.util.HashMap;
import java.util.Map;

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
 * A controller for the messages view.
 *
 * @author Baptiste Wicht
 */
public class MessageController extends AbstractController<IMessageView> {
    public MessageController() {
        super(IMessageView.class);
    }

    /**
     * Close the messages views.
     */
    @Action("messages.actions.close")
    public void close() {
        getView().closeDown();
    }

    /**
     * Open the next message.
     */
    @Action("messages.actions.display.next")
    public void next() {
        getView().next();
    }

    /**
     * Open the previous message.
     */
    @Action("messages.actions.display.previous")
    public void previous() {
        getView().previous();
    }
}