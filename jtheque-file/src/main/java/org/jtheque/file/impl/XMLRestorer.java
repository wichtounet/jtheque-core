package org.jtheque.file.impl;

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

import org.jtheque.file.able.IFileService.XmlBackupVersion;
import org.jtheque.file.able.ModuleBackup;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.XMLException;
import org.jtheque.xml.utils.javax.NodeLoader;
import org.jtheque.xml.utils.javax.XMLReader;

import org.w3c.dom.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A Restorer for the XML format.
 *
 * @author Baptiste Wicht
 */
public final class XMLRestorer {
    /**
     * Utility class, not instanciable.
     */
    private XMLRestorer() {
        super();
    }

    /**
     * Import all the data from the file.
     *
     * @param file The file.
     * @return All the module backups.
     * @throws XMLException When an error occurs during the restore process.
     */
    public static List<ModuleBackup> restore(File file) throws XMLException {
        List<ModuleBackup> backups = new ArrayList<ModuleBackup>(10);

        XMLReader reader = new XMLReader();

        try {
            reader.openFile(file);

            int version = reader.readInt("./header/file-version", reader.getRootElement());

            if (version != XmlBackupVersion.THIRD.ordinal()) {
                throw new XMLException("Unsupported version");
            }

            for (Node backupElement : reader.getNodes("//backup", reader.getRootElement())) {
                ModuleBackup backup = new ModuleBackup();

                backup.setId(reader.readString("id", backupElement));
                backup.setVersion(new Version(reader.readString("version", backupElement)));

                backup.setNodes(NodeLoader.resolveNodeStates(reader.getNodes("nodes/*", backupElement)));
            }
        } finally {
            FileUtils.close(reader);
        }

        return backups;
    }
}