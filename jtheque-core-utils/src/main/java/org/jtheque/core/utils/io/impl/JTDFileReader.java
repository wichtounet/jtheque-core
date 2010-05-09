package org.jtheque.core.utils.io.impl;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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