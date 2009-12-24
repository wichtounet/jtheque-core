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
import org.jtheque.core.managers.file.able.BackupWriter;
import org.jtheque.core.managers.file.able.BasicDataSource;
import org.jtheque.core.utils.file.jt.JTFileWriter;
import org.jtheque.utils.bean.IntDate;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * A writer implementation for JTD File.
 *
 * @author Baptiste Wicht
 */
public final class JTDFileWriter extends JTFileWriter {
    private final Collection<BackupWriter> writers;

    /**
     * Construct a new JTDFileWriter.
     *
     * @param writers The writers.
     */
    public JTDFileWriter(Collection<BackupWriter> writers) {
        this.writers = CollectionUtils.copyOf(writers);
    }

    @Override
    public void writeFile(DataOutputStream stream, BasicDataSource source) throws FileException {
        try {
            //Header
            stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(FileUtils.encryptKey(IFileManager.JT_KEY)));
            stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(source.getVersion().getVersion()));
            stream.writeInt(source.getFileVersion());
            stream.writeInt(IntDate.today().intValue());

            stream.writeLong(IFileManager.JT_CATEGORY_SEPARATOR);

            for (BackupWriter writer : writers) {
                writer.write(stream);
            }
        } catch (IOException e) {
            throw new FileException(e);
        } finally {
            FileUtils.close(stream);
        }
    }
}