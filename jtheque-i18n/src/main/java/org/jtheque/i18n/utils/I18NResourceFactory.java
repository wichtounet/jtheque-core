package org.jtheque.i18n.utils;

import org.jtheque.i18n.able.I18NResource;
import org.jtheque.i18n.impl.I18NResourceImpl;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

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

public class I18NResourceFactory {
	private I18NResourceFactory() {
		super();
	}

	public static I18NResource fromResource(Class<?> classz, String path) {
        Resource resource = new InputStreamResource(classz.getClassLoader().getResourceAsStream(path));

        return new I18NResourceImpl(path.substring(path.lastIndexOf('/')), resource);
    }

    public static I18NResource fromResource(String fileName, Class<?> classz, String path) {
        Resource resource = new InputStreamResource(classz.getClassLoader().getResourceAsStream(path));

        return new I18NResourceImpl(fileName, resource);
    }

    public static I18NResource fromFile(File file) {
        return new I18NResourceImpl(file.getName(), new FileSystemResource(file));
    }
}