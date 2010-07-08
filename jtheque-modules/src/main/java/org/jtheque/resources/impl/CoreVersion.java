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

import org.jtheque.utils.bean.Version;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A core version.
 *
 * @author Baptiste Wicht
 */
public class CoreVersion implements Comparable<CoreVersion> {
    private final Version version;
    private final List<FileDescriptor> bundles = new ArrayList<FileDescriptor>(5);

    /**
     * Construct a new CoreVersion.
     *
     * @param version The version of the resource.
     */
    public CoreVersion(Version version) {
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

    public Iterable<FileDescriptor> getBundles() {
        return Collections.unmodifiableList(bundles);
    }

    @Override
    public final int compareTo(CoreVersion o) {
        return version.compareTo(o.version);
    }

    /**
     * Add the bundle to the version.
     *
     * @param descriptor The descriptor.
     */
    public void addBundle(FileDescriptor descriptor) {
        bundles.add(descriptor);
    }
}