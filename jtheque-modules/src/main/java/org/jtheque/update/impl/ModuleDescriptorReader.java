package org.jtheque.update.impl;

import org.jtheque.resources.impl.ModuleDescriptor;
import org.jtheque.resources.impl.ModuleVersion;
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

public class ModuleDescriptorReader {
    /**
     * Read the module from the given URL.
     *
     * @param url The url to the module descriptor.
     *
     * @return The module descriptor.
     */
    public static ModuleDescriptor readModuleDescriptor(String url) {
        XMLReader<Node> reader = XML.newJavaFactory().newReader();

        try {
            return readModuleDescriptor(reader, new URL(url));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Read the module descriptor using the given reader and url.
     *
     * @param reader The XML Reader
     * @param url    The URL of the file.
     *
     * @return The module descriptor.
     */
    private static ModuleDescriptor readModuleDescriptor(XMLReader<Node> reader, URL url) {
        try {
            reader.openURL(url);

            String id = reader.readString("id", reader.getRootElement());

            ModuleDescriptor descriptor = new ModuleDescriptor(id);

            for (Object currentNode : reader.getNodes("version", reader.getRootElement())) {
                descriptor.addVersion(readModuleVersion(currentNode, reader));
            }

            return descriptor;
        } catch (XMLException e) {
            LoggerFactory.getLogger(ModuleDescriptorReader.class).error("Unable to read versions file", e);
        } finally {
            FileUtils.close(reader);
        }

        return null;
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
    private static ModuleVersion readModuleVersion(Object currentNode, XMLReader<Node> reader) throws XMLException {
        ModuleVersion resourceVersion = new ModuleVersion(Version.get(reader.readString("@name", currentNode)));

        //TODO readResources(currentNode, reader, resourceVersion);

        if (reader.existsNode("module", currentNode)) {
            resourceVersion.setCoreVersion(Version.get(reader.readString("module/@core", currentNode)));
            resourceVersion.setModuleFile(reader.readString("module/file", currentNode));
            resourceVersion.setModuleURL(reader.readString("module/url", currentNode));
        }

        return resourceVersion;
    }
}
