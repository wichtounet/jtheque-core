package org.jtheque.core.managers.message;

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