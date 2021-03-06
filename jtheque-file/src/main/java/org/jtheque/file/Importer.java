package org.jtheque.file;

import org.jtheque.utils.io.FileException;

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

/**
 * An importer.
 *
 * @author Baptiste Wicht
 */
public interface Importer {
    /**
     * Indicate if the importer can import from a specific file format or not.
     *
     * @param fileType The file format.
     *
     * @return true ou false
     */
    boolean canImportFrom(String fileType);

    /**
     * Import all the data from the file.
     *
     * @param filePath The path to the file.
     *
     * @throws org.jtheque.utils.io.FileException This exception is thrown when the import cannot be correctly made.
     */
    void importFrom(String filePath) throws FileException;
}