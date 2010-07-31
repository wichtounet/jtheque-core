package org.jtheque.messages.impl;

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

import org.jtheque.messages.able.IMessage;
import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.bean.EqualsBuilder;
import org.jtheque.utils.bean.HashCodeUtils;
import org.jtheque.utils.bean.IntDate;

/**
 * A simple message. This class is immutable.
 *
 * @author Baptiste Wicht
 */
@Immutable
public final class Message implements IMessage {
    /**
     * The Id of the message.
     */
    private final int id;

    /**
     * The title of the message.
     */
    private final String title;

    /**
     * The text of the message.
     */
    private final String message;

    /**
     * The date of the message.
     */
    private final IntDate date;

    /**
     * The source of the message.
     */
    private final String source;

    /**
     * Construct a new Message.
     *
     * @param id      The id of the message.
     * @param title   The title of the message.
     * @param message The message.
     * @param date    The date of the message.
     * @param source  The source of the message.
     */
    public Message(int id, String title, String message, IntDate date, String source) {
        super();

        this.id = id;
        this.title = title;
        this.message = message;
        this.date = new IntDate(date);
        this.source = source;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public IntDate getDate() {
        return new IntDate(date);
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", source='" + source + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Message) {
            Message other = (Message) o;

            return EqualsBuilder.newBuilder(this, other).
                    addField(id, other.id).
                    addField(date, other.date).
                    addField(message, other.message).
                    addField(source, other.source).
                    addField(title, other.title).
                    areEquals();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCodeDirect(id, title, message, date, source);
    }

    @Override
    public int compareTo(IMessage o) {
        return date.compareTo(o.getDate());
    }
}