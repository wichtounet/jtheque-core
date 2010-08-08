package org.jtheque.resources.impl;

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

/**
 * A reader for the versions file.
 *
 * @author Baptiste Wicht
 */
public final class ResourceDescriptorReader {
    /**
     * A Descriptor XML reader.
     */
    private ResourceDescriptorReader() {
        super();
    }

    /**
     * Read the the resource descriptor at the specified URL.
     *
     * @param url The URL of the versions file.
     *
     * @return The resource descriptor or null if the URL is invalid.
     */
    public static ResourceDescriptor readResourceDescriptor(String url) {
        XMLReader<Node> reader = XML.newJavaFactory().newReader();

        try {
            return readResourceDescriptor(reader, new URL(url));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Read a versions file and return it.
     *
     * @param reader The XML Reader
     * @param url    The URL of the file.
     *
     * @return The versions file.
     */
    private static ResourceDescriptor readResourceDescriptor(XMLReader<Node> reader, URL url) {
        try {
            reader.openURL(url);

            List<ResourceVersion> versions = CollectionUtils.newList();

            for (Object currentNode : reader.getNodes("version", reader.getRootElement())) {
                versions.add(readResourceVersion(currentNode, reader));
            }

            return new ResourceDescriptor(reader.readString("id", reader.getRootElement()), versions);
        } catch (XMLException e) {
            LoggerFactory.getLogger(ResourceDescriptorReader.class).error("Unable to read versions file", e);
        } finally {
            FileUtils.close(reader);
        }

        return null;
    }

    /**
     * Read the resource version.
     *
     * @param currentNode The current node.
     * @param reader      The XML reader.
     *
     * @return The ResourceVersion.
     *
     * @throws XMLException If an error occurs during XML parsing.
     */
    private static ResourceVersion readResourceVersion(Object currentNode, XMLReader<Node> reader) throws XMLException {
        return new ResourceVersion(
                Version.get(reader.readString("@name", currentNode)),
                reader.readString("@file", currentNode),
                reader.readString("@url", currentNode),
                reader.readBoolean("@library", currentNode));
    }
}