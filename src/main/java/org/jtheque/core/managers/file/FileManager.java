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

import org.jtheque.core.managers.AbstractActivableManager;
import org.jtheque.core.managers.file.able.BackupReader;
import org.jtheque.core.managers.file.able.BackupWriter;
import org.jtheque.core.managers.file.able.Backuper;
import org.jtheque.core.managers.file.able.FileType;
import org.jtheque.core.managers.file.able.Restorer;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.FileException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

/**
 * A FileManager implementation.
 *
 * @author Baptiste Wicht
 */
public final class FileManager extends AbstractActivableManager implements IFileManager {
    private Backuper[] backupers;
    private Restorer[] restorers;

    private final Map<FileType, Collection<BackupWriter>> backupWriters;
    private final Map<FileType, Collection<BackupReader>> backupReaders;

    /**
     * Construct a new FileManager.
     */
    public FileManager() {
        super();

        backupWriters = new EnumMap<FileType, Collection<BackupWriter>>(FileType.class);
        backupReaders = new EnumMap<FileType, Collection<BackupReader>>(FileType.class);
    }

    @Override
    public void backup(FileType format, File file) throws FileException {
        Backuper backuper = getBackuperFor(format);

        if (backuper == null) {
            throw new IllegalArgumentException("No backuper can be found for this format");
        }

        backuper.backup(file, backupWriters.get(format));
    }

    @Override
    public void restore(FileType format, File file) throws FileException {
        Restorer restorer = getRestorerFor(format);

        if (restorer == null) {
            throw new IllegalArgumentException("No restorer can be found for this format");
        }

        restorer.restore(file, backupReaders.get(format));
    }

    @Override
    public void registerBackupWriter(FileType format, BackupWriter writer) {
        Collection<BackupWriter> list = backupWriters.get(format);

        if (list == null) {
            list = new ArrayList<BackupWriter>(10);
            backupWriters.put(format, list);
        }

        list.add(writer);
    }

    @Override
    public String formatUTFToWrite(String utf) {
        return StringUtils.isEmpty(utf) ? IFileManager.UTF_NULL : utf;
    }

    @Override
    public String formatUTFToRead(String utf) {
        return IFileManager.UTF_NULL.equals(utf) ? "" : utf;
    }

    @Override
    public void registerBackupReader(FileType format, BackupReader reader) {
        Collection<BackupReader> list = backupReaders.get(format);

        if (list == null) {
            list = new ArrayList<BackupReader>(10);
            backupReaders.put(format, list);
        }

        list.add(reader);
    }

    @Override
    public void unregisterBackupReader(FileType format, BackupReader reader) {
        backupReaders.get(format).remove(reader);
    }

    @Override
    public void unregisterBackupWriter(FileType format, BackupWriter writer) {
        backupWriters.get(format).remove(writer);
    }

    @Override
    public Collection<BackupReader> getBackupReaders(FileType format) {
        return backupReaders.get(format);
    }

    @Override
    public boolean isBackupPossible(FileType format) {
        return backupReaders.get(format) != null;
    }

    @Override
    public boolean isRestorePossible(FileType format) {
        return backupWriters.get(format) != null;
    }

    /**
     * Set the backupers. This is not for use, this is only for Spring Injection.
     *
     * @param backupers The backupers to set.
     */
    public void setBackupers(Backuper[] backupers) {
        this.backupers = Arrays.copyOf(backupers, backupers.length);
    }

    /**
     * Set the restorers.  This is not for use, this is only for Spring Injection.
     *
     * @param restorers The restorers to set.
     */
    public void setRestorers(Restorer[] restorers) {
        this.restorers = Arrays.copyOf(restorers, restorers.length);
    }

    /**
     * Return the Restorer for the format.
     *
     * @param format The format to search.
     * @return The Restorer.
     */
    private Restorer getRestorerFor(FileType format) {
        for (Restorer b : restorers) {
            if (b.canImportFrom(format)) {
                return b;
            }
        }

        return null;
    }

    /**
     * Return the Backuper for the format.
     *
     * @param format The format to search.
     * @return The Backuper.
     */
    private Backuper getBackuperFor(FileType format) {
        for (Backuper b : backupers) {
            if (b.canExportTo(format)) {
                return b;
            }
        }

        return null;
    }
}