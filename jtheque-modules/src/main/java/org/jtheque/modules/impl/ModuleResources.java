package org.jtheque.modules.impl;

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

import org.jtheque.modules.able.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * The module resources.
 *
 * @author Baptiste Wicht
 */
public class ModuleResources implements Resources {
    private final List<String> resources = new ArrayList<String>(5);
    private final List<String> i18nResources = new ArrayList<String>(5);

    /**
     * Add a resource.
     *
     * @param name The name of the resource.
     */
    void addResource(String name) {
        resources.add(name);
    }

    /**
     * Add an i18n resource.
     *
     * @param name The name of the i18n resource.
     */
    void addI18NResource(String name) {
        i18nResources.add(name);
    }

    @Override
    public List<String> getResources() {
        return resources;
    }

    @Override
    public List<String> getI18NResources() {
        return i18nResources;
	}
}
