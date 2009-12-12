package org.jtheque.core.managers.file.able;

import org.jtheque.utils.io.FileException;

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
 * A Backup Reader. It seems an object who read a backup file and persist the data from the file.
 *
 * @author Baptiste Wicht
 */
public interface BackupReader {
    /**
     * Read the backup.
     *
     * @param object The backup object.
     * @throws FileException When an error occurs during the read process.
     */
    void readBackup(Object object) throws FileException;

    /**
     * Persist the data of the backup.
     */
    void persistTheData();
}