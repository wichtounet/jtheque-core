package org.jtheque.update.able;

import org.jtheque.utils.bean.Version;

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
     * Add an org.jtheque.update listener to the updatable.
     *
     * @param listener The listener to add.
     */
    void addUpdateListener(UpdateListener listener);

    /**
     * Remove an org.jtheque.update listener to the updatable.
     *
     * @param listener The listener to remove.
     */
    void removeUpdateListener(UpdateListener listener);

    /**
     * Set the updatable as updated.
     */
    void setUpdated();
}