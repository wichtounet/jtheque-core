package org.jtheque.messages.impl;

import org.jtheque.messages.Message;
import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.bean.EqualsBuilder;
import org.jtheque.utils.bean.HashCodeUtils;
import org.jtheque.utils.bean.IntDate;

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
 * A builder to create IMessage.
 *
 * @author Baptiste Wicht
 */
public final class Messages {
    /**
     * Utility class, not instantiable.
     */
    private Messages() {
        throw new AssertionError();
    }

    /**
     * Create a new empty message.
     *
     * @param id The id of the message.
     *
     * @return A new message with the given properties.
     */
    public static Message newEmptyMessage(int id) {
        return new MessageImpl(id, "", "", null, "");
    }

    /**
     * Create a new message at the date of today.
     *
     * @param id The id of the message.
     *
     * @return A new message with the given properties.
     */
    public static Message newEmptyTodayMessage(int id) {
        return new MessageImpl(id, "", "", IntDate.today(), "");
    }

    /**
     * Create a new message.
     *
     * @param id      The id of the message.
     * @param title   The title of the message.
     * @param message The message.
     * @param date    The date of the message.
     * @param source  The source of the message.
     *
     * @return A new message with the given properties.
     */
    public static Message newMessage(int id, String title, String message, IntDate date, String source) {
        return new MessageImpl(id, title, message, date, source);
    }

    /**
     * A simple message. This class is immutable.
     *
     * @author Baptiste Wicht
     */
    @Immutable
    private static final class MessageImpl implements Message {
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
        private final String text;

        /**
         * The date of the message.
         */
        private final IntDate date;

        /**
         * The source of the message.
         */
        private final String source;

        /**
         * Construct a new MessageImpl.
         *
         * @param id     The id of the message.
         * @param title  The title of the message.
         * @param text   The message.
         * @param date   The date of the message.
         * @param source The source of the message.
         */
        private MessageImpl(int id, String title, String text, IntDate date, String source) {
            super();

            this.id = id;
            this.title = title;
            this.text = text;
            this.date = date == null ? date : new IntDate(date);
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
        public String getText() {
            return text;
        }

        @Override
        public IntDate getDate() {
            return date == null ? date : new IntDate(date);
        }

        @Override
        public String getSource() {
            return source;
        }

        @Override
        public String toString() {
            return "MessageImpl{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", text='" + text + '\'' +
                    ", date=" + date +
                    ", source='" + source + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof MessageImpl) {
                MessageImpl other = (MessageImpl) o;

                return EqualsBuilder.newBuilder(this, other).
                        addField(id, other.id).
                        addField(date, other.date).
                        addField(text, other.text).
                        addField(source, other.source).
                        addField(title, other.title).
                        areEquals();
            }

            return false;
        }

        @Override
        public int hashCode() {
            return HashCodeUtils.hashCodeDirect(id, title, text, date, source);
        }

        @Override
        public int compareTo(Message o) {
            return date.compareTo(o.getDate());
        }
    }
}