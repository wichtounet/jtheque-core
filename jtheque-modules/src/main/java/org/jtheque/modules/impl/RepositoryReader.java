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

import org.jtheque.core.Core;
import org.jtheque.modules.ModuleDescription;
import org.jtheque.modules.Repository;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.annotations.GuardedBy;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.bean.InternationalString;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.XML;
import org.jtheque.xml.utils.XMLException;
import org.jtheque.xml.utils.XMLReader;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Map;

/**
 * A reader for repository XML file.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
final class RepositoryReader {
    @GuardedBy("RepositoryReader.class")
    private static Repository repository;

    /**
     * Utility class, not instantiable.
     */
    private RepositoryReader() {
        throw new AssertionError();
    }

    /**
     * Return the cached repository from the given URL.
     *
     * @param strUrl The URL of the repository.
     *
     * @return The cached repository.
     */
    static Repository getCachedRepository(String strUrl) {
        synchronized (RepositoryReader.class) {
            if (repository == null) {
                repository = read(strUrl);
            }

            return repository;
        }
    }

    /**
     * Read a repository file.
     *
     * @param strUrl The URL of the repository file.
     *
     * @return The repository.
     */
    private static Repository read(String strUrl) {
        XMLReader<Node> reader = XML.newJavaFactory().newReader();

        try {
            reader.openURL(strUrl);

            return new RepositoryImpl(readTitle(reader), readApplication(reader), readModules(reader));
        } catch (XMLException e) {
            LoggerFactory.getLogger(RepositoryReader.class).error("Unable to get information from repository", e);
        } finally {
            FileUtils.close(reader);
        }

        return null;
    }

    /**
     * Read the application informations from the reader.
     *
     * @param reader The reader to use.
     *
     * @return The name of the application.
     *
     * @throws XMLException If an error occurs during the XML reading process.
     */
    private static String readApplication(XMLReader<Node> reader) throws XMLException {
        return reader.readString("application", reader.getRootElement());
    }

    /**
     * Read the modules informations from the reader.
     *
     * @param reader The reader to use.
     *
     * @return The List of modules contained in the repository.
     *
     * @throws XMLException If an error occurs during the XML reading process.
     */
    private static List<ModuleDescription> readModules(XMLReader<Node> reader) throws XMLException {
        List<ModuleDescription> descriptions = CollectionUtils.newList();

        for (Object currentNode : reader.getNodes("modules/module", reader.getRootElement())) {
            Version coreVersion =
                    StringUtils.isEmpty(reader.readString("core", currentNode)) ?
                            Core.VERSION :
                            Version.get(reader.readString("core", currentNode));

            Map<String, String> resources = CollectionUtils.newHashMap(5);

            for (Node child : reader.getNodes("description/*", currentNode)) {
                resources.put(child.getNodeName(), child.getTextContent());
            }

            descriptions.add(new ModuleDescriptionImpl(
                    reader.readString("id", currentNode),
                    reader.readString("name", currentNode),
                    new InternationalString(resources),
                    reader.readString("versions", currentNode),
                    coreVersion));
        }

        return descriptions;
    }

    /**
     * Read the title of the repository from the reader.
     *
     * @param reader The reader to use.
     *
     * @return Read the title of the repository.
     *
     * @throws XMLException If an error occurs during the XML reading process.
     */
    private static InternationalString readTitle(XMLReader<Node> reader) throws XMLException {
        Map<String, String> resources = CollectionUtils.newHashMap(5);

        for (Node child : reader.getNodes("title/*", reader.getRootElement())) {
            resources.put(child.getNodeName(), child.getTextContent());
        }

        return new InternationalString(resources);
    }
}