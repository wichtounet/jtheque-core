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
public final class ResourceDescriptorReader {
    private final IXMLReader<Node> reader = XML.newJavaFactory().newReader();
    private ResourceDescriptor resourceDescriptor;

    /**
     * Read the the resource descriptor at the specified URL.
     *
     * @param versionsFileURL The URL of the versions file.
     *
     * @return The resource descriptor or null if the URL is invalid.
     */
    public ResourceDescriptor readURL(String versionsFileURL) {
        try {
            return readDescriptor(new URL(versionsFileURL));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Read a versions file and return it.
     *
     * @param url The URL of the file.
     *
     * @return The versions file.
     */
    private ResourceDescriptor readDescriptor(URL url) {
        try {
            reader.openURL(url);

            String id = reader.readString("id", reader.getRootElement());

            resourceDescriptor = new ResourceDescriptor(id);

            for (Object currentNode : reader.getNodes("version", reader.getRootElement())) {
                readVersion(currentNode);
            }
        } catch (XMLException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to read versions file", e);
        } finally {
            FileUtils.close(reader);
        }

        return resourceDescriptor;
    }

    private void readVersion(Object currentNode) throws XMLException {
        Version version = new Version(reader.readString("@name", currentNode));

        ResourceVersion resourceVersion = new ResourceVersion(version);

        for (Object libraryNode : reader.getNodes("libraries/library", currentNode)) {
            resourceVersion.addLibrary(readFileDescriptor(libraryNode));
        }

        for (Object libraryNode : reader.getNodes("files/file", currentNode)) {
            resourceVersion.addFile(readFileDescriptor(libraryNode));
        }

        resourceDescriptor.addVersion(resourceVersion);
    }

    private FileDescriptor readFileDescriptor(Object libraryNode) throws XMLException {
        String name = reader.readString("name", libraryNode);
        String url = reader.readString("url", libraryNode);

        return new FileDescriptor(name, url);
    }
}