package org.jtheque.lifecycle.application;

import org.jtheque.core.able.application.Application;
import org.jtheque.core.able.application.ApplicationProperties;
import org.jtheque.core.utils.ImageDescriptor;
import org.jtheque.core.utils.ImageType;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;

import java.util.HashMap;
import java.util.Map;

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

/**
 * An XML Application. It seems a JTheque Core Application who's read from an XML file.
 *
 * @author Baptiste Wicht
 */
public final class XMLApplication implements Application {
    private Version version;

    private ApplicationProperties applicationProperties;

    private String icon;
    private ImageDescriptor logo;

    private boolean displayLicense;

    private String[] supportedLanguages = {"fr", "en"};

    private final Map<String, String> properties = new HashMap<String, String>(5);

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public String getAuthor() {
        return applicationProperties.getAuthor();
    }

    @Override
    public String getName() {
        return applicationProperties.getName();
    }

    @Override
    public String getSite() {
        return applicationProperties.getSite();
    }

    @Override
    public String getEmail() {
        return applicationProperties.getEmail();
    }

    @Override
    public String getCopyright() {
        return applicationProperties.getCopyright();
    }

    @Override
    public String getLogo() {
        return logo.getImage();
    }

    @Override
    public ImageType getLogoType() {
        return logo.getType();
    }

    @Override
    public String getWindowIcon() {
        return icon;
    }

    @Override
    public boolean isDisplayLicense() {
        return displayLicense;
    }

    @Override
    public String getRepository() {
        return getProperty("application.repository");
    }

    @Override
    public String getMessageFileURL() {
        return getProperty("application.messages");
    }

    @Override
    public String[] getSupportedLanguages() {
        return ArrayUtils.copyOf(supportedLanguages);
    }

    @Override
    public String getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public String getLicenseFilePath() {
        return getProperty("application.license");
    }

    @Override
    public String getFolderPath() {
        return getProperty("application.folder.path");
    }

    //Package protected methods to fill the application

    /**
     * Set the property value.
     *
     * @param name  The name of the property.
     * @param value The value of the property.
     */
    void setProperty(String name, String value) {
        properties.put(name, value);
    }

    /**
     * Set that the application must display license.
     */
    void displayLicense() {
        displayLicense = true;
    }

    /**
     * Set the version of the application.
     *
     * @param version The version of the application.
     */
    void setVersion(Version version) {
        this.version = version;
    }

    /**
     * Set the supported languages of the application.
     *
     * @param supportedLanguages The supported languages of the application.
     */
    void setSupportedLanguages(String[] supportedLanguages) {
        this.supportedLanguages = ArrayUtils.copyOf(supportedLanguages);
    }

    /**
     * Set the application internationalisation of the application.
     *
     * @param applicationProperties The application properties.
     */
    void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    /**
     * Set the images of the application.
     *
     * @param logo The logo of the application.
     * @param icon The icon of the application.
     */
    void setImages(ImageDescriptor logo, String icon) {
        this.logo = logo;
        this.icon = icon;
    }
}