package org.jtheque.modules.impl;

import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;
import java.util.List;

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
 * An i18n description.
 *
 * @author Baptiste Wicht
 */
public class I18NResource {
    private final List<String> resources = CollectionUtils.newList(5);

    private final String name;
    private final Version version;

    /**
     * Construct a new I18NResource.
     *
     * @param name    The name of the resource.
     * @param version The version of the resource.
     */
    public I18NResource(String name, Version version) {
        super();

        this.name = name;
        this.version = version;
    }

    /**
     * Return the name of the i18n resource.
     *
     * @return The name of the i18n resource.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the version of the i18n resource.
     * @return The version of the i18n resource.
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Return the resources.
     * @return A Collection containing all the resources. 
     */
    public Collection<String> getResources() {
        return resources;
    }

    /**
     * Add a resource to the i18n resource.
     *
     * @param resource The resource to add. 
     */
    public void addResource(String resource) {
        resources.add(resource);
    }
}