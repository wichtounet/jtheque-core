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
