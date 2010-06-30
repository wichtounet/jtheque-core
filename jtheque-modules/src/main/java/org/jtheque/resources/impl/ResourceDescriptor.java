package org.jtheque.resources.impl;

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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A resource descriptor.
 *
 * @author Baptiste Wicht
 */
public final class ResourceDescriptor {
    private final String id;
    private final List<ResourceVersion> versions = new ArrayList<ResourceVersion>(5);

    /**
     * Construct a new ImageDescriptor with the given ID.
     *
     * @param id The id of the resource.
     */
    public ResourceDescriptor(String id) {
        super();

        this.id = id;
    }

    /**
     * Return the id of the resource.
     *
     * @return The id of the resource.
     */
    public String getId() {
        return id;
    }

    /**
     * Return all the versions contained in the file.
     *
     * @return A list containing all the versions of the file.
     */
    public Iterable<ResourceVersion> getVersions() {
        return versions;
    }

    /**
     * Add a version to the resource descriptor.
     *
     * @param version The new version to add to the descriptor. 
     */
    public void addVersion(ResourceVersion version) {
        versions.add(version);
    }

    /**
     * Return the most recent version of the VersionsFile.
     *
     * @return The most recent version.
     *
     * @throws NoSuchElementException If the version's file contains no version.
     */
    public ResourceVersion getMostRecentVersion() {
        if (versions.isEmpty()) {
            throw new NoSuchElementException("The version's file contains no versions. ");
        }

        return versions.get(versions.size() - 1);
    }
}