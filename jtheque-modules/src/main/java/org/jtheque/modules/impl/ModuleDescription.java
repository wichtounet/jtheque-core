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

import org.jtheque.utils.bean.InternationalString;
import org.jtheque.utils.bean.Version;

/**
 * An online description of a module.
 *
 * @author Baptiste Wicht
 */
public final class ModuleDescription {
    private String id;
    private String name;
    private InternationalString description;
    private String versionsFileURL;
    private Version coreVersion;

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

    /**
     * Return the description of the module.
     *
     * @return The description of the module.
     */
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

    /**
     * Return the versions file URL.
     *
     * @return The URL of the versions file.
     */
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

    /**
     * Return the core version.
     *
     * @return The version of the core.
     */
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

    /**
     * Return the id of the module.
     *
     * @return The id of the module.
     */
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