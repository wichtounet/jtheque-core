package org.jtheque.resources.impl;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.slf4j.LoggerFactory;

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

/**
 * A library resource. Basically it's a simple file that can be installed in a bundle context. This class is immutable.
 *
 * @author Baptiste Wicht
 */
public class LibraryResource extends FileResource {
    private Bundle bundle;

    /**
     * Construct a new LibraryResource.
     *
     * @param id     The id of the resource.
     * @param folder The folder in which the resource is.
     */
    public LibraryResource(String id, File folder) {
        super(id, folder);
    }

    @Override
    public void install(BundleContext bundleContext) {
        if (bundle == null) {
            try {
                bundle = bundleContext.installBundle("file:" + getResourceFolder().getAbsolutePath() + '/' + getId());
            } catch (BundleException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }
        }
    }
}