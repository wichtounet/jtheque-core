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

import java.io.File;

/**
 * @author Baptiste Wicht
 */
public interface IFoldersContainer {
    /**
     * Return the application folder. It seems the root folder where the application is launched.
     *
     * @return The File object who denotes the application folder.
     */
    File getApplicationFolder();

    /**
     * Return the skins folder. It seems the folder where the skins are located.
     *
     * @return The File object who denotes the skins folder.
     */
    File getSkinsFolder();

    /**
     * Returns the logs folder. It seems the folder where the logs are located.
     *
     * @return the File object who denotes the logs folder.
     */
    File getLogsFolder();

    /**
     * Return the libraries folder. It seems the folder where the libraries are located.
     *
     * @return The File object who denotes the libraries folder.
     */
    File getLibrariesFolder();

    /**
     * Return the modules folder. It seems the folder where the modules are located.
     *
     * @return The File object who denotes the modules folder.
     */
    File getModulesFolder();

    /**
     * Return the cache folder. It seems the folder where the cache are located.
     *
     * @return The File object who denotes the cache folder.
     */
    File getCacheFolder();

    /**
     * Return the path to the temporary folder.
     *
     * @return The path to the temporary folder.
     */
    String getTempFolderPath();
}
