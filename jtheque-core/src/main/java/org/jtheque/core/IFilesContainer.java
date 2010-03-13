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
public interface IFilesContainer {
    /**
     * Return the launcher file of the application.
     *
     * @return The launcher file.
     */
    File getLauncherFile();

    /**
     * Return the log file of the application.
     *
     * @return The log file.
     */
    File getLogsFile();
}
