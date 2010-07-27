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
import org.jtheque.utils.bean.IntDate;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.IXMLReader;
import org.jtheque.xml.utils.XML;
import org.jtheque.xml.utils.XMLException;

import org.w3c.dom.Node;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Baptiste Wicht
 */
final class MessageFileReader {
    /**
     * Construct a new MessageFileReader. This constructor is private because all methods are static.
     */
    private MessageFileReader() {
        throw new AssertionError();
    }

    /**
     * Read a messages file and return all the messages of the file.
     *
     * @param strUrl The URL of the file.
     *
     * @return The messages.
     *
     * @throws XMLException If there is an error reading the messages file.
     */
    public static Collection<IMessage> readMessagesFile(String strUrl) throws XMLException {
        URL url;

        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            throw new XMLException("URL Invalid ", e);
        }

        return readMessagesFile(url);
    }

    /**
     * Read a messages file and return all the messages of the file.
     *
     * @param url The URL of the file.
     *
     * @return The messages.
     *
     * @throws XMLException Thrown when an error occurs during the reading.
     */
    private static Collection<IMessage> readMessagesFile(URL url) throws XMLException {
        IXMLReader<Node> reader = XML.newJavaFactory().newReader();

        reader.openURL(url);

        String source = reader.readString("source", reader.getRootElement());

        List<IMessage> messages = CollectionUtils.newList();

        for (Object currentNode : reader.getNodes("messages/message", reader.getRootElement())) {
            Message message = Messages.newMessage(
                    reader.readInt("id", currentNode),
                    reader.readString("title", currentNode),
                    reader.readString("message", currentNode),
                    new IntDate(reader.readInt("date", currentNode)),
                    source);

            messages.add(message);
        }

        Collections.sort(messages);

        FileUtils.close(reader);

        return messages;
    }
}