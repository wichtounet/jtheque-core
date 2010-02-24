package org.jtheque.core.managers.core.application;

import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.utils.bean.Version;

import java.util.Set;

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
     * Return the image type of the window icon.
     *
     * @return The image type of the window icon.
     */
    ImageType getWindowIconType();

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

    boolean isModuleDiscovery();

    Set<String> getModules();
}