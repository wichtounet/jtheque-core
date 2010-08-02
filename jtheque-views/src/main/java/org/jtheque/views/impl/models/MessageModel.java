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

import org.jtheque.messages.able.Message;
import org.jtheque.messages.able.MessageService;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * A model for the messages view.
 *
 * @author Baptiste Wicht
 */
public final class MessageModel implements IMessageModel {
    private final MessageService messageService;
    private final Message defaultMessage;

    private final ListIterator<Message> iterator;

    /**
     * Construct a new MessageModel.
     *
     * @param messageService The message service.
     */
    public MessageModel(MessageService messageService) {
        super();

        this.messageService = messageService;

        iterator = new ArrayList<Message>(messageService.getMessages()).listIterator();

        defaultMessage = messageService.getEmptyMessage();
    }

    @Override
    public Message getNextMessage() {
        if (!iterator.hasNext()) {
            CollectionUtils.goToFirst(iterator);
        }

        return messageService.getMessages().isEmpty() ? defaultMessage : iterator.next();
    }

    @Override
    public Message getCurrentMessage() {
        if (!iterator.hasPrevious()) {
            CollectionUtils.goToLast(iterator);
        }

        return messageService.getMessages().isEmpty() ? defaultMessage : iterator.previous();
    }

    @Override
    public Message getPreviousMessage() {
        if (!iterator.hasNext()) {
            CollectionUtils.goToFirst(iterator);
        }

        return messageService.getMessages().isEmpty() ? defaultMessage : iterator.next();
    }

    @Override
    public boolean isDefaultMessage() {
        return messageService.getMessages().isEmpty();
    }
}
