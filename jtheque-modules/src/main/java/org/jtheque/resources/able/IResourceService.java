package org.jtheque.resources.able;

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

import org.jtheque.resources.impl.Resource;
import org.jtheque.utils.bean.Version;

import java.util.List;

/**
 * A resource service specification. Manage the resource of the modules.
 *
 * @author Baptiste Wicht
 */
public interface IResourceService {
    void addResource(Resource resource);

    /**
     * Return all the resources of the resource service.
     *
     * @return A List containing all the resources.
     */
    List<IResource> getResources();

    List<Version> getVersions(String resourceName);

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
    IResource getResource(String id, Version version);

    /**
     * Download the resource at the given URL and with the given version.
     *
     * @param url     The URL of the descriptor.
     * @param version The version to download.
     *
     * @return The downloaded resource.
     */
    IResource downloadResource(String url, Version version);

    /**
     * Install the given resource.
     *
     * @param resource The resource to install.
     */
    void installResource(IResource resource);

    /**
     * Indicate if a re
     *
     * @param id      The id of the resource to get.
     * @param version The version of the resource to get.
     *
     * @return true if the resource is installed otherwise false.
     */
    boolean isInstalled(String id, Version version);
}
