package org.jtheque.core.managers.core.application;

import org.jdom.Element;
import org.jtheque.core.managers.resource.ImageDescriptor;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.utils.SystemProperty;
import org.jtheque.core.utils.file.XMLException;
import org.jtheque.core.utils.file.XMLReader;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.InternationalString;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
 * An XML Application. It seems a JTheque Core Application who's read from an XML file.
 *
 * @author Baptiste Wicht
 */
public final class XMLApplication implements Application {
    private final XMLReader reader;

    private Version version;

    private ApplicationProperties applicationProperties;

    private ImageDescriptor icon;
    private ImageDescriptor logo;

    private String folderPath;
    private String licenceFilePath;
    private String applicationRepository;
    private String applicationMessageFileURL;

    private boolean displayLicence;

    private String[] supportedLanguages = {"fr", "en"};

    private final Map<String, String> properties = new HashMap<String, String>(5);

    /**
     * Construct a new XMLApplication from an XML file.
     *
     * @param filePath The path to the XML file.
     */
    public XMLApplication(String filePath){
        super();

        reader = new XMLReader();

        openFile(filePath);

        try {
            readFile();
        } catch (XMLException e){
            throw new IllegalArgumentException("Unable to read the file " + filePath, e);
        } finally {
            try {
                reader.close();
            } catch (IOException e){
                throw new IllegalArgumentException("Unable to close the file " + filePath, e);
            }
        }
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
     * @throws XMLException if an error occurs during the XML processing.
     */
    private void readFile() throws XMLException{
        readVersion();
        readApplicationValues();
        readInternationalization();
        readImages();
        readOptions();
        readProperties();
    }

    /**
     * Read the version of the application from the file.
     *
     * @throws XMLException if an error occurs during the XML processing.
     */
    private void readVersion() throws XMLException{
        String versionStr = reader.readString("@version", reader.getRootElement());

        version = new Version(versionStr);
    }

    /**
     * Read the application values (repository, messages file) from the file.
     *
     * @throws XMLException if an error occurs during the XML processing.
     */
    private void readApplicationValues() throws XMLException{
        String folder = reader.readString("folder", reader.getRootElement());

        if (StringUtils.isEmpty(folder) || !new File(folder).exists()){
            folderPath = new File(SystemProperty.USER_DIR.get()).getParentFile().getAbsolutePath();
        } else {
            folderPath = new File(folder).getAbsolutePath();
        }

        applicationRepository = reader.readString("repository", reader.getRootElement());
        applicationMessageFileURL = reader.readString("messages", reader.getRootElement());
    }

    /**
     * Read all the internationalized values of the application from the file.
     *
     * @throws XMLException if an error occurs during the XML processing.
     */
    private void readInternationalization() throws XMLException{
        Object i18nElement = reader.getNode("i18n", reader.getRootElement());

        readLanguages(i18nElement);
        readApplicationProperties(i18nElement);
    }

    /**
     * Read all the supported languages of the application.
     *
     * @param i18nElement The i18n XML element.
     *
     * @throws XMLException If an errors occurs during the XML processing.
     */
    private void readLanguages(Object i18nElement) throws XMLException{
        Collection<Element> nodes = reader.getNodes("languages/language", i18nElement);

        Collection<String> languages = new ArrayList<String>(nodes.size());

        for (Element languageElement : nodes){
            languages.add(languageElement.getText());
        }

        nodes = reader.getNodes("languages/*", i18nElement);

        for (Element languageElement : nodes){
            languages.add(languageElement.getName());
        }

        supportedLanguages = languages.toArray(new String[languages.size()]);
    }

    /**
     * Read the application internationalisation properties.
     *
     * @param i18nElement The i18n XML element.
     *
     * @throws XMLException If an errors occurs during the XML processing.
     */
    private void readApplicationProperties(Object i18nElement) throws XMLException{
        if (reader.getNode("files", i18nElement) != null || reader.getNode("name", i18nElement) == null){
            applicationProperties = new I18nAplicationProperties();
        } else {
            DirectValuesApplicationProperties props = new DirectValuesApplicationProperties();

            props.setName(readInternationalString("name", i18nElement));
            props.setAuthor(readInternationalString("author", i18nElement));
            props.setSite(readInternationalString("site", i18nElement));
            props.setEmail(readInternationalString("email", i18nElement));
            props.setCopyright(readInternationalString("copyright", i18nElement));

            applicationProperties = props;
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
     * Read the images from the file.
     *
     * @throws XMLException if an error occurs during the XML processing.
     */
    private void readImages() throws XMLException{
        logo = readImageDescriptor("logo");
        icon = readImageDescriptor("icon");
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

            String path = SystemProperty.USER_DIR.get() + "images/" + reader.readString("image", iconElement);

            if(reader.existsValue("@image", iconElement)){
                path += reader.readString("@image", iconElement);
            } else {
                path += reader.readString("image", iconElement);
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

            return new ImageDescriptor(path, type);
        }

        return new ImageDescriptor(node, ImageType.PNG);
    }

    /**
     * Read the application options from the file.
     *
     * @throws XMLException if an error occurs during the XML processing.
     */
    private void readOptions() throws XMLException{
        Object optionsElement = reader.getNode("options", reader.getRootElement());
        
        if(reader.existsValue("licence", optionsElement) && StringUtils.isNotEmpty(reader.readString("licence", optionsElement))){
            displayLicence = true;
            licenceFilePath = SystemProperty.USER_DIR.get() + reader.readString("licence", optionsElement);
        }
    }

    /**
     * Read the application properties from the file.
     *
     * @throws XMLException if an error occurs during the XML processing.
     */
    private void readProperties() throws XMLException{
        Collection<Element> nodes = reader.getNodes("properties/*", reader.getRootElement());

        for (Element propertyElement : nodes){
            properties.put(propertyElement.getName(), propertyElement.getText());
        }
    }

    @Override
    public Version getVersion(){
        return version;
    }

    @Override
    public String getAuthor(){
        return applicationProperties.getAuthor();
    }

    @Override
    public String getName(){
        return applicationProperties.getName();
    }

    @Override
    public String getSite(){
        return applicationProperties.getSite();
    }

    @Override
    public String getEmail(){
        return applicationProperties.getEmail();
    }

    @Override
    public String getCopyright(){
        return applicationProperties.getCopyright();
    }

    @Override
    public String getLogo(){
        return logo.getImage();
    }

    @Override
    public ImageType getLogoType(){
        return logo.getType();
    }

    @Override
    public String getWindowIcon(){
        return icon.getImage();
    }

    @Override
    public ImageType getWindowIconType(){
        return icon.getType();
    }

    @Override
    public boolean isDisplayLicence(){
        return displayLicence;
    }

    @Override
    public String getRepository(){
        return applicationRepository;
    }

    @Override
    public String getMessageFileURL(){
        return applicationMessageFileURL;
    }

    @Override
    public String[] getSupportedLanguages(){
        return ArrayUtils.copyOf(supportedLanguages);
    }

    @Override
    public String getProperty(String key){
        return properties.get(key);
    }

    @Override
    public String getLicenceFilePath(){
        return licenceFilePath;
    }

    @Override
    public String getFolderPath(){
        return folderPath;
    }
}