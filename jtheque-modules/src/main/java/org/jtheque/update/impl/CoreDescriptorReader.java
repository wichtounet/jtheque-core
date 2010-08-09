package org.jtheque.update.impl;

import org.jtheque.core.able.Core;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.XML;
import org.jtheque.xml.utils.XMLException;
import org.jtheque.xml.utils.XMLReader;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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

public class CoreDescriptorReader {
    private CoreDescriptorReader() {
        throw new AssertionError();
    }

    /**
     * Read the core descriptor at the specified URL.
     *
     * @return The core descriptor or null if there were an error during the reading.
     */
    public static CoreDescriptor readCoreDescriptor() {
        try {
            return readCoreDescriptor(new URL(Core.DESCRIPTOR_FILE_URL));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Read the core descriptor at the given URL.
     *
     * @param url The URL to read the core descriptor from.
     *
     * @return The core descriptor or null if there were a problem reading the file.
     */
    private static CoreDescriptor readCoreDescriptor(URL url) {
        XMLReader<Node> reader = XML.newJavaFactory().newReader();

        try {
            reader.openURL(url);

            List<CoreVersion> versions = CollectionUtils.newList(5);

            for (Object currentNode : reader.getNodes("version", reader.getRootElement())) {
                versions.add(readCoreVersion(currentNode, reader));
            }

            return new CoreDescriptor(versions);
        } catch (XMLException e) {
            LoggerFactory.getLogger(CoreDescriptorReader.class).error("Unable to read versions file", e);
        } finally {
            FileUtils.close(reader);
        }

        return null;
    }

    /**
     * Read the CoreVersion from the given node and using the given reader.
     *
     * @param currentNode The current node.
     * @param reader      The reader.
     *
     * @return The core version.
     *
     * @throws XMLException If an exception occurs during XML processing.
     */
    private static CoreVersion readCoreVersion(Object currentNode, XMLReader<Node> reader) throws XMLException {
        List<FileDescriptor> descriptors = CollectionUtils.newList(25);

        for (Object libraryNode : reader.getNodes("bundles/bundle", currentNode)) {
            descriptors.add(readFileDescriptor(libraryNode, reader));
        }

        return new CoreVersion(Version.get(reader.readString("@name", currentNode)), descriptors);
    }

    /**
     * Read the file descriptor.
     *
     * @param currentNode The current node.
     * @param reader      The XML reader.
     *
     * @return The FileDescriptor of the current node.
     *
     * @throws XMLException If an exception occurs during XML parsing.
     */
    private static FileDescriptor readFileDescriptor(Object currentNode, XMLReader<Node> reader) throws XMLException {
        String name = reader.readString("name", currentNode);
        String url = reader.readString("url", currentNode);
        String version = reader.readString("version", currentNode);

        return new FileDescriptor(name, url, Version.get(version));
    }
}
