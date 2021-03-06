package org.jtheque.resources;

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

import org.jtheque.utils.bean.Version;

import java.util.Collection;
import java.util.List;

/**
 * A resource service specification. Manage the resource of the modules.
 *
 * @author Baptiste Wicht
 */
public interface ResourceService {
    /**
     * Add a resource.
     *
     * @param resource The resource to add to the service.
     */
    void addResource(Resource resource);

    /**
     * Return all the resources of the resource service.
     *
     * @return A List containing all the resources.
     */
    Collection<Resource> getResources();

    /**
     * Indicate if a resource with the given name already exists or not.
     *
     * @param resourceName The resource name to test.
     *
     * @return true if the resource exists else false.
     */
    boolean exists(String resourceName);

    /**
     * Return the resource of the given id with the given version.
     *
     * @param id      The id of the resource to get.
     * @param version The version of the resource to get.
     *
     * @return The resource if found otherwise null.
     */
    Resource getResource(String id, Version version);

    /**
     * Get the resource with the given id and version if already installed otherwise download it using the given
     * descriptor url and return it.
     *
     * @param id      The id of the resource.
     * @param version The version of the resource.
     * @param url     The url of the descriptor.
     *
     * @return The resource.
     */
    Resource getOrDownloadResource(String id, Version version, String url);

    /**
     * Return all the versions of the resource with the given name.
     *
     * @param resourceName The name of the resource to search the versions for.
     *
     * @return A List containing all the versions of the given resource.
     */
    List<Version> getVersions(String resourceName);

    /**
     * Install the given resource.
     *
     * @param resource The resource to installFromRepository.
     */
    void installResource(Resource resource);

    /**
     * Indicate if a re
     *
     * @param id      The id of the resource to get.
     * @param version The version of the resource to get.
     *
     * @return true if the resource is installed otherwise false.
     */
    boolean isNotInstalled(String id, Version version);
}
