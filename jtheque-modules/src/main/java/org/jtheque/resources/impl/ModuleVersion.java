package org.jtheque.resources.impl;

import org.jtheque.utils.bean.Version;

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
 * A Module version.
 *
 * @author Baptiste Wicht
 */
public class ModuleVersion extends ResourceVersion {
    private Version coreVersion;
    private String moduleFile;
    private String moduleURL;

    /**
     * Create a new ModuleVersion.
     *
     * @param version The version.
     */
    public ModuleVersion(Version version) {
        super(version);
    }

    /**
     * Return the version of the core this version of the module needs.
     *
     * @return The Version of the core needed to run this module version.
     */
    public Version getCoreVersion() {
        return coreVersion;
    }

    /**
     * Set the core version needed by this version of the module.
     *
     * @param coreVersion The core version this module version needs.
     */
    public void setCoreVersion(Version coreVersion) {
        this.coreVersion = coreVersion;
    }

    /**
     * Return the module file name.
     *
     * @return The module file name. 
     */
    public String getModuleFile() {
        return moduleFile;
    }

    /**
     * Set the module file. It's the name of the module jar file.
     *
     * @param moduleFile The module file.
     */
    public void setModuleFile(String moduleFile) {
        this.moduleFile = moduleFile;
    }

    /**
     * Return the URL to the module file.
     *
     * @return The URL to the module file.
     */
    public String getModuleURL() {
        return moduleURL;
    }

    /**
     * Set the module URL.
     *
     * @param moduleURL The module URL.
     */
    public void setModuleURL(String moduleURL) {
        this.moduleURL = moduleURL;
    }
}