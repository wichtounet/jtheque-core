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

import org.jtheque.utils.bean.Version;

import java.util.Collection;

/**
 * A version loader specification.
 *
 * @author Baptiste Wicht
 */
public interface IVersionsLoader {
    /**
     * Return the version of the object.
     *
     * @param object The object to get the version from.
     * @return The version of the object.
     */
    Version getVersion(Object object);

    /**
     * Return all the versions of the object.
     *
     * @param object The object to get the versions from.
     * @return A List containing all the versions from.
     */
    Collection<Version> getVersions(Object object);

    /**
     * Return the online versions of the object.
     *
     * @param object The object to get the version from.
     * @return A List containing all the online versions of the object.
     */
    Collection<OnlineVersion> getOnlineVersions(Object object);

    /**
     * Return the online version corresponding to the specified version.
     *
     * @param version The version to search for.
     * @param object  The object to search in.
     * @return The corresponding online version.
     */
    OnlineVersion getOnlineVersion(Version version, Object object);

    /**
     * Return the install version of the version file.
     *
     * @param versionFileURL The VersionFile URL.
     * @return The install version of the versions file.
     */
    InstallVersion getInstallVersion(String versionFileURL);

    /**
     * Return the most recent version of the object.
     *
     * @param object The object. It can be the Core, a module or an updatable.
     * @return The most recent version of the object.
     */
    Version getMostRecentVersion(Object object);
}
