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
import org.jtheque.utils.bean.EqualsBuilder;
import org.jtheque.utils.bean.HashCodeUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;

/**
 * A core version.
 *
 * @author Baptiste Wicht
 */
@Immutable
final class CoreVersion implements Comparable<CoreVersion> {
    private final Version version;
    private final Collection<FileDescriptor> bundles;

    /**
     * Construct a new CoreVersion.
     *
     * @param version The version of the resource.
     * @param bundles The bundles of the version.
     */
    CoreVersion(Version version, Collection<FileDescriptor> bundles) {
        super();

        this.version = version;
        this.bundles = CollectionUtils.protectedCopy(bundles);
    }

    /**
     * Return the JTheque's version.
     *
     * @return The JTheque's version.
     */
    Version getVersion() {
        return version;
    }

    /**
     * Return the bundles of the core version. This is the list of the bundles that will be downloaded in update.
     *
     * @return An Iterable on the descriptors.
     */
    Iterable<FileDescriptor> getBundles() {
        return bundles;
    }

    @Override
    public int compareTo(CoreVersion o) {
        return version.compareTo(o.version);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CoreVersion) {
            CoreVersion other = (CoreVersion) obj;

            return EqualsBuilder.newBuilder(this, obj).
                    addField(version, other.version).
                    addField(bundles, other.bundles).
                    areEquals();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCodeDirect(version, bundles);
    }
}