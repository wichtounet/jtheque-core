package org.jtheque.core.able.application;

import org.jtheque.core.utils.ImageType;
import org.jtheque.utils.bean.Version;

import java.util.Set;

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
 * A JTheque Core application specification.
 *
 * @author Baptiste Wicht
 */
public interface Application {
    /**
     * Return the path to the logo.
     *
     * @return The path to the logo.
     */
    String getLogo();

    /**
     * Return the image type of the logo.
     *
     * @return The image type of the logo.
     */
    ImageType getLogoType();

    /**
     * Return the path to the window icon.
     *
     * @return The path to the window icon.
     */
    String getWindowIcon();

    /**
     * Return the path to the licence.
     *
     * @return The path to the licence of the application.
     */
    String getLicenceFilePath();

    /**
     * Return the application version.
     *
     * @return The version of the application.
     */
    Version getVersion();

    /**
     * The path to the folder of the installation.
     *
     * @return The path to the application folder.
     */
    String getFolderPath();

    /**
     * Indicate if we must display the licence.
     *
     * @return true if we must display the licence else false.
     */
    boolean isDisplayLicence();

    /**
     * Return the url of the application repository.
     *
     * @return The URL to the application repository.
     */
    String getRepository();

    /**
     * Return the messages file url for the application.
     *
     * @return The URL to the messages files of the application.
     */
    String getMessageFileURL();

    /**
     * Return the name of the application.
     *
     * @return The name of the application.
     */
    String getName();

    /**
     * Return the author of the application.
     *
     * @return The author of the application.
     */
    String getAuthor();

    /**
     * Return the email of the author of the application.
     *
     * @return The email of the author of the application.
     */
    String getEmail();

    /**
     * Return the site of the application.
     *
     * @return The site of the application.
     */
    String getSite();

    /**
     * Return the copyright of the application.
     *
     * @return The copyright of the application.
     */
    String getCopyright();

    /**
     * Return all the supported languages of the application.
     *
     * @return An array containing the languages than the application support.
     */
    String[] getSupportedLanguages();

    /**
     * Return the application property.
     *
     * @param key The key of the property.
     *
     * @return The value of the property or empty string if there is no property with this key.
     */
    String getProperty(String key);

    /**
     * Indicate if the application allows module discovery.
     *
     * @return true if the application allows module discovery. 
     */
    boolean isModuleDiscovery();

    /**
     * Return the modules configured in the application.
     *
     * @return A Set containing all the modules configured in the application. 
     */
    Set<String> getModules();
}