package org.jtheque.core;

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

import org.jtheque.core.application.Application;
import org.jtheque.core.lifecycle.ILifeCycle;
import org.jtheque.utils.bean.Version;

import java.util.Collection;

/**
 * A core specification.
 *
 * @author Baptiste Wicht
 */
public interface ICore {
    String IMAGES_BASE_NAME = "org/jtheque/core/images";
    Version VERSION = new Version("2.1");

    String FORUM_URL = "http://www.developpez.net/forums/f751/applications/projets/projets-heberges/jtheque/";
    String VERSIONS_FILE_URL = "http://jtheque.developpez.com/public/versions/Core.versions";
    String HELP_URL = "http://jtheque.developpez.com/";

    /**
     * Launch the JTheque Core.
     *
     * @param application The application to launch.
     */
    void setApplication(Application application);

    /**
     * Return the messages file url for the core.
     *
     * @return The URL to the messages files of the core.
     */
    String getCoreMessageFileURL();

    /**
     * Return the folders of the application.
     *
     * @return The folders of the application.
     */
    IFoldersContainer getFolders();

    /**
     * Return the files of the application.
     *
     * @return The files of the application.
     */
    IFilesContainer getFiles();

    /**
     * Add an internationalized credits message.
     *
     * @param key The internationalized key.
     */
    void addCreditsMessage(String key);

    /**
     * Return all the credits message.
     *
     * @return A List containing all the credits message.
     */
    Collection<String> getCreditsMessage();

    /**
     * Indicate if the current version of the core is compatible with an other version.
     *
     * @param version The version to test the compatibility with.
     * @return true if the current version is compatible with the other version.
     */
    boolean isNotCompatibleWith(Version version);

    /**
     * Return the current application.
     *
     * @return The current application.
     */
    Application getApplication();

    /**
     * Return the configuration of the core.
     *
     * @return The configuration of the core.
     */
    CoreConfiguration getConfiguration();

    ILifeCycle getLifeCycle();

    /**
     * Return all the supported languages.
     *
     * @return A List containing all the supported languages.
     */
    Collection<String> getPossibleLanguages();
}