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
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.XMLException;
import org.jtheque.xml.utils.XMLReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Baptiste Wicht
 */
final class MessageFileReader {
    /**
     * Construct a new MessageFileReader. This constructor is private because
     * all methods are static.
     */
    private MessageFileReader() {
        super();
    }

    /**
     * Read a messages file and return it.
     *
     * @param strUrl The URL of the file.
     * @return The versions file.
     * @throws XMLException If there is an error reading the messages file.
     */
    public static MessageFile readMessagesFile(String strUrl) throws XMLException {
        URL url;

        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            throw new XMLException("Unable to get the messages. ", e);
        }

        return readMessagesFile(url);
    }

    /**
     * Read a messages file and return it.
     *
     * @param url The URL of the file.
     * @return The messages file.
     * @throws XMLException Thrown when an error occurs during the reading.
     */
    private static MessageFile readMessagesFile(URL url) throws XMLException {
        MessageFile messageFile = new MessageFile();

        XMLReader reader = new XMLReader();

        List<IMessage> messages = new ArrayList<IMessage>(10);

        reader.openURL(url);

        messageFile.setSource(reader.readString("source", reader.getRootElement()));

        for (Object currentNode : reader.getNodes("messages/message", reader.getRootElement())) {
            IMessage message = new Message();

            message.setId(reader.readInt("id", currentNode));
            message.setDate(new IntDate(reader.readInt("date", currentNode)));
            message.setTitle(reader.readString("title", currentNode));
            message.setMessage(reader.readString("message", currentNode));
            message.setSource(messageFile.getSource());

            messages.add(message);
        }

        Collections.sort(messages);

        messageFile.setMessages(messages);

        FileUtils.close(reader);

        return messageFile;
    }
}