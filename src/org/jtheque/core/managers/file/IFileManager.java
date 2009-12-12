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
import org.jtheque.core.managers.file.able.BackupReader;
import org.jtheque.core.managers.file.able.BackupWriter;
import org.jtheque.core.managers.file.able.FileType;
import org.jtheque.utils.io.FileException;

import java.io.File;
import java.util.Collection;

/**
 * A FileManager specification.
 *
 * @author Baptiste Wicht
 */
public interface IFileManager extends ActivableManager {
    long JT_CATEGORY_SEPARATOR = -89569876428459544L;
    long JT_INTERN_SEPARATOR = -95684111123647897L;

    int CONTENT = 5;
    int NO_CONTENT = 10;

    String UTF_NULL = "NULL";

    String JT_KEY = "1W@JTHEQUE@W1";

    /**
     * XML Backup versions.
     *
     * @author Baptiste Wicht
     */
    enum XmlBackupVersion {
        FIRST,
        SECOND //JTheque Core 2.0
    }

    /**
     * JTD File versions.
     *
     * @author Baptiste Wicht
     */
    enum JTDVersion {
        FIRST
    }

    /**
     * Format an UTF string before insert it in a file to be sure that it isn't empty.
     *
     * @param utf The UTF string.
     * @return The UTF String if it isn't empty or NULL.
     */
    String formatUTFToWrite(String utf);

    /**
     * Format an UTF string after read it in a file to get the good value.
     *
     * @param utf The UTF string.
     * @return The UTF String if it isn't empty or NULL.
     */
    String formatUTFToRead(String utf);

    /**
     * Backup to a File with a specific format.
     *
     * @param format The format of the backup.
     * @param file   The file to backup to.
     * @throws FileException Thrown when an error occurs during the backup process.
     */
    void backup(FileType format, File file) throws FileException;

    /**
     * Restore the data from a File with a specific format.
     *
     * @param format The format of the backup.
     * @param file   The file to restore from.
     * @throws FileException Thrown when an error occurs during the restore process.
     */
    void restore(FileType format, File file) throws FileException;

    /**
     * Register a new BackupWriter.
     *
     * @param format The format the BackupWriter manage.
     * @param writer The writer to register.
     */
    void registerBackupWriter(FileType format, BackupWriter writer);

    /**
     * Register a new BackupReader.
     *
     * @param format The format the BackupReader manage.
     * @param reader The reader to register.
     */
    void registerBackupReader(FileType format, BackupReader reader);

    /**
     * Unregister a BackupWriter.
     *
     * @param format The format the BackupWriter manage.
     * @param writer The writer to unregister.
     */
    void unregisterBackupWriter(FileType format, BackupWriter writer);

    /**
     * Unregister a BackupReader.
     *
     * @param format The format the BackupReader manage.
     * @param reader The reader to unregister.
     */
    void unregisterBackupReader(FileType format, BackupReader reader);

    /**
     * Return all the backupers of a specific format.
     *
     * @param format The format.
     * @return All the BackupReader.
     */
    Collection<BackupReader> getBackupReaders(FileType format);

    /**
     * Indicate if the backup is possible for the specified file type.
     *
     * @param type The file type to test.
     * @return true if the backup is possible with this file type else false.
     */
    boolean isBackupPossible(FileType type);

    /**
     * Indicate if the restore is possible for the specified file type.
     *
     * @param type The file type to test.
     * @return true if the restore is possible with this file type else false.
     */
    boolean isRestorePossible(FileType type);
}