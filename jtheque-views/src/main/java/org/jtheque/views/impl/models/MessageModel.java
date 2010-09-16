package org.jtheque.views.impl.models;

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

import org.jtheque.messages.Message;
import org.jtheque.messages.MessageService;

/**
 * A model for the messages view.
 *
 * @author Baptiste Wicht
 */
public final class MessageModel implements IMessageModel {
    private final Message[] messages;
    private int current;

    /**
     * Construct a new MessageModel.
     *
     * @param messageService The message service.
     */
    public MessageModel(MessageService messageService) {
        super();

        messages = messageService.getMessages().toArray(new Message[messageService.getMessages().size()]);
    }

    @Override
    public Message getCurrentMessage() {
        return current == messages.length ? null : messages[current];
    }

    @Override
    public Message getNextMessage() {
        return messages[++current];
    }

    @Override
    public Message getPreviousMessage() {
        return messages[--current];
    }

    @Override
    public boolean hasNext() {
        return current < messages.length;
    }

    @Override
    public boolean hasPrevious() {
        return current > 0;
    }
}
