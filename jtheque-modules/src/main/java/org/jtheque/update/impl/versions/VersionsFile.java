package org.jtheque.update.impl.versions;

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

import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An online version's file.
 *
 * @author Baptiste Wicht
 */
public final class VersionsFile {
    private List<OnlineVersion> onlineVersions;
    private InstallVersion installVersion;

    /**
     * Return all the versions contained in the file.
     *
     * @return A list containing all the versions of the file.
     */
    public Collection<OnlineVersion> getVersions() {
        return onlineVersions;
    }

    /**
     * Set the versions of the file.
     *
     * @param onlineVersions A list containing all the versions of the file.
     */
    public void setVersions(Collection<OnlineVersion> onlineVersions) {
        this.onlineVersions = CollectionUtils.copyOf(onlineVersions);
    }

    /**
     * Return the install version of the file.
     *
     * @return The install version.
     */
    public InstallVersion getInstallVersion() {
        return installVersion;
    }

    /**
     * Set the install version.
     *
     * @param installVersion The install version.
     */
    public void setInstallVersion(InstallVersion installVersion) {
        this.installVersion = installVersion;
    }

    /**
     * Return the most recent version of the VersionsFile.
     *
     * @return The most recent version.
     * @throws NoSuchElementException If the version's file contains no version.
     */
    public OnlineVersion getMostRecentVersion() {
        if (onlineVersions.isEmpty()) {
            throw new NoSuchElementException("The version's file contains no versions. ");
        }

        return onlineVersions.get(onlineVersions.size() - 1);
    }
}