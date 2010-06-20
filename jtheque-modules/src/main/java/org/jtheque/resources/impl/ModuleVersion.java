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

public class ModuleVersion extends ResourceVersion {
    private Version coreVersion;
    private String moduleFile;
    private String moduleURL;

    public ModuleVersion(Version version) {
        super(version);
    }

    public Version getCoreVersion() {
        return coreVersion;
    }

    public void setCoreVersion(Version coreVersion) {
        this.coreVersion = coreVersion;
    }

    public String getModuleFile() {
        return moduleFile;
    }

    public void setModuleFile(String moduleFile) {
        this.moduleFile = moduleFile;
    }

    public String getModuleURL() {
        return moduleURL;
    }

    public void setModuleURL(String moduleURL) {
        this.moduleURL = moduleURL;
    }
}