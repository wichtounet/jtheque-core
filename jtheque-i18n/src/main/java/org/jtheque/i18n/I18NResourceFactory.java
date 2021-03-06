package org.jtheque.i18n;

import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.annotations.ThreadSafe;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.net.URL;

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
 * An utility factory for I18NResource.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class I18NResourceFactory {
    /**
     * Utility class, not instantiable.
     */
    private I18NResourceFactory() {
        throw new AssertionError();
    }

    /**
     * Construct a new I18NResource from the given URL.
     *
     * @param name The name of the file.
     * @param url  The url to create the resource to.
     *
     * @return The I18NResource to the URL.
     */
    public static I18NResource fromURL(String name, URL url) {
        Resource resource = new UrlResource(url);

        return new I18NResourceImpl(name, resource);
    }

    /**
     * Construct a I18NResource from the path using the class (with the class loader) to load it.
     *
     * @param classz The class to get the class loader.
     * @param path   The resource path.
     *
     * @return The I18NResource corresponding to the given resource.
     */
    public static I18NResource fromResource(Class<?> classz, String path) {
        Resource resource = new InputStreamResource(classz.getClassLoader().getResourceAsStream(path));

        return new I18NResourceImpl(path.substring(path.lastIndexOf('/')), resource);
    }

    /**
     * Construct a I18NResource from the given file.
     *
     * @param file The file to use to create the I18NResource.
     *
     * @return The I18NResource corresponding to the given file.
     */
    public static I18NResource fromFile(File file) {
        return new I18NResourceImpl(file.getName(), new FileSystemResource(file));
    }

    /**
     * An i18n resource implementation.
     *
     * @author Baptiste Wicht
     */
    @Immutable
    private static final class I18NResourceImpl implements I18NResource {
        private final String fileName;
        private final Resource resource;

        /**
         * Construct a new I18NResourceImpl.
         *
         * @param fileName The file name.
         * @param resource The spring resource to the file.
         */
        private I18NResourceImpl(String fileName, Resource resource) {
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
}