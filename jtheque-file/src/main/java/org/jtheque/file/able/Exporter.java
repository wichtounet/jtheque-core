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

/**
 * An exporter. It's simply an object who export to a specific format.
 *
 * @author Baptiste Wicht
 */
public interface Exporter {
    /**
     * Indicate if the exporter can export to a specific fileType.
     *
     * @param fileType The fileType to export to.
     * @return true if the exporter can export to this file type, else false.
     */
    boolean canExportTo(FileType fileType);
}