package org.jtheque.core.managers.view.impl.models;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.message.IMessageManager;
import org.jtheque.core.managers.message.Message;
import org.jtheque.utils.bean.IntDate;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * A model for the messages view.
 *
 * @author Baptiste Wicht
 */
public final class MessageModel implements IMessageModel {
    private final IMessageManager manager;
    private final Message defaultMessage;

    private final ListIterator<Message> iterator;

    /**
     * Construct a new MessageModel.
     */
    public MessageModel() {
        super();

        manager = Managers.getManager(IMessageManager.class);

        iterator = new ArrayList<Message>(manager.getMessages()).listIterator();

        defaultMessage = new Message();
        defaultMessage.setDate(IntDate.today());
        defaultMessage.setId(-1);
        defaultMessage.setSource("");
        defaultMessage.setMessage("");
        defaultMessage.setTitle("");
    }

    @Override
    public Message getNextMessage() {
        if (!iterator.hasNext()) {
            CollectionUtils.goToFirst(iterator);
        }

        return manager.getMessages().isEmpty() ? defaultMessage : iterator.next();
    }

    @Override
    public Message getCurrentMessage() {
        if (!iterator.hasPrevious()) {
            CollectionUtils.goToLast(iterator);
        }

        return manager.getMessages().isEmpty() ? defaultMessage : iterator.previous();
    }

    @Override
    public Message getPreviousMessage() {
        if (!iterator.hasNext()) {
            CollectionUtils.goToFirst(iterator);
        }

        return manager.getMessages().isEmpty() ? defaultMessage : iterator.next();
    }

    @Override
    public boolean isDefaultMessage() {
        return manager.getMessages().isEmpty();
    }
}
