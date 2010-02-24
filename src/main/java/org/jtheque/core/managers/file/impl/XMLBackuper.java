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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.file.IFileManager.XmlBackupVersion;
import org.jtheque.core.managers.file.able.ModuleBackup;
import org.jtheque.core.utils.file.XMLWriter;
import org.jtheque.core.utils.file.nodes.NodeSaver;
import org.jtheque.utils.bean.IntDate;
import org.jtheque.utils.io.FileException;

import java.io.File;

/**
 * A FileBackuper for the XML format.
 *
 * @author Baptiste Wicht
 */
public final class XMLBackuper {
    private XMLBackuper() {
        super();
    }

    public static void backup(File file, Iterable<ModuleBackup> backups){
        XMLWriter writer = new XMLWriter("jtheque-backup");

        writeHeader(writer);

        for (ModuleBackup backup : backups) {
            writeBackup(writer, backup);
        }

        writer.write(file.getAbsolutePath());
    }

    /**
     * Write the header in the Document.
     *
     * @param writer The writer in which add the header.
     */
    private static void writeHeader(XMLWriter writer) {
        writer.add("header");

        writer.addOnly("date", Integer.toString(IntDate.today().intValue()));
        writer.addOnly("file-version", Integer.toString(XmlBackupVersion.THIRD.ordinal()));
        writer.addOnly("jtheque-version", Managers.getCore().getApplication().getVersion().getVersion());

        writer.switchToParent();
    }

    private static void writeBackup(XMLWriter writer, ModuleBackup backup) {
        writer.add("backup");

        writer.addOnly("id", backup.getId());
        writer.addOnly("version", backup.getVersion().getVersion());

        writer.add("nodes");

        NodeSaver.writeNodes(writer, backup.getNodes());

        writer.switchToParent(); //Out from "nodes"

        writer.switchToParent(); //Out from "backup"
    }
}