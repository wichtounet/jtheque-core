package org.jtheque.core.impl;

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

import org.jtheque.core.able.ICore;
import org.jtheque.core.able.IFoldersContainer;
import org.jtheque.core.utils.SystemProperty;
import org.jtheque.utils.io.FileUtils;

import java.io.File;

/**
 * Give access to the folders of the application.
 *
 * @author Baptiste Wicht
 */
public final class Folders implements IFoldersContainer {
    private File applicationFolder;
    private File librariesFolder;
    private File modulesFolder;
    private File logsFolder;
    private File cacheFolder;

    private final ICore core;

    /**
     * Construct a new Folders.
     *
     * @param core The core.
     */
    public Folders(ICore core) {
        super();

        this.core = core;
    }

    @Override
    public File getApplicationFolder() {
        if (applicationFolder == null) {
            applicationFolder = new File(core.getApplication().getFolderPath());

            FileUtils.createIfNotExists(applicationFolder);
        }

        return applicationFolder;
    }

    @Override
    public File getLogsFolder() {
        if (logsFolder == null) {
            logsFolder = new File(getApplicationFolder(), "logs");

            FileUtils.createIfNotExists(logsFolder);
        }

        return logsFolder;
    }

    @Override
    public File getLibrariesFolder() {
        if (librariesFolder == null) {
            librariesFolder = new File(getApplicationFolder(), "lib");

            FileUtils.createIfNotExists(librariesFolder);
        }

        return librariesFolder;
    }

    @Override
    public File getModulesFolder() {
        if (modulesFolder == null) {
            modulesFolder = new File(getApplicationFolder(), "modules");

            FileUtils.createIfNotExists(modulesFolder);
        }

        return modulesFolder;
    }

    @Override
    public File getCacheFolder() {
        if (cacheFolder == null) {
            cacheFolder = new File(getApplicationFolder(), "cache");

            FileUtils.createIfNotExists(cacheFolder);
        }

        return cacheFolder;
    }

    @Override
    public String getTempFolderPath() {
        return SystemProperty.JAVA_IO_TMP_DIR.get();
    }
}