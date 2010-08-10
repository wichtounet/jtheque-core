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

import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;

/**
 * A resource descriptor.
 *
 * @author Baptiste Wicht
 */
@Immutable
public final class ResourceDescriptor extends AbstractDescriptor {
    private final Collection<ResourceVersion> versions;

    /**
     * Construct a new ImageDescriptor with the given ID.
     *
     * @param id       The id of the resource.
     * @param versions The versions of the descriptor.
     */
    public ResourceDescriptor(String id, Collection<ResourceVersion> versions) {
        super(id);

        this.versions = CollectionUtils.protectedCopy(versions);
    }

    /**
     * Return all the versions contained in the file.
     *
     * @return A list containing all the versions of the file.
     */
    public Iterable<ResourceVersion> getVersions() {
        return versions;
    }
}