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

import org.jtheque.update.actions.UpdateAction;
import org.jtheque.utils.Constants;
import org.jtheque.utils.bean.EqualsUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.Collection;

/**
 * An online version of JTheque.
 *
 * @author Baptiste Wicht
 */
public class OnlineVersion implements Comparable<OnlineVersion> {
    private Version version;
    private Version coreVersion;
    private Collection<UpdateAction> actions;

    /**
     * Return the JTheque's version.
     *
     * @return The JTheque's version.
     */
    public final Version getVersion() {
        return version;
    }

    /**
     * Set the JTheque's version.
     *
     * @param version The JTheque's version.
     */
    public final void setVersion(Version version) {
        this.version = version;
    }

    /**
     * Return the actions to be executed when updating to this version.
     *
     * @return A list containing all the actions.
     */
    public final Collection<UpdateAction> getActions() {
        return actions;
    }

    /**
     * Set the actions to be executed when updating to this version.
     *
     * @param actions A list containing all the actions.
     */
    public final void setActions(Collection<UpdateAction> actions) {
        this.actions = CollectionUtils.copyOf(actions);
    }

    /**
     * Return the version transformed to string.
     *
     * @return The string version.
     */
    public final String getStringVersion() {
        return version.getVersion();
    }

    @Override
    public final int compareTo(OnlineVersion o) {
        return version.compareTo(o.version);
    }

    /**
     * Return the necessary version of the core.
     *
     * @return The needed core version.
     */
    public final Version getCoreVersion() {
        return coreVersion;
    }

    /**
     * Set the version of the core for this version.
     *
     * @param coreVersion The needed version of the core for this module.
     */
    public final void setCoreVersion(Version coreVersion) {
        this.coreVersion = coreVersion;
    }

    @Override
    public final boolean equals(Object o) {
        if (EqualsUtils.areObjectIncompatible(this, o)) {
            return false;
        }

        OnlineVersion that = (OnlineVersion) o;

        if (EqualsUtils.areNotEquals(actions, that.actions)) {
            return false;
        }

        if (EqualsUtils.areNotEquals(coreVersion, that.coreVersion)) {
            return false;
        }

        return !EqualsUtils.areNotEquals(version, that.version);
    }

    @Override
    public final int hashCode() {
        int result = Constants.HASH_CODE_START;

        result = Constants.HASH_CODE_PRIME * result + (version == null ? 0 : version.hashCode());
        result = Constants.HASH_CODE_PRIME * result + (coreVersion == null ? 0 : coreVersion.hashCode());
        result = Constants.HASH_CODE_PRIME * result + (actions == null ? 0 : actions.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return "OnlineVersion{" +
                "version=" + version +
                ", core=" + coreVersion +
                ", actions=" + actions +
                '}';
    }
}