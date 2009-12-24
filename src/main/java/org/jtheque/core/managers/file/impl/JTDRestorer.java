package org.jtheque.core.managers.file.impl;

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

import org.jtheque.core.managers.file.IFileManager.JTDVersion;
import org.jtheque.core.managers.file.able.BackupReader;
import org.jtheque.core.managers.file.able.FileType;
import org.jtheque.core.managers.file.able.Restorer;
import org.jtheque.core.utils.file.jt.JTFileReader;
import org.jtheque.core.utils.file.jt.able.JTNotZippedFile;
import org.jtheque.core.utils.file.jt.impl.JTDFileReader;
import org.jtheque.utils.io.FileException;

import java.io.File;
import java.util.Collection;

/**
 * A Restorer for the JTD format.
 *
 * @author Baptiste Wicht
 */
public final class JTDRestorer implements Restorer {
    @Override
    public void restore(File file, Collection<BackupReader> readers) throws FileException {
        JTFileReader reader = new JTDFileReader(readers);

        JTNotZippedFile jtdFile = (JTNotZippedFile) reader.readFile(file);

        if (jtdFile.isValid()) {
            for (BackupReader r : readers) {
                r.persistTheData();
            }
        } else if (jtdFile.getHeader().getFileVersion() != JTDVersion.FIRST.ordinal()) {
            throw new FileException("Unsupported version");
        } else {
            throw new FileException("Invalid file");
        }
    }

    @Override
    public boolean canImportFrom(FileType fileType) {
        return fileType == FileType.JTD;
    }
}