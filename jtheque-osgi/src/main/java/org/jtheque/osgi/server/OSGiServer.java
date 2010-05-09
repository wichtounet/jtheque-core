package org.jtheque.osgi.server;

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

import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * An OSGi server specification.
 *
 * @author Baptiste Wicht
 */
public interface OSGiServer {
    /**
     * Start the server.
     */
    void start();

    /**
     * Stop the server.
     */
    void stop();

    /**
     * Return all the installed bundles.
     *
     * @return An array containing all the installed bundles.
     */
    Bundle[] getBundles();

    /**
     * Install the specified bundle to the server.
     *
     * @param path The path to the bundle.
     */
    void installBundle(String path);

    /**
     * Start the bundle with the specified name.
     *
     * @param bundleName The name of the bundle.
     */
    void startBundle(String bundleName);

    /**
     * Stop the bundle with the specified name.
     *
     * @param bundleName The name of the bundle.
     */
    void stopBundle(String bundleName);

    /**
     * Uninstall the bundle with the specified name.
     *
     * @param bundleName The name of the bundle.
     */
    void uninstallBundle(String bundleName);

    /**
     * Return the state of the specified bundle.
     *
     * @param bundle The bundle to get the state for.
     *
     * @return The state of the bundle.
     */
    BundleState getState(Bundle bundle);

    BundleState getState(String bundle);

    boolean isInstalled(String bundleName);

    Version getVersion(String bundleName);
    Version getVersion(Bundle bundle);

    /**
     * Print debug informations about the current state of the server.
     */
    void debug();

    Bundle getBundle(String s);
}