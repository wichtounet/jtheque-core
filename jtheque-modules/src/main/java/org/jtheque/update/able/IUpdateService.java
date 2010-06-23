package org.jtheque.update.able;

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
import org.jtheque.modules.impl.InstallationResult;
import org.jtheque.utils.bean.Version;

import java.util.Collection;
import java.util.List;

/**
 * An org.jtheque.update manager specification.
 *
 * @author Baptiste Wicht
 */
public interface IUpdateService {
    /**
     * Update JTheque. This method search on internet the datas of the version we want to download and download all the
     * files useful and org.jtheque.update the local files. Last, we reboot the program.
     *
     * @param versionToDownload The version we want to download
     */
    void updateCore(Version versionToDownload);

    /**
     * Update the module.
     *
     * @param object  The object to update.
     * @param version The current version.
     */
    void update(Versionable object, Version version);

    /**
     * Return the list of available versions on internet.
     *
     * @return The available versions
     */
    Collection<Version> getKernelVersions();

    /**
     * Verify if there is a new update available and if the user want to org.jtheque.update the application.
     *
     * @return An empty list if there were no updates therefore a list containing i18n messages to display about the
     *         state of updates.
     */
    List<String> getPossibleUpdates();

    /**
     * Indicate if the current version is the last version.
     *
     * @return true if we've the last version, else false
     */
    boolean isCurrentVersionUpToDate();

    /**
     * Test if a object is up to date or if there is a most recent version on org.jtheque.update site.
     *
     * @param object The object to test.
     *
     * @return true if the module is up to date else false.
     */
    boolean isUpToDate(Versionable object);

    /**
     * Return all the versions of the object.
     *
     * @param object The object to get the versions for.
     *
     * @return A List containing all the versions of the updatable.
     */
    Collection<Version> getVersions(Versionable object);

    /**
     * Install a module from a versions file.
     *
     * @param versionFileURL The URL to the version file.
     *
     * @return The result of the installation.
     */
    InstallationResult install(String versionFileURL);

    /**
     * Update the module to the most recent available version.
     *
     * @param module The module to org.jtheque.update.
     */
    void updateToMostRecentVersion(Module module);

    /**
     * Return the most recent version of the object.
     *
     * @param object The object. It can be the Core, a module or an updatable.
     *
     * @return The most recent version of the object or null if the resource is not reachable over the internet
     */
    Version getMostRecentVersion(Versionable object);

    /**
     * Return the most recent version of the core.
     *
     * @return The most recent version of the core.
     */
    Version getMostRecentCoreVersion();
}