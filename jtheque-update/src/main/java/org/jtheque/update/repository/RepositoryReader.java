package org.jtheque.update.repository;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jdom.Element;
import org.jtheque.core.ICore;
import org.jtheque.io.XMLException;
import org.jtheque.io.XMLReader;
import org.jtheque.modules.impl.ModuleDescription;
import org.jtheque.modules.impl.Repository;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.InternationalString;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileUtils;
import org.slf4j.LoggerFactory;

/**
 * A reader for repository XML file.
 *
 * @author Baptiste Wicht
 */
public final class RepositoryReader {
    private final Repository repository;
    private final XMLReader reader;

    /**
     * Construct a new RepositoryReader.
     */
    public RepositoryReader() {
        super();

        repository = new Repository();
        reader = new XMLReader();
    }

    /**
     * Read a repository file.
     *
     * @param strUrl The URL of the repository file.
     * @return The repository.
     */
    public Repository read(String strUrl) {
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
     * @throws XMLException If an error occurs during the XML reading process.
     */
    private void readApplication(XMLReader reader) throws XMLException {
        repository.setApplication(reader.readString("application", reader.getRootElement()));
    }

    /**
     * Read the modules informations from the reader.
     *
     * @param reader The reader to use.
     * @throws XMLException If an error occurs during the XML reading process.
     */
    private void readModules(XMLReader reader) throws XMLException {
        for (Object currentNode : reader.getNodes("modules/module", reader.getRootElement())) {
            ModuleDescription module = new ModuleDescription();

            module.setId(reader.readString("id", currentNode));
            module.setName(reader.readString("name", currentNode));

            if (StringUtils.isEmpty(reader.readString("core", currentNode))) {
                module.setCoreVersion(ICore.VERSION);
            } else {
                module.setCoreVersion(new Version(reader.readString("core", currentNode)));
            }

            module.setVersionsFileURL(reader.readString("versions", currentNode));

            Element descriptionElement = reader.getNode("description", currentNode);

            InternationalString description = new InternationalString();

            for (Object child : descriptionElement.getChildren()) {
                Element childElement = (Element) child;

                description.put(childElement.getName(), childElement.getValue());
            }

            module.setDescription(description);

            repository.getModules().add(module);
        }
    }

    /**
     * Read the title of the repository from the reader.
     *
     * @param reader The reader to use.
     * @throws XMLException If an error occurs during the XML reading process.
     */
    private void readTitle(XMLReader reader) throws XMLException {
        InternationalString title = new InternationalString();

        Element titleElement = reader.getNode("title", reader.getRootElement());

        for (Object child : titleElement.getChildren()) {
            Element childElement = (Element) child;

            title.put(childElement.getName(), childElement.getValue());
        }

        repository.setTitle(title);
    }
}