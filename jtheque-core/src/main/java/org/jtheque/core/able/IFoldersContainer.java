package org.jtheque.core.able;

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
     * Returns the logs folder. It seems the folder where the logs are located.
     *
     * @return the File object who denotes the logs folder.
     */
    File getLogsFolder();

    /**
     * Return the libraries folder. It seems the folder where the libraries are located. This folder
     * is only for the librairies of the modules. 
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
