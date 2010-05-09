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

import org.jtheque.core.utils.io.BasicDataSource;
import org.jtheque.core.utils.io.JTFileWriter;
import org.jtheque.core.utils.io.able.JTFile;
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
            String key = FileUtils.encryptKey(JTFile.JT_KEY);
            String version = source.getVersion().getVersion();

            stream.writeUTF(StringUtils.isEmpty(key) ? "NULL" : key);
            stream.writeUTF(StringUtils.isEmpty(version) ? "NULL" : version);
            stream.writeInt(source.getFileVersion());
            stream.writeInt(IntDate.today().intValue());

            stream.writeLong(JTFile.JT_CATEGORY_SEPARATOR);
        } catch (IOException e) {
            throw new FileException(e);
        } finally {
            FileUtils.close(stream);
        }
    }
}