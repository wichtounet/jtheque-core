package org.jtheque.resources.impl;

import org.jtheque.resources.able.SimpleResource;

import org.osgi.framework.BundleContext;

import java.io.File;

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

public class FileResource implements SimpleResource {
    private final String id;
    private final File resourceFolder;

    /**
     * Construct a new FileResource.
     *
     * @param id             The id of the file.
     * @param resourceFolder The resource folder.
     */
    public FileResource(String id, File resourceFolder) {
        super();

        this.id = id;
        this.resourceFolder = resourceFolder;
    }

    @Override
    public String getId() {
        return id;
    }

    public File getResourceFolder() {
        return resourceFolder;
    }

    @Override
    public void install(BundleContext bundleContext) {
        //Nothing to install
    }
}