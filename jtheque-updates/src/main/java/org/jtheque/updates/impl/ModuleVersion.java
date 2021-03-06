package org.jtheque.updates.impl;

import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;

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
@Immutable
final class ModuleVersion {
    private final Version version;
    private final Version coreVersion;
    private final String moduleFile;
    private final String moduleURL;
    private final Collection<FileDescriptor> resources;

    /**
     * Create a new ModuleVersion.
     *
     * @param version     The version.
     * @param coreVersion The core version needed by this module version.
     * @param moduleFile  The module JAR file name.
     * @param moduleURL   The URL to the module file.
     * @param resources   The resources needed by this module version.
     */
    ModuleVersion(Version version, Version coreVersion, String moduleFile, String moduleURL, Collection<FileDescriptor> resources) {
        super();

        this.version = version;
        this.coreVersion = coreVersion;
        this.moduleFile = moduleFile;
        this.moduleURL = moduleURL;
        this.resources = CollectionUtils.protectedCopy(resources);
    }

    /**
     * Return the Version of the module.
     *
     * @return The Version of the module.
     */
    Version getVersion() {
        return version;
    }

    /**
     * Return the version of the core this version of the module needs.
     *
     * @return The Version of the core needed to run this module version.
     */
    Version getCoreVersion() {
        return coreVersion;
    }

    /**
     * Return the module file name.
     *
     * @return The module file name.
     */
    String getModuleFile() {
        return moduleFile;
    }

    /**
     * Return the URL to the module file.
     *
     * @return The URL to the module file.
     */
    String getModuleURL() {
        return moduleURL;
    }

    /**
     * Return all the resources.
     *
     * @return An Iterable on all the resources of the module.
     */
    Iterable<FileDescriptor> getResources() {
        return resources;
    }
}