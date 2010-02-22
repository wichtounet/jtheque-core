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
import org.jtheque.core.managers.file.able.ModuleBackup;
import org.jtheque.core.utils.file.XMLException;
import org.jtheque.core.utils.file.XMLReader;
import org.jtheque.utils.io.FileException;
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
     * @throws FileException When an error occurs during the restore process.
     *
     * @return
     */
    public static List<ModuleBackup> restore(File file) throws FileException {
        List<ModuleBackup> backups = new ArrayList<ModuleBackup>(10);

        XMLReader reader = new XMLReader();

        try {
            reader.openFile(file);

            if (reader.getRootElement() == null) {
                throw new FileException("File corrupted (no root element)");
            }

            int version = reader.readInt("./header/file-version", reader.getRootElement());

            if (version != XmlBackupVersion.THIRD.ordinal()) {
                throw new FileException("Unsupported version");
            }

            //Todo get all the backups
        } catch (XMLException e) {
            throw new FileException("File corrupted", e);
        } finally {
            FileUtils.close(reader);
        }

        return backups;
    }
}