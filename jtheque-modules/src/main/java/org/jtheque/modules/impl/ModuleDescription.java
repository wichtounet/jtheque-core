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

import org.jtheque.modules.able.IModuleDescription;
import org.jtheque.utils.bean.InternationalString;
import org.jtheque.utils.bean.Version;

/**
 * An online description of a module.
 *
 * @author Baptiste Wicht
 */
public final class ModuleDescription implements IModuleDescription {
    private String id;
    private String name;
    private InternationalString description;
    private String versionsFileURL;
    private Version coreVersion;

    @Override
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

    @Override
    public InternationalString getDescription() {
        return description;
    }

    /**
     * Set the description of the module.
     *
     * @param description The description of the module.
     */
    public void setDescription(InternationalString description) {
        this.description = description;
    }

    @Override
    public String getVersionsFileURL() {
        return versionsFileURL;
    }

    /**
     * Set the versions file URL.
     *
     * @param versionsFileURL The URL of the versions file.
     */
    public void setVersionsFileURL(String versionsFileURL) {
        this.versionsFileURL = versionsFileURL;
    }

    @Override
    public Version getCoreVersion() {
        return coreVersion;
    }

    /**
     * Set the version of the core.
     *
     * @param coreVersion The version of the core.
     */
    public void setCoreVersion(Version coreVersion) {
        this.coreVersion = coreVersion;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Set the id of the module.
     *
     * @param id The id of the module.
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ModuleDescription{" +
                "name='" + name + '\'' +
                ", description=" + description +
                ", versionsFileURL='" + versionsFileURL + '\'' +
                ", core=" + coreVersion +
                '}';
    }
}