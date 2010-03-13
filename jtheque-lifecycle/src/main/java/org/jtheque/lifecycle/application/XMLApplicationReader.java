package org.jtheque.lifecycle.application;

import org.jdom.Element;
import org.jtheque.core.utils.SystemProperty;
import org.jtheque.core.application.Application;
import org.jtheque.core.utils.ImageDescriptor;
import org.jtheque.core.utils.ImageType;
import org.jtheque.io.XMLException;
import org.jtheque.io.XMLReader;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.InternationalString;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

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

/**
 * An XML application reader.
 *
 * @author Baptiste Wicht
 */
public final class XMLApplicationReader {
    private XMLReader reader;

    /**
     * Read the application file.
     *
     * @param filePath The path to the application file.
     *
     * @return The builded Application.
     */
    public Application readApplication(String filePath){
        XMLApplication application = new XMLApplication();

        reader = new XMLReader();

        openFile(filePath);

        try {
            readFile(application);
        } catch (XMLException e){
            throw new IllegalArgumentException("Unable to read the file " + filePath, e);
        } finally {
            FileUtils.close(reader);
        }

        return application;
    }

    /**
     * Open the file.
     *
     * @param filePath The path to the file.
     */
    private void openFile(String filePath){
        try {
            reader.openFile(filePath);
        } catch (XMLException e){
            throw new IllegalArgumentException("Unable to read the file " + filePath, e);
        }
    }

    /**
     * Read the file.
     *
     * @param application The application to fill.
     *
     * @throws XMLException if an error occurs during the XML processing.
     */
    private void readFile(XMLApplication application) throws XMLException{
        readVersion(application);
        readApplicationValues(application);
        readInternationalization(application);
        application.setImages(readImageDescriptor("logo"), readImageDescriptor("icon"));
        readModules(application);
        readOptions(application);
        readProperties(application);
    }

    /**
     * Read the version of the application from the file.
     *
     * @param application The application to fill.
     *
     * @throws XMLException if an error occurs during the XML processing.
     */
    private void readVersion(XMLApplication application) throws XMLException{
        String versionStr = reader.readString("@version", reader.getRootElement());

        application.setVersion(new Version(versionStr));
    }

    /**
     * Read the application values (repository, messages file) from the file.
     *
     * @param application The application to fill.
     *
     * @throws XMLException if an error occurs during the XML processing.
     */
    private void readApplicationValues(XMLApplication application) throws XMLException{
        String folder = reader.readString("folder", reader.getRootElement());

        if (StringUtils.isEmpty(folder) || !new File(folder).exists()){
            application.setProperty("application.folder.path", new File(SystemProperty.USER_DIR.get()).getParentFile().getAbsolutePath());
        } else {
            application.setProperty("application.folder.path", new File(folder).getAbsolutePath());
        }

        application.setProperty("application.repository", reader.readString("repository", reader.getRootElement()));
        application.setProperty("application.messages", reader.readString("messages", reader.getRootElement()));
    }

    /**
     * Read all the internationalized values of the application from the file.
     *
     * @param application The application to fill.
     *
     * @throws XMLException if an error occurs during the XML processing.
     */
    private void readInternationalization(XMLApplication application) throws XMLException{
        Object i18nElement = reader.getNode("i18n", reader.getRootElement());

        readLanguages(i18nElement, application);
        readApplicationProperties(i18nElement, application);
    }

    /**
     * Read all the supported languages of the application.
     *
     * @param application The application to fill.
     * @param i18nElement The i18n XML element.
     *
     * @throws XMLException If an errors occurs during the XML processing.
     */
    private void readLanguages(Object i18nElement, XMLApplication application) throws XMLException{
        if(reader.existsNode("languages", i18nElement)){
            Collection<Element> nodes = reader.getNodes("languages/language", i18nElement);

            Collection<String> languages = new ArrayList<String>(nodes.size());

            for (Element languageElement : nodes){
                languages.add(languageElement.getText());
            }

            nodes = reader.getNodes("languages/*", i18nElement);

            for (Element languageElement : nodes){
                languages.add(languageElement.getName());
            }

            application.setSupportedLanguages(languages.toArray(new String[languages.size()]));
        }
    }

    /**
     * Read the application internationalisation properties.
     *
     * @param application The application to fill.
     * @param i18nElement The i18n XML element.
     *
     * @throws XMLException If an errors occurs during the XML processing.
     */
    private void readApplicationProperties(Object i18nElement, XMLApplication application) throws XMLException{
        if (reader.getNode("files", i18nElement) != null || reader.getNode("name", i18nElement) == null){
            application.setApplicationProperties(new I18nAplicationProperties());
        } else {
            DirectValuesApplicationProperties props = new DirectValuesApplicationProperties();

            props.setName(readInternationalString("name", i18nElement));
            props.setAuthor(readInternationalString("author", i18nElement));
            props.setSite(readInternationalString("site", i18nElement));
            props.setEmail(readInternationalString("email", i18nElement));
            props.setCopyright(readInternationalString("copyright", i18nElement));

            application.setApplicationProperties(props);
        }
    }

    /**
     * Read an international string from the file.
     *
     * @param path The path the international string element.
     * @param parentElement The parent element.
     *
     * @return The internationalized string.
     *
     * @throws XMLException if an error occurs during the XML processing.
     */
    private InternationalString readInternationalString(String path, Object parentElement) throws XMLException{
        InternationalString internationalString = new InternationalString();

        Collection<Element> elements = reader.getNodes(path + "/*", parentElement);

        for (Element child : elements){
            internationalString.put(child.getName(), child.getText());
        }

        return internationalString;
    }

    /**
     * Read the window icon information from the file.
     *
     * @param node The node to read the image from.
     *
     * @throws XMLException if an error occurs during the XML processing.
     *
     * @return Return the read image descriptor. If the node doesn't exists a default ImageDescriptor with
     * the name of the node as the image and PNG type is returned.
     */
    private ImageDescriptor readImageDescriptor(String node) throws XMLException{
        if(reader.existsNode(node, reader.getRootElement())){
            Object iconElement = reader.getNode(node, reader.getRootElement());

            StringBuilder path = new StringBuilder(SystemProperty.USER_DIR.get());
			path.append("images/");
			path.append(reader.readString("image", iconElement));
			
            if(reader.existsValue("@image", iconElement)){
                path.append(reader.readString("@image", iconElement));
            } else {
                path.append(reader.readString("image", iconElement));
            }

            ImageType type;

            if(reader.existsNode("type", iconElement)){
                String typeStr = reader.readString("type", iconElement);
                type = StringUtils.isEmpty(typeStr) ? ImageType.PNG : ImageType.resolve(typeStr);
            } else if(reader.existsValue("@type", iconElement)){
                String typeStr = reader.readString("@type", iconElement);
                type = StringUtils.isEmpty(typeStr) ? ImageType.PNG : ImageType.resolve(typeStr);
            } else {
                type = ImageType.PNG;
            }

            return new ImageDescriptor(path.toString(), type);
        }

        return new ImageDescriptor(node, ImageType.PNG);
    }

    private void readModules(XMLApplication application) throws XMLException{
        if(reader.existsNode("modules", reader.getRootElement())){
            Object modulesElement = reader.getNode("modules", reader.getRootElement());

            if(reader.existsValue("@discovery", modulesElement) && StringUtils.isNotEmpty(reader.readString("@discovery", modulesElement))){
                String discovery = reader.readString("@discovery", modulesElement);

                application.setAutoDiscovery("true".equals(discovery));
            }

            for (Element child : reader.getNodes("*", modulesElement)){
                application.getModules().add(child.getName());
            }
        }
    }

    /**
     * Read the application options from the file.
     *
     * @param application The application to fill.
     *
     * @throws XMLException if an error occurs during the XML processing.
     */
    private void readOptions(XMLApplication application) throws XMLException{
        if(reader.existsNode("options", reader.getRootElement())){
            Object optionsElement = reader.getNode("options", reader.getRootElement());

            if(reader.existsValue("licence", optionsElement) && StringUtils.isNotEmpty(reader.readString("licence", optionsElement))){
                application.displayLicence();
                application.setProperty("application.licence", SystemProperty.USER_DIR.get() + reader.readString("licence", optionsElement));
            }
        }
    }

    /**
     * Read the application properties from the file.
     *
     * @param application The application to fill.
     *
     * @throws XMLException if an error occurs during the XML processing.
     */
    private void readProperties(XMLApplication application) throws XMLException{
        Collection<Element> nodes = reader.getNodes("properties/*", reader.getRootElement());

        for (Element propertyElement : nodes){
            application.setProperty(propertyElement.getName(), propertyElement.getText());
        }
    }
}