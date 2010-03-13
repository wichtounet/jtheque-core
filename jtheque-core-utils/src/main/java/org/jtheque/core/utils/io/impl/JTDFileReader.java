package org.jtheque.core.utils.io.impl;

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

import org.jtheque.core.utils.io.AbstractJTFileHeader;
import org.jtheque.core.utils.io.JTFileReader;
import org.jtheque.core.utils.io.able.JTFile;
import org.jtheque.core.utils.io.able.JTNotZippedFile;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * A JTD File's header.
 *
 * @author Baptiste Wicht
 */
public final class JTDFileReader extends JTFileReader {
    @Override
    public JTFile readFile(DataInputStream stream) throws FileException {
        JTNotZippedFile file = new JTDFile();

        try {
            AbstractJTFileHeader header = file.getHeader();

            String key = stream.readUTF();
            String version = stream.readUTF();

            header.setKey("NULL".equals(key) ? "" : key);
            header.setVersionJTheque(new Version("NULL".equals(version) ? "" : version));
            header.setFileVersion(stream.readInt());
            header.setDate(stream.readInt());
            
            if (stream.readLong() != JTFile.JT_CATEGORY_SEPARATOR) {
                file.setCorrectSeparators(false);
            }
        } catch (IOException e) {
            throw new FileException("File corrupted", e);
        } finally {
            FileUtils.close(stream);
        }

        return file;
    }
}