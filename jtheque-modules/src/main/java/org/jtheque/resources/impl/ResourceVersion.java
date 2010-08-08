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

/**
 * A resource version.
 *
 * @author Baptiste Wicht
 */
public final class ResourceVersion implements Comparable<ResourceVersion> {
    private final Version version;
    private final String file;
    private final String url;
    private final boolean library;

    /**
     * Construct a new ResourceVersion.
     *
     * @param version The version of the resource.
     * @param file    The file of the resource.
     * @param url     The url to download the file.
     * @param library A boolean tag indicating if the resource is a library.
     */
    public ResourceVersion(Version version, String file, String url, boolean library) {
        super();

        this.version = version;
        this.file = file;
        this.url = url;
        this.library = library;
    }

    /**
     * Return the JTheque's version.
     *
     * @return The JTheque's version.
     */
    public final Version getVersion() {
        return version;
    }

    public String getFile() {
        return file;
    }

    public boolean isLibrary() {
        return library;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResourceVersion) {
            ResourceVersion other = (ResourceVersion) obj;

            return EqualsBuilder.newBuilder(this, obj).
                    addField(version, other.version).
                    addField(file, other.file).
                    addField(url, other.url).
                    addField(library, other.library).
                    areEquals();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCodeDirect(version, file, url, library);
    }

    @Override
    public final int compareTo(ResourceVersion o) {
        return version.compareTo(o.version);
    }
}