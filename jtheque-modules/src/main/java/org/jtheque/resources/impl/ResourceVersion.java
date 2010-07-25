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

import org.jtheque.utils.bean.EqualsBuilder;
import org.jtheque.utils.bean.HashCodeUtils;
import org.jtheque.utils.bean.Version;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A resource version.
 *
 * @author Baptiste Wicht
 */
public class ResourceVersion implements Comparable<ResourceVersion> {
    private final Version version;
    private final List<FileDescriptor> files = new ArrayList<FileDescriptor>(5);
    private final List<FileDescriptor> libraries = new ArrayList<FileDescriptor>(5);

    /**
     * Construct a new ResourceVersion.
     *
     * @param version The version of the resource.
     */
    public ResourceVersion(Version version) {
        super();

        this.version = version;
    }

    /**
     * Return the JTheque's version.
     *
     * @return The JTheque's version.
     */
    public final Version getVersion() {
        return version;
    }

    /**
     * Return the version transformed to string.
     *
     * @return The string version.
     */
    public final String getStringVersion() {
        return version.getVersion();
    }

    /**
     * Return all the files of the resource.
     *
     * @return An Iterable on all the files of the resource.
     */
    public Iterable<FileDescriptor> getFiles() {
        return Collections.unmodifiableList(files);
    }

    /**
     * Return all the libraries of the resource.
     *
     * @return An Iterable on all the libraries of the resource. 
     */
    public Iterable<FileDescriptor> getLibraries() {
        return Collections.unmodifiableList(libraries);
    }

    /**
     * Add the file to the version.
     *
     * @param descriptor The descriptor.
     */
    public void addFile(FileDescriptor descriptor) {
        files.add(descriptor);
    }

    /**
     * Add the library to the version.
     *
     * @param descriptor The descriptor.
     */
    public void addLibrary(FileDescriptor descriptor) {
        libraries.add(descriptor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResourceVersion) {
            ResourceVersion other = (ResourceVersion) obj;

            return EqualsBuilder.newBuilder(this, obj).
                    addField(version, other.version).
                    addField(files, other.files).
                    addField(libraries, other.libraries).
                    areEquals();
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCodeDirect(version, files, libraries);
    }

    @Override
    public final int compareTo(ResourceVersion o) {
        return version.compareTo(o.version);
    }
}