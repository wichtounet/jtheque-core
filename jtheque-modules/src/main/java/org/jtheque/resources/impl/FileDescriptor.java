package org.jtheque.resources.impl;

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
 * A File Descriptor. Describe a file or library in a resource descriptor.
 *
 * @author Baptiste Wicht
 */
public class FileDescriptor {
    private final String name;
    private final String url;
    private final Version version;

    public FileDescriptor(String name, String url, Version version) {
        super();

        this.name = name;
        this.url = url;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Version getVersion() {
        return version;
    }
}