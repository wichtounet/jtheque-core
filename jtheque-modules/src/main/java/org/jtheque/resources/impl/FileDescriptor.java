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

    private Version version;

    /**
     * Create a new FileDescriptor.
     *
     * @param name    The name of the file.
     * @param url     The url to the file.
     */
    public FileDescriptor(String name, String url) {
        super();

        this.name = name;
        this.url = url;
    }

    /**
     * Create a new FileDescriptor.
     *
     * @param name    The name of the file.
     * @param url     The url to the file.
     * @param version The version of the file.
     */
    public FileDescriptor(String name, String url, Version version) {
        super();

        this.name = name;
        this.url = url;
        this.version = version;
    }

    /**
     * Return the name of the file descriptor.
     *
     * @return The name of the file descriptor. 
     */
    public String getName() {
        return name;
    }

    /**
     * Return the URL of the file descriptor.
     *
     * @return The URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Return the version of the file descriptor.
     *
     * @return The version of the file descriptor.
     */
    public Version getVersion() {
        return version;
    }
}