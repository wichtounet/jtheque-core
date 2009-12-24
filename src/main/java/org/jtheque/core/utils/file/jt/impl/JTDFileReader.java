package org.jtheque.core.utils.file.jt.impl;

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
import org.jtheque.core.managers.file.IFileManager;
import org.jtheque.core.managers.file.able.BackupReader;
import org.jtheque.core.utils.file.jt.AbstractJTFileHeader;
import org.jtheque.core.utils.file.jt.JTFileReader;
import org.jtheque.core.utils.file.jt.able.JTFile;
import org.jtheque.core.utils.file.jt.able.JTNotZippedFile;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * A JTD File's header.
 *
 * @author Baptiste Wicht
 */
public final class JTDFileReader extends JTFileReader {
    private final Collection<BackupReader> readers;

    /**
     * Construct a new JTD file reader.
     *
     * @param readers The readers.
     */
    public JTDFileReader(Collection<BackupReader> readers) {
        this.readers = CollectionUtils.copyOf(readers);
    }

    @Override
    public JTFile readFile(DataInputStream stream) throws FileException {
        JTNotZippedFile file = new JTDFile();

        try {
            AbstractJTFileHeader header = file.getHeader();

            header.setKey(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF()));
            header.setVersionJTheque(new Version(Managers.getManager(IFileManager.class).formatUTFToRead(stream.readUTF())));
            header.setFileVersion(stream.readInt());
            header.setDate(stream.readInt());

            if (stream.readLong() != IFileManager.JT_CATEGORY_SEPARATOR) {
                file.setCorrectSeparators(false);
            }

            for (BackupReader reader : readers) {
                reader.readBackup(stream);
            }
        } catch (IOException e) {
            throw new FileException("File corrupted", e);
        } finally {
            FileUtils.close(stream);
        }

        return file;
    }
}