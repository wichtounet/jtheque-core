package org.jtheque.update.impl;

import org.jtheque.core.able.Core;
import org.jtheque.resources.impl.CoreDescriptor;
import org.jtheque.resources.impl.CoreVersion;
import org.jtheque.resources.impl.FileDescriptor;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.XML;
import org.jtheque.xml.utils.XMLException;
import org.jtheque.xml.utils.XMLReader;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.net.MalformedURLException;
import java.net.URL;

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
 * Created by IntelliJ IDEA. User: wichtounet Date: Aug 8, 2010 Time: 8:12:42 PM To change this template use File |
 * Settings | File Templates.
 */
public class CoreDescriptorReader {

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

            CoreDescriptor descriptor = new CoreDescriptor();

            for (Object currentNode : reader.getNodes("version", reader.getRootElement())) {
                descriptor.addVersion(readCoreVersion(currentNode, reader));
            }

            return descriptor;
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
        CoreVersion resourceVersion = new CoreVersion(Version.get(reader.readString("@name", currentNode)));

        readResources(currentNode, reader, resourceVersion);

        return resourceVersion;
    }

    /**
     * Read the resources from the given node and using the given reader and then fill the core version with the
     * resources.
     *
     * @param currentNode The current node.
     * @param reader      The XML reader.
     * @param coreVersion The core version to fill.
     *
     * @throws XMLException If an exceptions occurs during XML processing.
     */
    private static void readResources(Object currentNode, XMLReader<Node> reader, CoreVersion coreVersion) throws XMLException {
        for (Object libraryNode : reader.getNodes("bundles/bundle", currentNode)) {
            coreVersion.addBundle(readFileDescriptor(libraryNode, reader));
        }
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

        if (StringUtils.isNotEmpty(version)) {
            return new FileDescriptor(name, url, Version.get(version));
        } else {
            return new FileDescriptor(name, url);
        }
    }
}
