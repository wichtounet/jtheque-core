package org.jtheque.modules.able;

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
import org.osgi.framework.Bundle;

public interface Module {
    /**
     * Return the real module.
     *
     * @return The real module.
     */
    Bundle getModule();

    /**
     * Return the current state of the module.
     *
     * @return The current state of the module.
     */
    ModuleState getState();

    /**
     * Return the id of the module.
     *
     * @return The id of the module.
     */
    String getId();

    /**
     * Return the name key of the module.
     *
     * @return The name key of the module.
     */
    String getName();

    /**
     * Return the author key of the module.
     *
     * @return The author key of the module.
     */
    String getAuthor();

    /**
     * Return the key description of the module.
     *
     * @return The key description of the module.
     */
    String getDescription();

    Version getVersion();

    Version getCoreVersion();

    String getUrl();

    String getUpdateUrl();

    String[] getBundles();

    String[] getDependencies();

    String getMessagesUrl();

    void setCollection(boolean collection);

    boolean isCollection();
    
    /**
     * Set the state of the module.
     *
     * @param state The state of the module.
     */
    void setState(ModuleState state);
}
