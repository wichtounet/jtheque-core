package org.jtheque.core.managers.core;

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

import org.jtheque.core.managers.core.application.Application;
import org.jtheque.core.managers.core.io.IFilesContainer;
import org.jtheque.core.managers.core.io.IFoldersContainer;
import org.jtheque.core.managers.lifecycle.ILifeCycleManager;
import org.jtheque.utils.bean.Version;

import java.util.Collection;

/**
 * A core specification.
 *
 * @author Baptiste Wicht
 */
public interface ICore {
    String IMAGES_BASE_NAME = "org/jtheque/core/resources/images";

    /**
     * Return the version of the core.
     *
     * @return The version of the core.
     */
    Version getCoreCurrentVersion();

    /**
     * Launch the JTheque Core.
     *
     * @param application The application to launch.
     */
    void launchJThequeCore(Application application);

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
     * Return the manager of the life cycle.
     *
     * @return The manager of the life cycle.
     */
    ILifeCycleManager getLifeCycleManager();

    /**
     * Return the base name for the images.
     *
     * @return The base name for the images.
     */
    String getImagesBaseName();

    /**
     * Return the configuration of the core.
     *
     * @return The configuration of the core.
     */
    CoreConfiguration getConfiguration();
}
