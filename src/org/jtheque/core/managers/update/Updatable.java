package org.jtheque.core.managers.update;

import org.jtheque.utils.bean.Version;

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

/**
 * An updatable. It seems an objet who can be updated externally.
 *
 * @author Baptiste Wicht
 */
public interface Updatable {
    /**
     * Return the name of the updatable.
     *
     * @return The name of the updatable.
     */
    String getName();

    /**
     * Return the internationalization key of the updatable.
     *
     * @return The internationalization key of the updatable.
     */
    String getKey();

    /**
     * Return the version of the updatable.
     *
     * @return The version  of the updatable.
     */
    Version getVersion();

    /**
     * Return the default version of the updatable.
     *
     * @return The default version of the updatable.
     */
    Version getDefaultVersion();

    /**
     * Set the version of the updatable.
     *
     * @param version The version of the updatable.
     */
    void setVersion(Version version);

    /**
     * Return the URL of the versions file.
     *
     * @return the URL of the versions file.
     */
    String getVersionsFileURL();

    /**
     * Add an update listener to the updatable.
     *
     * @param listener The listener to add.
     */
    void addUpdateListener(UpdateListener listener);

    /**
     * Remove an update listener to the updatable.
     *
     * @param listener The listener to remove.
     */
    void removeUpdateListener(UpdateListener listener);

    /**
     * Set the updatable as updated.
     */
    void setUpdated();
}