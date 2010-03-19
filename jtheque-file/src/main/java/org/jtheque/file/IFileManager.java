package org.jtheque.file;

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

import org.jtheque.file.able.ModuleBackuper;

import org.jtheque.io.XMLException;

import java.io.File;

/**
 * A FileManager specification.
 *
 * @author Baptiste Wicht
 */
public interface IFileManager {
    /**
     * XML Backup versions.
     *
     * @author Baptiste Wicht
     */
    enum XmlBackupVersion {
        FIRST,
        SECOND, //JTheque Core 2.0
        THIRD   //JTheque Core 2.1
    }

    /**
     * Backup to a File.
     *
     * @param file   The file to backup to.
     */
    void backup(File file);

    /**
     * Restore the data from a File.
     *
     * @param file   The file to restore from.
     *
     * @throws XMLException If there is a problem reading the backup.
     */
    void restore(File file) throws XMLException;

    void registerBackuper(String moduleId, ModuleBackuper backuper);
}