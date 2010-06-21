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
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.IXMLReader;
import org.jtheque.xml.utils.XML;
import org.jtheque.xml.utils.XMLException;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A reader for the versions file.
 *
 * @author Baptiste Wicht
 */
public final class DescriptorReader {
    private DescriptorReader() {
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
        IXMLReader<Node> reader = XML.newJavaFactory().newReader();

        try {
            return readResourceDescriptor(reader, new URL(url));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static ModuleDescriptor readModuleDescriptor(String url) {
        IXMLReader<Node> reader = XML.newJavaFactory().newReader();

        try {
            return readModuleDescriptor(reader, new URL(url));
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
    private static ResourceDescriptor readResourceDescriptor(IXMLReader<Node> reader, URL url) {
        try {
            reader.openURL(url);

            String id = reader.readString("id", reader.getRootElement());

            ResourceDescriptor descriptor = new ResourceDescriptor(id);

            for (Object currentNode : reader.getNodes("version", reader.getRootElement())) {
                descriptor.addVersion(readResourceVersion(currentNode, reader));
            }

            return descriptor;
        } catch (XMLException e) {
            LoggerFactory.getLogger(DescriptorReader.class).error("Unable to read versions file", e);
        } finally {
            FileUtils.close(reader);
        }

        return null;
    }

    private static ModuleDescriptor readModuleDescriptor(IXMLReader<Node> reader, URL url) {
        try {
            reader.openURL(url);

            String id = reader.readString("id", reader.getRootElement());

            ModuleDescriptor descriptor = new ModuleDescriptor(id);

            for (Object currentNode : reader.getNodes("version", reader.getRootElement())) {
                descriptor.addVersion(readModuleVersion(currentNode, reader));
            }

            return descriptor;
        } catch (XMLException e) {
            LoggerFactory.getLogger(DescriptorReader.class).error("Unable to read versions file", e);
        } finally {
            FileUtils.close(reader);
        }

        return null;
    }

    private static ResourceVersion readResourceVersion(Object currentNode, IXMLReader<Node> reader) throws XMLException {
        Version version = new Version(reader.readString("@name", currentNode));

        ResourceVersion resourceVersion = new ResourceVersion(version);

        readResources(currentNode, reader, resourceVersion);

        return resourceVersion;
    }

    private static ModuleVersion readModuleVersion(Object currentNode, IXMLReader<Node> reader) throws XMLException {
        Version version = new Version(reader.readString("@name", currentNode));

        ModuleVersion resourceVersion = new ModuleVersion(version);

        readResources(currentNode, reader, resourceVersion);

        if (reader.existsNode("module", currentNode)) {
            resourceVersion.setCoreVersion(new Version(reader.readString("module/@core", currentNode)));
            resourceVersion.setModuleFile(reader.readString("module/@file", currentNode));
            resourceVersion.setModuleURL(reader.readString("module/@url", currentNode));
        }

        return resourceVersion;
    }

    private static void readResources(Object currentNode, IXMLReader<Node> reader, ResourceVersion resourceVersion) throws XMLException {
        for (Object libraryNode : reader.getNodes("libraries/library", currentNode)) {
            resourceVersion.addLibrary(readFileDescriptor(libraryNode, reader));
        }

        for (Object libraryNode : reader.getNodes("files/file", currentNode)) {
            resourceVersion.addFile(readFileDescriptor(libraryNode, reader));
        }
    }

    private static FileDescriptor readFileDescriptor(Object libraryNode, IXMLReader<Node> reader) throws XMLException {
        String name = reader.readString("name", libraryNode);
        String url = reader.readString("url", libraryNode);
        String version = reader.readString("version", libraryNode);

        return new FileDescriptor(name, url, new Version(version));
    }
}