package org.jtheque.modules.impl;

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
 * The result of a module installation.
 *
 * @author Baptiste Wicht
 */
public final class InstallationResult {
    private boolean installed;
    private String jarFile;
    private String name;

    /**
     * Indicate if the module has been installed.
     *
     * @return true if the module has been installed else false.
     */
    public boolean isInstalled() {
        return installed;
    }

    /**
     * Set if the module has been installed or not.
     *
     * @param installed A boolean tag indicating if the module has been installed or not.
     */
    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    /**
     * Return the jar-file name.
     *
     * @return The name of the jar file.
     */
    public String getJarFile() {
        return jarFile;
    }

    /**
     * Set the jar file name.
     *
     * @param jarFile The name of the jar file.
     */
    public void setJarFile(String jarFile) {
        this.jarFile = jarFile;
    }

    /**
     * Return the name of the module.
     *
     * @return The name of the module.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the module.
     *
     * @param name The name of the module.
     */
    public void setName(String name) {
        this.name = name;
	}
}