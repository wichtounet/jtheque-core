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

    /**
     * Return the state of the given bundle.
     *
     * @param bundleName The bundle to get the state for.
     *
     * @return The state of the bundle.
     */
    BundleState getState(String bundleName);

    /**
     * Indicate if a bundle is installed.
     *
     * @param bundleName The name of the bundle.
     *
     * @return true if the bundle is installed else false.
     */
    boolean isInstalled(String bundleName);

    /**
     * Return the version of the given bundle.
     *
     * @param bundleName The version to get the state for.
     *
     * @return The version of the bundle.
     */
    Version getVersion(String bundleName);

    /**
     * Return the version of the bundle.
     *
     * @param bundle The bundle to get the version of.
     *
     * @return The version of the bundle.
     */
    Version getVersion(Bundle bundle);

    /**
     * Return the bundle of the specified name.
     *
     * @param bundleName The name
     *
     * @return The bundle with the specified name.
     */
    Bundle getBundle(String bundleName);

    /**
     * Restart the server without the bundle cache.
     */
    void restart();
}