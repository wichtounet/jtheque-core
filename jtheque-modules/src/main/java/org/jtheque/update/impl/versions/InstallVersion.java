package org.jtheque.update.impl.versions;

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
                '}';
    }
}