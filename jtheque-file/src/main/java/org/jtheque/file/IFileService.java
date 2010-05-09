package org.jtheque.file;

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

import org.jtheque.file.able.ModuleBackuper;

import org.jtheque.io.XMLException;

import java.io.File;

/**
 * A FileService specification.
 *
 * @author Baptiste Wicht
 */
public interface IFileService {
    /**
     * XML Backup versions.
     *
     * @author Baptiste Wicht
     */
    enum XmlBackupVersion {
        THIRD   //JTheque Core 2.1
    }

    /**
     * Backup to a File.
     *
     * @param file   The file to backup to.
     */
    void backup(File file);

    /**
     * Restore the data from a File.
     *
     * @param file   The file to restore from.
     *
     * @throws XMLException If there is a problem reading the backup.
     */
    void restore(File file) throws XMLException;

    void registerBackuper(String moduleId, ModuleBackuper backuper);
}