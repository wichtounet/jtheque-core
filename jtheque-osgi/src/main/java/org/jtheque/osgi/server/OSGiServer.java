package org.jtheque.osgi.server;

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