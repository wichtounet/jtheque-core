package org.jtheque.updates.impl;

import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;
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
@Immutable
final class ModuleDescriptor {
    private final String id;
    private final Collection<ModuleVersion> moduleVersions;
    private final ModuleVersion mostRecent;

    /**
     * Construct a new ModuleDescriptor.
     *
     * @param id             The id of the module.
     * @param moduleVersions The module versions contained in the descriptions.
     */
    ModuleDescriptor(String id, Collection<ModuleVersion> moduleVersions) {
        super();

        this.id = id;
        this.moduleVersions = CollectionUtils.protectedCopy(moduleVersions);

        mostRecent = moduleVersions.isEmpty() ? null : CollectionUtils.last(moduleVersions);
    }

    /**
     * Return the id of the resource.
     *
     * @return The id of the resource.
     */
    String getId() {
        return id;
    }

    /**
     * Return all the versions of the descriptor.
     *
     * @return A Collection containing all the versions of the descriptor.
     */
    Collection<ModuleVersion> getVersions() {
        return moduleVersions;
    }

    /**
     * Return the most recent version of the VersionsFile.
     *
     * @return The most recent version.
     *
     * @throws NoSuchElementException If the version's file contains no version.
     */
    ModuleVersion getMostRecentVersion() {
        if (moduleVersions.isEmpty()) {
            throw new NoSuchElementException("The descriptor contains no versions. ");
        }

        return mostRecent;
    }
}