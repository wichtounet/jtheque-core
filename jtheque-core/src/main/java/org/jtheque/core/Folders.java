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
    private File skinsFolder;
    private File logsFolder;
    private File cacheFolder;

    @Override
    public File getApplicationFolder() {
        if (applicationFolder == null) {
            applicationFolder = new File(CoreServices.get(ICore.class).getApplication().getFolderPath());

            FileUtils.createIfNotExists(applicationFolder);
        }

        return applicationFolder;
    }

    @Override
    public File getSkinsFolder() {
        if (skinsFolder == null) {
            skinsFolder = new File(getApplicationFolder(), "/skins");

            FileUtils.createIfNotExists(skinsFolder);
        }

        return skinsFolder;
    }

    @Override
    public File getLogsFolder() {
        if (logsFolder == null) {
            logsFolder = new File(getApplicationFolder(), "/logs");

            FileUtils.createIfNotExists(logsFolder);
        }

        return logsFolder;
    }

    @Override
    public File getLibrariesFolder() {
        if (librariesFolder == null) {
            librariesFolder = new File(getApplicationFolder(), "/lib");

            FileUtils.createIfNotExists(librariesFolder);
        }

        return librariesFolder;
    }

    @Override
    public File getModulesFolder() {
        if (modulesFolder == null) {
            modulesFolder = new File(getApplicationFolder(), "/modules");

            FileUtils.createIfNotExists(modulesFolder);
        }

        return modulesFolder;
    }

    @Override
    public File getCacheFolder() {
        if (cacheFolder == null) {
            cacheFolder = new File(getApplicationFolder(), "/cache");

            FileUtils.createIfNotExists(cacheFolder);
        }

        return cacheFolder;
    }

    @Override
    public String getTempFolderPath() {
        return SystemProperty.JAVA_IO_TMP_DIR.get();
    }
}