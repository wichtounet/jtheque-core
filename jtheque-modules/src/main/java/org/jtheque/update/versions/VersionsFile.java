package org.jtheque.update.versions;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
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