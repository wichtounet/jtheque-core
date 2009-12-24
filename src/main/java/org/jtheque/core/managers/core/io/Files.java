package org.jtheque.core.managers.core.io;

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

import org.jtheque.core.managers.Managers;

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
        return Managers.getCore().getFolders().getApplicationFolder() + File.separator + "JTheque-Launcher.jar";
    }

    @Override
    public File getLogsFile() {
        return new File(Managers.getCore().getFolders().getLogsFolder(), "/jtheque.log");
    }

    @Override
    public String getVersionsFileURL() {
        return "http://jtheque.developpez.com/public/versions/Core.versions";
    }

    @Override
    public String getOnlineHelpURL() {
        return "http://jtheque.developpez.com/";
    }

    @Override
    public String getForumURL() {
        return "http://www.developpez.net/forums/f751/applications/projets/projets-heberges/jtheque/";
    }

    @Override
    public String getMakeDonationURL() {
        return "https://sourceforge.net/project/project_donations.php?group_id=178515";
    }
}