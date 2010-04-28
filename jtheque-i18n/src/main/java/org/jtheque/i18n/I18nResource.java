package org.jtheque.i18n;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.InputStream;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
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