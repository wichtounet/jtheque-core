package org.jtheque.file.impl;

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

import org.jdom.Element;
import org.jtheque.file.IFileManager.XmlBackupVersion;
import org.jtheque.file.able.ModuleBackup;
import org.jtheque.io.NodeLoader;
import org.jtheque.io.XMLException;
import org.jtheque.io.XMLReader;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A Restorer for the XML format.
 *
 * @author Baptiste Wicht
 */
public final class XMLRestorer {
    private XMLRestorer() {
        super();
    }

    /**
     * Import all the data from the file.
     *
     * @param file    The file.
     * @throws XMLException When an error occurs during the restore process.
     *
     * @return All the module backups. 
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

            for(Element backupElement : reader.getNodes("//backup", reader.getRootElement())){
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