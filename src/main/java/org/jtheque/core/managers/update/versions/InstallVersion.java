package org.jtheque.core.managers.update.versions;

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

/**
 * An installation version.
 *
 * @author Baptiste Wicht
 */
public final class InstallVersion extends OnlineVersion {
    private String jarFile;
    private String title;

    /**
     * Return the jar file name.
     *
     * @return The name of the jar file.
     */
    public String getJarFile() {
        return jarFile;
    }

    /**
     * Set the jar file name.
     *
     * @param jarFile The name of the jar file of the module.
     */
    public void setJarFile(String jarFile) {
        this.jarFile = jarFile;
    }

    /**
     * Return the title of the version.
     *
     * @return The title of the version.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the version.
     *
     * @param title The title of version.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "InstallVersion{" +
                "title='" + title + '\'' +
                ", jarFile='" + jarFile + '\'' +
                ", version='" + getVersion() + '\'' +
                ", core='" + getCoreVersion() + '\'' +
                ", actions='" + getActions() + '\'' +
                ", patches='" + getPatches() + '\'' +
                '}';
    }
}