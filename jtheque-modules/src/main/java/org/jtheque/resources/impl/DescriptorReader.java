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

import org.jtheque.utils.StringUtils;
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
    /**
     * A Descriptor XML reader.
     */
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

    public static CoreDescriptor readCoreDescriptor(String url) {
        IXMLReader<Node> reader = XML.newJavaFactory().newReader();

        try {
            return readCoreDescriptor(reader, new URL(url));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Read the module from the given URL.
     *
     * @param url The url to the module descriptor.
     *
     * @return The module descriptor.
     */
    public static ModuleDescriptor readModuleDescriptor(String url) {
        IXMLReader<Node> reader = XML.newJavaFactory().newReader();

        try {
            return readModuleDescriptor(reader, new URL(url));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private static CoreDescriptor readCoreDescriptor(IXMLReader<Node> reader, URL url) {
        try {
            reader.openURL(url);

            CoreDescriptor descriptor = new CoreDescriptor();

            for (Object currentNode : reader.getNodes("version", reader.getRootElement())) {
                descriptor.addVersion(readCoreVersion(currentNode, reader));
            }

            return descriptor;
        } catch (XMLException e) {
            LoggerFactory.getLogger(DescriptorReader.class).error("Unable to read versions file", e);
        } finally {
            FileUtils.close(reader);
        }

        return null;
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

    /**
     * Read the module descriptor using the given reader and url.
     *
     * @param reader The XML Reader
     * @param url    The URL of the file.
     *
     * @return The module descriptor.
     */
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

    private static CoreVersion readCoreVersion(Object currentNode, IXMLReader<Node> reader) throws XMLException {
        CoreVersion resourceVersion = new CoreVersion(new Version(reader.readString("@name", currentNode)));

        readResources(currentNode, reader, resourceVersion);

        return resourceVersion;
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
    private static ResourceVersion readResourceVersion(Object currentNode, IXMLReader<Node> reader) throws XMLException {
        ResourceVersion resourceVersion = new ResourceVersion(new Version(reader.readString("@name", currentNode)));

        readResources(currentNode, reader, resourceVersion);

        return resourceVersion;
    }

    /**
     * Read the module version.
     *
     * @param currentNode The current node.
     * @param reader      The XML reader.
     *
     * @return The ModuleVersion.
     *
     * @throws XMLException If an error occurs during XML parsing.
     */
    private static ModuleVersion readModuleVersion(Object currentNode, IXMLReader<Node> reader) throws XMLException {
        ModuleVersion resourceVersion = new ModuleVersion(new Version(reader.readString("@name", currentNode)));

        readResources(currentNode, reader, resourceVersion);

        if (reader.existsNode("module", currentNode)) {
            resourceVersion.setCoreVersion(new Version(reader.readString("module/@core", currentNode)));
            resourceVersion.setModuleFile(reader.readString("module/file", currentNode));
            resourceVersion.setModuleURL(reader.readString("module/url", currentNode));
        }

        return resourceVersion;
    }

    private static void readResources(Object currentNode, IXMLReader<Node> reader, CoreVersion coreVersion) throws XMLException {
        for (Object libraryNode : reader.getNodes("bundles/bundle", currentNode)) {
            coreVersion.addBundle(readFileDescriptor(libraryNode, reader));
        }
    }

    /**
     * Read the resources of the descriptor.
     *
     * @param currentNode     The current node.
     * @param reader          The XML reader.
     * @param resourceVersion The resource version.
     *
     * @throws XMLException If an exception occurs during XML parsing.
     */
    private static void readResources(Object currentNode, IXMLReader<Node> reader, ResourceVersion resourceVersion) throws XMLException {
        for (Object libraryNode : reader.getNodes("libraries/library", currentNode)) {
            resourceVersion.addLibrary(readFileDescriptor(libraryNode, reader));
        }

        for (Object libraryNode : reader.getNodes("files/file", currentNode)) {
            resourceVersion.addFile(readFileDescriptor(libraryNode, reader));
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
    private static FileDescriptor readFileDescriptor(Object currentNode, IXMLReader<Node> reader) throws XMLException {
        String name = reader.readString("name", currentNode);
        String url = reader.readString("url", currentNode);
        String version = reader.readString("version", currentNode);

        if(StringUtils.isNotEmpty(version)){
            return new FileDescriptor(name, url, new Version(version));
        } else {
            return new FileDescriptor(name, url);
        }
    }
}