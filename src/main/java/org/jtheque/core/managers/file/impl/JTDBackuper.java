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
import org.jtheque.core.managers.file.IFileManager.JTDVersion;
import org.jtheque.core.managers.file.able.BackupWriter;
import org.jtheque.core.managers.file.able.Backuper;
import org.jtheque.core.managers.file.able.BasicDataSource;
import org.jtheque.core.managers.file.able.FileType;
import org.jtheque.core.utils.file.jt.JTFileWriter;
import org.jtheque.core.utils.file.jt.impl.JTDFileWriter;
import org.jtheque.utils.io.FileException;

import java.io.File;
import java.util.Collection;

/**
 * A Backuper for the JTD format.
 *
 * @author Baptiste Wicht
 */
public final class JTDBackuper implements Backuper {
    @Override
    public void backup(File file, Collection<BackupWriter> writers) throws FileException {
        JTFileWriter writer = new JTDFileWriter(writers);

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setFileVersion(JTDVersion.FIRST.ordinal());
        dataSource.setVersion(Managers.getCore().getApplication().getVersion());

        writer.writeFile(file, dataSource);
    }

    @Override
    public boolean canExportTo(FileType fileType) {
        return fileType == FileType.JTD;
    }
}