package org.jtheque.core.managers.file.able;

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
 * A simple datasource for JT files.
 *
 * @author Baptiste Wicht
 */
public final class BasicDataSource {
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