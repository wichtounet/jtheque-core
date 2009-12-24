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

import org.jtheque.core.managers.file.IFileManager.XmlBackupVersion;
import org.jtheque.core.managers.file.able.BackupReader;
import org.jtheque.core.managers.file.able.FileType;
import org.jtheque.core.managers.file.able.Restorer;
import org.jtheque.core.utils.file.XMLException;
import org.jtheque.core.utils.file.XMLReader;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;

import java.io.File;
import java.util.Collection;

/**
 * A Restorer for the XML format.
 *
 * @author Baptiste Wicht
 */
public final class XMLRestorer implements Restorer {
    @Override
    public void restore(File file, Collection<BackupReader> readers) throws FileException {
        XMLReader reader = new XMLReader();

        try {
            reader.openFile(file);

            if (reader.getRootElement() == null) {
                throw new FileException("File corrupted (no root element)");
            }

            int version = reader.readInt("./header/file-version", reader.getRootElement());

            if (version != XmlBackupVersion.FIRST.ordinal() && version != XmlBackupVersion.SECOND.ordinal()) {
                throw new FileException("Unsupported version");
            }

            for (BackupReader r : readers) {
                r.readBackup(reader.getRootElement());
            }
        } catch (XMLException e) {
            throw new FileException("File corrupted", e);
        } finally {
            FileUtils.close(reader);
        }
    }

    @Override
    public boolean canImportFrom(FileType fileType) {
        return fileType == FileType.XML;
    }
}