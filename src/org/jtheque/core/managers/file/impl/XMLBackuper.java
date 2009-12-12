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
import org.jtheque.core.managers.file.able.BackupWriter;
import org.jtheque.core.managers.file.able.Backuper;
import org.jtheque.core.managers.file.able.FileType;
import org.jtheque.core.utils.file.XMLWriter;
import org.jtheque.utils.bean.IntDate;
import org.jtheque.utils.io.FileException;

import java.io.File;
import java.util.Collection;

/**
 * A Backuper for the XML format.
 *
 * @author Baptiste Wicht
 */
public final class XMLBackuper implements Backuper {
    @Override
    public void backup(File file, Collection<BackupWriter> writers) throws FileException {
        XMLWriter writer = new XMLWriter();

        writeHeader(writer);

        for (BackupWriter backupWriter : writers) {
            backupWriter.write(writer);
        }

        writer.write(file.getAbsolutePath());
    }

    @Override
    public boolean canExportTo(FileType fileType) {
        return fileType == FileType.XML;
    }

    /**
     * Write the header in the Document.
     *
     * @param writer The writer in which add the header.
     */
    private static void writeHeader(XMLWriter writer) {
        writer.add("header");

        writer.addOnly("date", Integer.toString(IntDate.today().intValue()));
        writer.addOnly("file-version", Integer.toString(XmlBackupVersion.SECOND.ordinal()));
        writer.addOnly("jtheque-version", Managers.getCore().getApplication().getVersion().getVersion());

        writer.switchToParent();
    }
}