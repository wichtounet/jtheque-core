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

import org.jtheque.core.managers.file.IFileManager;
import org.jtheque.core.managers.file.able.BasicDataSource;
import org.jtheque.core.utils.file.jt.JTFileWriter;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.IntDate;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A writer implementation for JTD File.
 *
 * @author Baptiste Wicht
 */
public final class JTDFileWriter extends JTFileWriter {
    @Override
    public void writeFile(DataOutputStream stream, BasicDataSource source) throws FileException {
        try {
            String key = FileUtils.encryptKey(IFileManager.JT_KEY);
            String version = source.getVersion().getVersion();

            stream.writeUTF(StringUtils.isEmpty(key) ? "NULL" : key);
            stream.writeUTF(StringUtils.isEmpty(version) ? "NULL" : version);
            stream.writeInt(source.getFileVersion());
            stream.writeInt(IntDate.today().intValue());

            stream.writeLong(IFileManager.JT_CATEGORY_SEPARATOR);
        } catch (IOException e) {
            throw new FileException(e);
        } finally {
            FileUtils.close(stream);
        }
    }
}