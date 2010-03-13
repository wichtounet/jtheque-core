package org.jtheque.messages;

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

import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;

/**
 * @author Baptiste Wicht
 *         <p/>
 *         Created by IntelliJ IDEA.
 *         User: wichtounet
 *         Date: 20 mars 2009
 *         Time: 12:39:50
 */
public final class MessageFile {
    /**
     * The source of the messages.
     */
    private String source;

    /**
     * The messages.
     */
    private Collection<Message> messages;

    /**
     * Return the source of the messages.
     *
     * @return The source off the messages.
     */
    public String getSource() {
        return source;
    }

    /**
     * Set the source of the messages.
     *
     * @param source The source of the messages.
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Return all the messages of the file.
     *
     * @return A List containing all the messages of the file.
     */
    public Collection<Message> getMessages() {
        return messages;
    }

    /**
     * Set the messages.
     *
     * @param messages The messages of the file.
     */
    public void setMessages(Collection<Message> messages) {
        this.messages = CollectionUtils.copyOf(messages);
    }

    @Override
    public String toString() {
        return "MessageFile{" +
                "source='" + source + '\'' +
                ", messages=" + messages +
                '}';
    }
}
