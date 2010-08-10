package org.jtheque.update.impl;

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

import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A core descriptor.
 *
 * @author Baptiste Wicht
 */
@Immutable
public final class CoreDescriptor {
    private final List<CoreVersion> versions;

    public CoreDescriptor(Collection<CoreVersion> versions) {
        super();

        this.versions = CollectionUtils.copyOf(versions);
    }

    /**
     * Return all the versions contained in the file.
     *
     * @return A list containing all the versions of the file.
     */
    public Collection<CoreVersion> getVersions() {
        return CollectionUtils.protect(versions);
    }

    /**
     * Return the most recent version of the VersionsFile.
     *
     * @return The most recent version.
     *
     * @throws NoSuchElementException If the version's file contains no version.
     */
    public CoreVersion getMostRecentVersion() {
        if (versions.isEmpty()) {
            throw new NoSuchElementException("The version's file contains no versions. ");
        }

        return versions.get(versions.size() - 1);
    }
}