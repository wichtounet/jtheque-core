package org.jtheque.io;

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

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * A reader for XML files. This reader keep a current element so it's easier to read.
 *
 * @author Baptiste Wicht
 */
public final class XMLOverReader implements Closeable {
    private InputStream stream;
    private Document document;

    private static final String OPEN_ERROR = "Error opening the file";
    private static final String READING_ERROR = "Error reading the file";

    private Element current;

    private final Deque<Entry> elements = new ArrayDeque<Entry>();

    /**
     * Open the file a the URL.
     *
     * @param strUrl The URL of the XML file.
     * @throws XMLException If an error occurs during the open process.
     */
    public void openURL(String strUrl) throws XMLException {
        URL url;
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            throw new XMLException("Invalid URL", e);
        }

        openURL(url);
    }

    /**
     * Open the file a the URL.
     *
     * @param url The URL of the XML file.
     * @throws XMLException If an error occurs during the open process.
     */
    public void openURL(URL url) throws XMLException {
        try {
            URLConnection urlConnection = url.openConnection();
            urlConnection.setUseCaches(false);

            urlConnection.connect();

            stream = urlConnection.getInputStream();

            SAXBuilder sxb = new SAXBuilder("org.apache.xerces.parsers.SAXParser");

            try {
                document = sxb.build(stream);

                current = document.getRootElement();
            } catch (JDOMException e) {
                throw new XMLException(OPEN_ERROR, e);
            }
        } catch (IOException e) {
            throw new XMLException(OPEN_ERROR, e);
        }
    }

    /**
     * Open the file.
     *
     * @param strFile The path to the file to open.
     * @throws XMLException If an error occurs during the open process.
     */
    public void openFile(String strFile) throws XMLException {
        openFile(new File(strFile));
    }

    /**
     * Open the file.
     *
     * @param file The file to open.
     * @throws XMLException If an error occurs during the open process.
     */
    public void openFile(File file) throws XMLException {
        try {
            stream = new FileInputStream(file);

            SAXBuilder sxb = new SAXBuilder();

            document = sxb.build(stream);

            current = document.getRootElement();
        } catch (JDOMException e) {
            throw new XMLException(OPEN_ERROR, e);
        } catch (IOException e) {
            throw new XMLException(OPEN_ERROR, e);
        }
    }

    @Override
    public void close() throws IOException {
        if (stream != null) {
            stream.close();
        }
    }

    /**
     * Read a node from the current element. The current element become the read node if there is one.
     *
     * @param path The path of the node.
     * @return if the node exists.
     * @throws XMLException If an error occurs during XML Processing.
     */
    public boolean readNode(String path) throws XMLException {
        try {
            Element n = (Element) XPath.newInstance(path).selectSingleNode(current);

            if (n != null) {
                current = n;

                return true;
            }
        } catch (JDOMException e) {
            throw new XMLException("Error selecting nodes", e);
        }

        return false;
    }

    /**
     * Change the current element for the parent of current element.
     */
    public void switchToParent() {
        if (current == null) {
            throw new IllegalArgumentException("No current element");
        }

        current = current.getParentElement();
    }

    /**
     * Go to the next element of the request. The first time this method is called on a specific path, the request is executed and
     * the elements enqueued. The next time, we only use the enqueued iterator to retrieve the next element.
     *
     * @param path The XPath request.
     * @return <code>true</code> if there is a next element else <code>false</code>.
     * @throws XMLException If an error occurs during XML Processing.
     */
    public boolean next(String path) throws XMLException {
        if (elements.isEmpty() || !elements.peek().getPath().equals(path)) {
            try {
                elements.addFirst(new Entry(path, XPath.newInstance(path).selectNodes(current).iterator()));
            } catch (JDOMException e) {
                throw new XMLException("Error selecting nodes", e);
            }
        }

        if (elements.peek().getElements().hasNext()) {
            current = elements.peek().getElements().next();

            return true;
        } else {
            elements.poll();

            return false;
        }
    }

    /**
     * Read a String value from the node.
     *
     * @param path The XPath request.
     * @return The string value of the request.
     * @throws XMLException If an errors occurs during the reading process.
     */
    public String readString(String path) throws XMLException {
        String value;

        try {
            value = XPath.newInstance(path).valueOf(current);
        } catch (JDOMException e) {
            throw new XMLException(READING_ERROR, e);
        }

        return value;
    }

    /**
     * Read a int value from the node.
     *
     * @param path The XPath request.
     * @return The int value of the request.
     * @throws XMLException If an errors occurs during the reading process.
     */
    public int readInt(String path) throws XMLException {
        String value;

        try {
            value = XPath.newInstance(path).valueOf(current);
        } catch (JDOMException e) {
            throw new XMLException(READING_ERROR, e);
        }

        return Integer.parseInt(value);
    }

    /**
     * Read a double value from the node.
     *
     * @param path The XPath request.
     * @return The double value of the request.
     * @throws XMLException If an errors occurs during the reading process.
     */
    public double readDouble(String path) throws XMLException {
        String value;

        try {
            value = XPath.newInstance(path).valueOf(current);
        } catch (JDOMException e) {
            throw new XMLException(READING_ERROR, e);
        }

        return Double.parseDouble(value);
    }

    /**
     * Read a boolean value from the node.
     *
     * @param path The XPath request.
     * @return The boolean value of the request.
     * @throws XMLException If an errors occurs during the reading process.
     */
    public boolean readBoolean(String path) throws XMLException {
        String value;

        try {
            value = XPath.newInstance(path).valueOf(current);
        } catch (JDOMException e) {
            throw new XMLException(READING_ERROR, e);
        }

        return Boolean.parseBoolean(value);
    }

    /**
     * Read a long value from the node.
     *
     * @param path The XPath request.
     * @return The double value of the request.
     * @throws XMLException If an errors occurs during the reading process.
     */
    public long readLong(String path) throws XMLException {
        String value;

        try {
            value = XPath.newInstance(path).valueOf(current);
        } catch (JDOMException e) {
            throw new XMLException(READING_ERROR, e);
        }

        return Long.parseLong(value);
    }

    /**
     * An entry of the elements stack.
     *
     * @author Baptiste Wicht
     */
    private static final class Entry {
        private final String path;
        private final Iterator<Element> elements;

        /**
         * Construct a new Entry.
         *
         * @param path     The XPath request.
         * @param elements The iterator on the found elements.
         */
        private Entry(String path, Iterator<Element> elements) {
            super();

            this.path = path;
            this.elements = elements;
        }

        /**
         * Return the XPath request.
         *
         * @return The XPath request.
         */
        public String getPath() {
            return path;
        }

        /**
         * Return the iterator on the results of the request.
         *
         * @return The iterator on the results of the request.
         */
        public Iterator<Element> getElements() {
            return elements;
        }
    }
}