package org.jtheque.core.managers.file;

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

import org.jtheque.core.managers.ActivableManager;
import org.jtheque.core.managers.file.able.ModuleBackuper;
import org.jtheque.utils.io.FileException;

import java.io.File;

/**
 * A FileManager specification.
 *
 * @author Baptiste Wicht
 */
public interface IFileManager extends ActivableManager {
    long JT_CATEGORY_SEPARATOR = -89569876428459544L;

    String JT_KEY = "1W@JTHEQUE@W1";

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
     * @throws FileException Thrown when an error occurs during the backup process.
     */
    void backup(File file) throws FileException;

    /**
     * Restore the data from a File.
     *
     * @param file   The file to restore from.
     * @throws FileException Thrown when an error occurs during the restore process.
     */
    void restore(File file) throws FileException;

    void registerBackuper(ModuleBackuper backuper);
    void unregisterBackuper(ModuleBackuper backuper);
}