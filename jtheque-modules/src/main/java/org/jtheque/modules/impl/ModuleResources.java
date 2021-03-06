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

import org.jtheque.resources.Resource;
import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;

/**
 * The module resources.
 *
 * @author Baptiste Wicht
 */
@Immutable
final class ModuleResources {
    private final Collection<ImageResource> imageResources;
    private final Collection<I18NResource> i18nResources;
    private final Collection<Resource> resources;

    /**
     * Create a new ModuleResources.
     *
     * @param imageResources The image resources.
     * @param i18nResources  The i18n resources.
     * @param resources      The resources.
     */
    ModuleResources(Collection<ImageResource> imageResources, Collection<I18NResource> i18nResources, Collection<Resource> resources) {
        super();

        this.imageResources = CollectionUtils.protectedCopy(imageResources);
        this.i18nResources = CollectionUtils.protectedCopy(i18nResources);
        this.resources = CollectionUtils.protectedCopy(resources);
    }

    /**
     * Return all the image resources.
     *
     * @return An Iterable on the image resources.
     */
    public Iterable<ImageResource> getImageResources() {
        return imageResources;
    }

    /**
     * Return all the i18n resources.
     *
     * @return An Iterable on the i18n resources.
     */
    public Iterable<I18NResource> getI18NResources() {
        return i18nResources;
    }

    /**
     * Return all the resources.
     *
     * @return An Iterable on the resources.
     */
    public Iterable<Resource> getResources() {
        return resources;
    }
}
