package org.jtheque.core.managers.update.actions;

import org.jtheque.core.managers.Managers;
import org.jtheque.utils.StringUtils;

import java.io.File;

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
 * @author Baptiste Wicht
 */
public abstract class AbstractUpdateAction implements UpdateAction {
    private String file;
    private String folder;

    /**
     * Set the name of the file.
     *
     * @param file The name of the file.
     */
    public final void setFile(String file) {
        this.file = file;
    }

    /**
     * Set the name of the folder.
     *
     * @param folder The name of folder.
     */
    public final void setFolder(String folder) {
        this.folder = folder;
    }

    /**
     * Return the complete path to the destination file.
     *
     * @return The complete path.
     */
    final String getDestination() {
        return buildFilePath(folder, file);
    }

    /**
     * Return the name of the file.
     *
     * @return The name of the file. 
     */
    public String getFile() {
        return file;
    }

    /**
     * Build the file path with the specified folder and file.
     *
     * @param folder The folder of the file path.
     * @param file   The file part of the path.
     * @return The file path.
     */
    static String buildFilePath(String folder, String file) {
        StringBuilder builder = new StringBuilder(200);

        builder.append(Managers.getCore().getFolders().getApplicationFolder().getAbsolutePath());

        if (!StringUtils.isEmpty(folder)) {
            builder.append(File.separator);
            builder.append(folder);
        }

        builder.append(File.separator);
        builder.append(file);

        return builder.toString();
    }
}
