package org.jtheque.messages;

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

import org.jtheque.utils.Constants;
import org.jtheque.utils.bean.EqualsUtils;
import org.jtheque.utils.bean.IntDate;

/**
 * @author Baptiste Wicht
 */
public final class Message implements Comparable<Message> {
    /**
     * The Id of the message.
     */
    private int id;

    /**
     * The title of the message.
     */
    private String title;

    /**
     * The text of the message.
     */
    private String message;

    /**
     * The date of the message.
     */
    private IntDate date;

    /**
     * The source of the message.
     */
    private String source;

    /**
     * Return the Id of the message.
     *
     * @return The id of the message.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the Id of the message.
     *
     * @param id The Id of the message.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the title of the message.
     *
     * @return The title of the message.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the message.
     *
     * @param title The title of the message.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return the text of the message.
     *
     * @return The text of the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the text of the message.
     *
     * @param message The text of the message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Return the date of the message.
     *
     * @return The date of the message.
     */
    public IntDate getDate() {
        return date;
    }

    /**
     * Set the date of the message.
     *
     * @param date The date of the message.
     */
    public void setDate(IntDate date) {
        this.date = date;
    }

    /**
     * Return the source of the message.
     *
     * @return The source of the message.
     */
    public String getSource() {
        return source;
    }

    /**
     * Set the source of the message.
     *
     * @param source The source of the message.
     */
    public void setSource(String source) {
        this.source = source;
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
        if (EqualsUtils.areObjectIncompatible(this, o)) {
            return false;
        }

        Message other = (Message) o;

        if (EqualsUtils.areNotEquals(id, other.id)) {
            return false;
        }

        if (EqualsUtils.areNotEquals(date, other.date)) {
            return false;
        }

        if (EqualsUtils.areNotEquals(message, other.message)) {
            return false;
        }

        if (EqualsUtils.areNotEquals(source, other.source)) {
            return false;
        }

        return !EqualsUtils.areNotEquals(title, other.title);
    }

    @Override
    public int hashCode() {
        int result = Constants.HASH_CODE_START;

        result = Constants.HASH_CODE_PRIME * result + id;
        result = Constants.HASH_CODE_PRIME * result + title.hashCode();
        result = Constants.HASH_CODE_PRIME * result + message.hashCode();
        result = Constants.HASH_CODE_PRIME * result + date.hashCode();
        result = Constants.HASH_CODE_PRIME * result + source.hashCode();

        return result;
    }

    @Override
    public int compareTo(Message o) {
        return date.compareTo(o.date);
    }
}