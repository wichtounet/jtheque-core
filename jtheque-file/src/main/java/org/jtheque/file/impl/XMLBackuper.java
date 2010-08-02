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

import org.jtheque.core.able.Core;
import org.jtheque.file.able.FileService.XmlBackupVersion;
import org.jtheque.file.able.ModuleBackup;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.bean.IntDate;
import org.jtheque.xml.utils.XMLWriter;
import org.jtheque.xml.utils.XML;

import org.w3c.dom.Node;

import java.io.File;

/**
 * A FileBackuper for the XML format.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
final class XMLBackuper {
    /**
     * Utility class, not instanciable.
     */
    private XMLBackuper() {
        super();
    }

    /**
     * Backup all the backups in the given file.
     *
     * @param file    The file to write to.
     * @param backups The backups to write.
     */
    public static void backup(File file, Iterable<ModuleBackup> backups) {
        XMLWriter<Node> writer = XML.newJavaFactory().newWriter("jtheque-backup");

        writeHeader(writer);

        for (ModuleBackup backup : backups) {
            writeBackup(writer, backup);
        }

        synchronized (XMLBackuper.class){
            writer.write(file.getAbsolutePath());
        }
    }

    /**
     * Write the header in the Document.
     *
     * @param writer The writer in which add the header.
     */
    private static void writeHeader(XMLWriter<Node> writer) {
        writer.add("header");

        writer.addOnly("date", Integer.toString(IntDate.today().intValue()));
        writer.addOnly("file-version", Integer.toString(XmlBackupVersion.THIRD.ordinal()));
        writer.addOnly("jtheque-version", Core.VERSION.getVersion());

        writer.switchToParent();
    }

    /**
     * Write the backup.
     *
     * @param writer The write to use.
     * @param backup The backup to write.
     */
    private static void writeBackup(XMLWriter<Node> writer, ModuleBackup backup) {
        writer.add("backup");

        writer.addOnly("id", backup.getId());
        writer.addOnly("version", backup.getVersion().getVersion());

        writer.add("nodes");

        XML.newJavaFactory().newNodeSaver().writeNodes(writer, backup.getNodes());

        writer.switchToParent(); //Out from "nodes"

        writer.switchToParent(); //Out from "backup"
    }
}