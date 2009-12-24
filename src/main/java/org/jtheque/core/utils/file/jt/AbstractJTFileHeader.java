package org.jtheque.core.utils.file.jt;

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