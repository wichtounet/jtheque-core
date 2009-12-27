package org.jtheque.core.managers.core.application;

import org.jtheque.core.managers.resource.ImageDescriptor;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;

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
    private Version version;

    private ApplicationProperties applicationProperties;

    private ImageDescriptor icon;
    private ImageDescriptor logo;

    private boolean displayLicence;

    private String[] supportedLanguages = {"fr", "en"};

    private final Map<String, String> properties = new HashMap<String, String>(5);

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
        return getProperty("application.repository");
    }

    @Override
    public String getMessageFileURL(){
        return getProperty("application.messages");
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
        return getProperty("application.licence");
    }

    @Override
    public String getFolderPath(){
        return getProperty("application.folder.path");
    }

    //Package protected methods to fill the application

    /**
     * Set the property value.
     *
     * @param name The name of the property.
     * @param value The value of the property.
     */
    void setProperty(String name, String value){
        properties.put(name, value);
    }

    /**
     * Set that the application must diplay licence.
     */
    void displayLicence(){
        displayLicence = true;
    }

    /**
     * Set the version of the application.
     *
     * @param version The version of the application.
     */
    void setVersion(Version version){
        this.version = version;
    }

    /**
     * Set the supported languages of the application.
     *
     * @param supportedLanguages The supported languages of the application.
     */
    void setSupportedLanguages(String[] supportedLanguages){
        this.supportedLanguages = ArrayUtils.copyOf(supportedLanguages);
    }

    /**
     * Set the application internationalisation of the application.
     *
     * @param applicationProperties The application properties. 
     */
    void setApplicationProperties(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    /**
     * Set the images of the application. 
     *
     * @param logo The logo of the application.
     * @param icon The icon of the application. 
     */
    void setImages(ImageDescriptor logo, ImageDescriptor icon){
        this.logo = logo;
        this.icon = icon;
    }
}