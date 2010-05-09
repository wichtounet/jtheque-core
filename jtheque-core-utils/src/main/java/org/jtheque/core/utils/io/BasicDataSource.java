package org.jtheque.core.utils.io;

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

import org.jtheque.utils.bean.Version;

/**
 * A simple datasource for JT files.
 *
 * @author Baptiste Wicht
 */
public class BasicDataSource {
    private int fileVersion;
    private Version version;

    /**
     * Return the version of the file.
     *
     * @return The version of the file.
     */
    public int getFileVersion() {
        return fileVersion;
    }

    /**
     * Set the file version.
     *
     * @param fileVersion The version of the file.
     */
    public void setFileVersion(int fileVersion) {
        this.fileVersion = fileVersion;
    }

    /**
     * Return the version of JTheque.
     *
     * @return The version of JTheque.
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Set the JTheque Version.
     *
     * @param version The version of JTheque.
     */
    public void setVersion(Version version) {
        this.version = version;
    }
}