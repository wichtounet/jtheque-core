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

import org.jtheque.io.XMLException;
import org.jtheque.io.XMLReader;
import org.jtheque.utils.bean.IntDate;
import org.jtheque.utils.io.FileUtils;

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
     */
    public static MessageFile readMessagesFile(String strUrl) throws XMLException{
        URL url = null;
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

        List<Message> messages = new ArrayList<Message>(10);

        reader.openURL(url);

        messageFile.setSource(reader.readString("source", reader.getRootElement()));

        for (Object currentNode : reader.getNodes("messages/message", reader.getRootElement())) {
            Message message = new Message();

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