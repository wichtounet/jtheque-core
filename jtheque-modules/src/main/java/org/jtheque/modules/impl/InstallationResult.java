package org.jtheque.modules.impl;

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
 * The result of a module installation.
 *
 * @author Baptiste Wicht
 */
public final class InstallationResult {
    private boolean installed;
    private String jarFile;

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
}