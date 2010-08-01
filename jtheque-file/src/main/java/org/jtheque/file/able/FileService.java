package org.jtheque.file.able;

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

import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.io.FileException;
import org.jtheque.xml.utils.XMLException;

import java.io.File;
import java.util.Collection;

/**
 * A FileService specification.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public interface FileService {
    /**
     * XML Backup versions.
     *
     * @author Baptiste Wicht
     */
    enum XmlBackupVersion {
        THIRD   //JTheque Core 2.1
    }

    /**
     * Register a new exporter for the module.
     *
     * @param module   The module id.
     * @param exporter The exporter to register.
     */
    void registerExporter(String module, Exporter<?> exporter);

    /**
     * Register a new importer for the module.
     *
     * @param module   The module id.
     * @param importer The importer to register.
     */
    void registerImporter(String module, Importer importer);

    /**
     * Export datas to the given file.
     *
     * @param module   The module id.
     * @param fileType The type of file.
     * @param file     The file to export to.
     * @param datas    The datas to export.
     * @param <T>      The type of datas to export.
     *
     * @throws FileException If an exception occurs during the export using the Exporter<T>
     */
    <T> void exportDatas(String module, String fileType, String file, Collection<T> datas) throws FileException;

    /**
     * Import datas from the given file.
     *
     * @param module   The module id.
     * @param fileType The type of file.
     * @param file     The file to import from.
     *
     * @throws FileException If an exception occurs during the export using the Importer
     */
    void importDatas(String module, String fileType, String file) throws FileException;

    /**
     * Backup to a File.
     *
     * @param file The file to backup to.
     */
    void backup(File file);

    /**
     * Restore the data from a File.
     *
     * @param file The file to restore from.
     *
     * @throws XMLException If there is a problem reading the backup.
     */
    void restore(File file) throws XMLException;

    /**
     * Register a backuper.
     *
     * @param moduleId The id of the module that register this backuper. This id is used to automatically dispose the
     *                 backuper if the module is stopped. If the module id is null or empty, the backuper will never be
     *                 released.
     * @param backuper The backuper to register.
     */
    void registerBackuper(String moduleId, ModuleBackuper backuper);
}