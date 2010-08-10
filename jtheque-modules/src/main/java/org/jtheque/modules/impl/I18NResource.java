package org.jtheque.modules.impl;

import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;

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
@Immutable
final class I18NResource {
    private final Collection<String> resources;
    private final String name;
    private final Version version;

    /**
     * Construct a new I18NResource.
     *
     * @param name      The name of the resource.
     * @param version   The version of the resource.
     * @param resources The resources.
     */
    I18NResource(String name, Version version, Collection<String> resources) {
        super();

        this.name = name;
        this.version = version;
        this.resources = CollectionUtils.protectedCopy(resources);
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
     *
     * @return The version of the i18n resource.
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Return the resources.
     *
     * @return A Collection containing all the resources.
     */
    public Collection<String> getResources() {
        return resources;
    }
}