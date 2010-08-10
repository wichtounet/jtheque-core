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

import org.jtheque.core.able.Versionable;
import org.jtheque.utils.bean.Version;

import org.osgi.framework.Bundle;

/**
 * A module specification.
 *
 * @author Baptiste Wicht
 */
public interface Module extends Versionable {
    /**
     * Return the real module.
     *
     * @return The real module.
     */
    Bundle getBundle();

    /**
     * Return the current state of the module.
     *
     * @return The current state of the module.
     */
    ModuleState getState();

    /**
     * Set the state of the module.
     *
     * @param state The state of the module.
     */
    void setState(ModuleState state);

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

    /**
     * Return the needed version of the core.
     *
     * @return The needed version of the core.
     */
    Version getCoreVersion();

    /**
     * Return the URL of the website of the module.
     *
     * @return The URL of the website of the module.
     */
    String getUrl();

    /**
     * Return all the dependencies of the module. The dependencies are a link to a module that must be started before
     * this module.
     *
     * @return An array containing all the module dependencies of the module.
     */
    String[] getDependencies();

    /**
     * Return the URL of the messages file of the module.
     *
     * @return The URL of the messages file of the module.
     */
    String getMessagesUrl();

    /**
     * Indicate if the module needs a collection.
     *
     * @return true if the module needs a collection otherwise false.
     */
    boolean isCollection();

    /**
     * Return the internationalized state.
     *
     * @return The internationalized state.
     */
    String getDisplayState();
}
