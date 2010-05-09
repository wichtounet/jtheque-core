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
 * An header for a JTheque File.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractJTFileHeader {
    private String key;
    private Version versionJTheque;
    private int fileVersion;
    private int date;

    /**
     * Return the hash key.
     *
     * @return The hash key.
     */
    protected final CharSequence getKey() {
        return key;
    }

    /**
     * Set the hash key.
     *
     * @param key The hash key.
     */
    public final void setKey(String key) {
        this.key = key;
    }

    /**
     * Set the JTheque Version.
     *
     * @param version The version of JTheque.
     */
    public final void setVersionJTheque(Version version) {
        versionJTheque = version;
    }

    /**
     * Return the version of JTheque.
     *
     * @return The version of JTheque.
     */
    protected final Version getVersionJTheque() {
        return versionJTheque;
    }

    /**
     * Set the date of the file.
     *
     * @param date The date of the file.
     */
    public final void setDate(int date) {
        this.date = date;
    }

    /**
     * Return the date of the file.
     *
     * @return The date of the file.
     */
    public final int getDate() {
        return date;
    }

    /**
     * Return the version of the file.
     *
     * @return The version of the file.
     */
    public final int getFileVersion() {
        return fileVersion;
    }

    /**
     * Set the file version.
     *
     * @param fileVersion The version of the file.
     */
    public final void setFileVersion(int fileVersion) {
        this.fileVersion = fileVersion;
    }

    /**
     * Indicate if the file is complete or not.
     *
     * @return true if the file is complete else false.
     */
    public abstract boolean isComplete();
}