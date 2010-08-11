package org.jtheque.resources.impl;

import org.jtheque.resources.Resource;
import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.bean.Version;

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
 * A resource implementation.
 *
 * @author Baptiste Wicht
 */
@Immutable
public final class ResourceImpl implements Resource {
    private final String id;
    private final Version version;
    private final String url;
    private final String file;
    private final boolean library;

    /**
     * Construct a new ResourceImpl.
     *
     * @param id      The id of the resource.
     * @param version The version of the resource set.
     * @param url     The url of the resource set.
     * @param file    The file of the resource.
     * @param library The library of the resource.
     */
    public ResourceImpl(String id, Version version, String url, String file, boolean library) {
        super();

        this.id = id;
        this.version = version;
        this.url = url;
        this.file = file;
        this.library = library;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getFile() {
        return file;
    }

    @Override
    public boolean isLibrary() {
        return library;
    }
}