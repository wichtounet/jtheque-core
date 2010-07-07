package org.jtheque.views.impl.controllers;

import org.jtheque.ui.utils.AbstractController;
import org.jtheque.views.able.windows.IMessageView;

import javax.annotation.Resource;

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
public class MessageController extends AbstractController {
    @Resource
    private IMessageView messageView;

    /**
     * Open the next message.
     */
    private void next() {
        messageView.next();
    }

    /**
     * Open the previous message. 
     */
    private void previous() {
        messageView.previous();
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(2);

        translations.put("messages.actions.display.next", "next");
        translations.put("messages.actions.display.previous", "previous");

        return translations;
    }
}