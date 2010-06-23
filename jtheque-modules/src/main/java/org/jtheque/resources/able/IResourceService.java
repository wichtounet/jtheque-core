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

public interface IResourceService {
    void addResource(Resource resource);

    List<IResource> getResources();

    List<Version> getVersions(String resourceName);

    boolean exists(String resourceName);

    IResource getResource(String id, Version version);

    IResource downloadResource(String url, Version version);

    void installResource(IResource resource);

    boolean isInstalled(String name, Version version);
}