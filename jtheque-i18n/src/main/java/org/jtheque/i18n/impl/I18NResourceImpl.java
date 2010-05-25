package org.jtheque.i18n.impl;

import org.jtheque.i18n.able.I18NResource;
import org.springframework.core.io.Resource;

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
 * An i18n resource implementation.
 *
 * @author Baptiste Wicht
 */
public final class I18NResourceImpl implements I18NResource {
    private final String fileName;
    private final Resource resource;

    /**
     * Construct a new I18NResourceImpl.
     *
     * @param fileName The file name.
     * @param resource The spring resource to the file.
     */
    public I18NResourceImpl(String fileName, Resource resource) {
        super();

        this.fileName = fileName;
        this.resource = resource;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public Resource getResource() {
        return resource;
    }

    @Override
    public String toString() {
        return "I18NResourceImpl{" +
                "fileName='" + fileName + '\'' +
                ", resource=" + resource +
                '}';
    }
}