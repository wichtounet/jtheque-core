package org.jtheque.views.impl.models;

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
import org.jtheque.resources.ResourceService;
import org.jtheque.ui.utils.models.SimpleListModel;

/**
 * A List model to display the resources.
 *
 * @author Baptiste Wicht
 */
public final class ResourceListModel extends SimpleListModel<Resource> {
    private static final long serialVersionUID = 7991835157869808160L;

    /**
     * Create a new ResourceListModel.
     *
     * @param resourceService The resource service.
     */
    public ResourceListModel(ResourceService resourceService) {
        super();

        setElements(resourceService.getResources());
    }
}