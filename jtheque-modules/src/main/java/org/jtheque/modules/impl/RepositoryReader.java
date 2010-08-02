package org.jtheque.modules.impl;

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

import org.jtheque.core.able.Core;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.InternationalString;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.XMLReader;
import org.jtheque.xml.utils.XML;
import org.jtheque.xml.utils.XMLException;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 * A reader for repository XML file.
 *
 * @author Baptiste Wicht
 */
public final class RepositoryReader {
    private final org.jtheque.modules.impl.Repository repository = new org.jtheque.modules.impl.Repository();
    private final XMLReader<Node> reader = XML.newJavaFactory().newReader();

    /**
     * Read a repository file.
     *
     * @param strUrl The URL of the repository file.
     *
     * @return The repository.
     */
    public org.jtheque.modules.able.Repository read(String strUrl) {
        try {
            reader.openURL(strUrl);

            read();
        } catch (XMLException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to get information from repository", e);
        } finally {
            FileUtils.close(reader);
        }

        return repository;
    }

    /**
     * Read all the informations from the reader.
     *
     * @throws XMLException If an error occurs during the XML reading process.
     */
    private void read() throws XMLException {
        readTitle(reader);
        readApplication(reader);
        readModules(reader);
    }

    /**
     * Read the application informations from the reader.
     *
     * @param reader The reader to use.
     *
     * @throws XMLException If an error occurs during the XML reading process.
     */
    private void readApplication(XMLReader<Node> reader) throws XMLException {
        repository.setApplication(reader.readString("application", reader.getRootElement()));
    }

    /**
     * Read the modules informations from the reader.
     *
     * @param reader The reader to use.
     *
     * @throws XMLException If an error occurs during the XML reading process.
     */
    private void readModules(XMLReader<Node> reader) throws XMLException {
        for (Object currentNode : reader.getNodes("modules/module", reader.getRootElement())) {
            ModuleDescription module = new ModuleDescription();

            module.setId(reader.readString("id", currentNode));
            module.setName(reader.readString("name", currentNode));

            if (StringUtils.isEmpty(reader.readString("core", currentNode))) {
                module.setCoreVersion(Core.VERSION);
            } else {
                module.setCoreVersion(Version.get(reader.readString("core", currentNode)));
            }

            module.setVersionsFileURL(reader.readString("versions", currentNode));

            InternationalString description = new InternationalString();

            for (Node child : reader.getNodes("description/*", currentNode)) {
                description.put(child.getNodeName(), child.getTextContent());
            }

            module.setDescription(description);

            repository.getModules().add(module);
        }
    }

    /**
     * Read the title of the repository from the reader.
     *
     * @param reader The reader to use.
     *
     * @throws XMLException If an error occurs during the XML reading process.
     */
    private void readTitle(XMLReader<Node> reader) throws XMLException {
        InternationalString title = new InternationalString();

        for (Node child : reader.getNodes("title/*", reader.getRootElement())) {
            title.put(child.getNodeName(), child.getTextContent());
        }

        repository.setTitle(title);
    }
}