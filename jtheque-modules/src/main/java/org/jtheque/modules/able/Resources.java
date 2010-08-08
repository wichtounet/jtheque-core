package org.jtheque.modules.able;

import org.jtheque.modules.utils.I18NResource;
import org.jtheque.modules.utils.ImageResource;
import org.jtheque.resources.able.Resource;

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
 * The resources of the module. This is the resources declared in the XML file of the module.
 *
 * @author Baptiste Wicht
 */
public interface Resources {
    /**
     * Return all the resources of the modules.
     *
     * @return A List containing all the resources of the modules.
     */
    List<ImageResource> getImageResources();

    /**
     * Return all the i18n resources of the modules.
     *
     * @return A List containing all the i18n resources of the modules.
     */
    List<I18NResource> getI18NResources();

    /**
     * Return all the resources.
     *
     * @return A List containing all the resources. 
     */
    List<Resource> getResources();
}
