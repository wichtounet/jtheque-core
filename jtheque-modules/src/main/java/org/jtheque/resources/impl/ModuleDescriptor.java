package org.jtheque.resources.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

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
 * A module descriptor.
 *
 * @author Baptiste Wicht
 */
public class ModuleDescriptor extends AbstractDescriptor {
    private final List<ModuleVersion> moduleVersions = new ArrayList<ModuleVersion>(5);

    /**
     * Construct a new ModuleDescriptor.
     *
     * @param id The id of the module.
     */
    public ModuleDescriptor(String id) {
        super(id);
    }

    /**
     * Return all the versions of the descriptor.
     *
     * @return A Collection containing all the versions of the descriptor.
     */
    public Collection<ModuleVersion> getVersions() {
        return moduleVersions;
    }

    /**
     * Add a version to the descriptor.
     *
     * @param version The version to add.
     */
    public void addVersion(ModuleVersion version) {
        moduleVersions.add(version);
    }

    /**
     * Return the most recent version of the VersionsFile.
     *
     * @return The most recent version.
     *
     * @throws NoSuchElementException If the version's file contains no version.
     */
    public ModuleVersion getMostRecentVersion() {
        if (moduleVersions.isEmpty()) {
            throw new NoSuchElementException("The descriptor contains no versions. ");
        }

        return moduleVersions.get(moduleVersions.size() - 1);
    }
}