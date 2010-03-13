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
 * Give access to the files of the application.
 *
 * @author Baptiste Wicht
 */
public final class Files implements IFilesContainer {
    @Override
    public File getLauncherFile() {
        return new File(getLauncherPath());
    }

    /**
     * Return the path to the launcher.
     *
     * @return The path to the launcher.
     */
    private static String getLauncherPath() {
        return CoreServices.get(ICore.class).getFolders().getApplicationFolder() + File.separator + "JTheque-Launcher.jar";
    }

    @Override
    public File getLogsFile() {
        return new File(CoreServices.get(ICore.class).getFolders().getLogsFolder(), "/jtheque.log");
    }
}