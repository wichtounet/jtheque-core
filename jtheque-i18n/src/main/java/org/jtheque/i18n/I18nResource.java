package org.jtheque.i18n;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.InputStream;

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

public class I18nResource {
    private final String fileName;
    private final Resource resource;

    public I18nResource(String fileName, Resource resource) {
        super();

        this.fileName = fileName;
        this.resource = resource;
    }

    public String getFileName() {
        return fileName;
    }

    public Resource getResource() {
        return resource;
    }

    public static I18nResource fromResource(Class<?> classz, String path) {
        Resource resource = new InputStreamResource(classz.getClassLoader().getResourceAsStream(path));

        return new I18nResource(path.substring(path.lastIndexOf('/')), resource);
    }

    public static I18nResource fromResource(String fileName, Class<?> classz, String path) {
        Resource resource = new InputStreamResource(classz.getClassLoader().getResourceAsStream(path));

        return new I18nResource(fileName, resource);
    }

    public static I18nResource fromFile(File file) {
        return new I18nResource(file.getName(), new FileSystemResource(file));
    }

    @Override
    public String toString() {
        return "I18nResource{" +
                "fileName='" + fileName + '\'' +
                ", resource=" + resource +
                '}';
    }
}