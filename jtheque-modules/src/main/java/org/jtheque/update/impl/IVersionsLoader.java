package org.jtheque.update.impl;

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
import org.jtheque.modules.able.Module;
import org.jtheque.resources.impl.CoreVersion;
import org.jtheque.resources.impl.ModuleVersion;
import org.jtheque.utils.bean.Version;

import java.util.Collection;

/**
 * A version loader specification.
 *
 * @author Baptiste Wicht
 */
public interface IVersionsLoader {
    /**
     * Return all the versions of the object.
     *
     * @param object The object to get the versions from.
     *
     * @return A List containing all the versions from.
     */
    Collection<Version> getVersions(Module object);

    /**
     * Return the online versions of the object.
     *
     * @param object The object to get the version from.
     *
     * @return A List containing all the online versions of the object.
     */
    Collection<ModuleVersion> getOnlineVersions(Module object);

    /**
     * Return the online version corresponding to the specified version.
     *
     * @param version The version to search for.
     * @param object  The object to search in.
     *
     * @return The corresponding online version.
     */
    ModuleVersion getModuleVersion(Version version, Module object);

    /**
     * Return the most recent version of the object.
     *
     * @param object The object. It can be the Core, a module or an updatable.
     *
     * @return The most recent version of the object.
     */
    Version getMostRecentVersion(Versionable object);

    /**
     * Return the most recent version of the module at the given url.
     *
     * @param url The url of the descriptor.
     *
     * @return The most recent module version of the descriptor.
     */
    ModuleVersion getMostRecentModuleVersion(String url);

    /**
     * Return the core version corresponding to the given version.
     *
     * @param version The version.
     *
     * @return The core version of this version if there is one otherwise {@code null}. 
     */
    CoreVersion getCoreVersion(Version version);

    /**
     * Return all the core versions.
     *
     * @return A Collection containing all the core versions.
     */
    Collection<Version> getCoreVersions();

    /**
     * Return the most recent core version.
     *
     * @return The most recent core version. 
     */
    Version getMostRecentCoreVersion();
}
