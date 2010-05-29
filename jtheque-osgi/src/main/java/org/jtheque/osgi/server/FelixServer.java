package org.jtheque.osgi.server;

import org.apache.felix.framework.Felix;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
 * A Felix OSGI server implementation.
 *
 * @author Baptiste Wicht
 */
public class FelixServer implements OSGiServer {
    private Felix felix;

    private final Map<String, Bundle> bundles = new HashMap<String, Bundle>(10);

    @Override
    public void start() {
        getLogger().debug("Starting Felix Server");

        long startTime = System.currentTimeMillis();

        Map<String, Object> configMap = new HashMap<String, Object>(5);

        configMap.put("felix.cache.bufsize", "8192");
        configMap.put("org.osgi.framework.storage", System.getProperty("user.dir") + "/cache");

        try {
            felix = new Felix(configMap);
            felix.start();
        } catch (Exception e) {
            getLogger().error("Unable to start Felix due to {}", e.getMessage());
            getLogger().error(e.getMessage(), e);
        }

        getLogger().debug("Felix Server started in {} ms", System.currentTimeMillis() - startTime);

        autoDeploy();

        for (Bundle bundle : felix.getBundleContext().getBundles()) {
            bundles.put(bundle.getSymbolicName(), bundle);
        }

        getLogger().info("Felix started with {} cached bundles : {}", bundles.size(), bundles);
    }

    /**
     * Auto deploy the bundles in the "bundles" folder of the user dir.
     */
    private void autoDeploy() {
        File deployDir = new File(System.getProperty("user.dir") + "/bundles");

        getLogger().info("Auto deploy start");

        for (File f : deployDir.listFiles(new JarFileFilter())) {
            String name = f.getName();

            String symbolicName = name.substring(0, name.indexOf('-'));

            getLogger().info("Auto deploy : " + symbolicName);

            installIfNecessary(symbolicName, f.getAbsolutePath());
        }

        getLogger().info("Auto deploy ends");
    }

    @Override
    public void stop() {
        try {
            try {
                for (Bundle bundle : bundles.values()) {
                    if (!"org.apache.felix.framework".equals(bundle.getSymbolicName())) {
                        bundle.stop();
                    }
                }
            } catch (BundleException e) {
                LoggerFactory.getLogger(getClass()).error("Cannot stop System Bundle");
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }

            felix.stop();
            felix.waitForStop(1000);

            getLogger().info("Felix stopped");
        } catch (BundleException e) {
            getLogger().error("Unable to stop Felix due to {}", e.getMessage());
            getLogger().error(e.getMessage(), e);
        } catch (InterruptedException e) {
            getLogger().error("Unable to stop Felix due to {}", e.getMessage());
            getLogger().error(e.getMessage(), e);
        }
    }

    @Override
    public Bundle[] getBundles() {
        return felix.getBundleContext().getBundles();
    }

    @Override
    public void installBundle(String path) {
        try {
            String url = path.startsWith("file:") ? path : "file:" + path;

            Bundle bundle = felix.getBundleContext().installBundle(url);

            bundles.put(bundle.getSymbolicName(), bundle);

            getLogger().info("Installed bundle {}", bundle.getSymbolicName());
        } catch (BundleException e) {
            getLogger().error("Unable to install bundle at path {} due to {}", path, e.getMessage());
            getLogger().error(e.getMessage(), e);
        }
    }

    @Override
    public void startBundle(String bundleName) {
        Bundle bundle = getBundle(bundleName);

        if (bundle != null) {
            try {
                getLogger().info("Start bundle {}", bundle.getSymbolicName());

                bundle.start();

                getLogger().info("Started bundle {}", bundle.getSymbolicName());
            } catch (BundleException e) {
                getLogger().error("Unable to start bundle ({}) due to {}", bundleName, e.getMessage());
                getLogger().error(e.getMessage(), e);

                debug();
            }
        } else {
            getLogger().error("Unable to start bundle ({}) because it's not installed", bundleName);
        }
    }

    @Override
    public void stopBundle(String bundleName) {
        Bundle bundle = getBundle(bundleName);

        if (bundle != null) {
            try {
                bundle.stop();

                getLogger().info("Stopped bundle {}", bundle.getSymbolicName());
            } catch (BundleException e) {
                getLogger().error("Unable to stop bundle ({}) due to {}", bundleName, e.getMessage());
                getLogger().error(e.getMessage(), e);

                debug();
            }
        } else {
            getLogger().error("Unable to stop bundle ({}) because it's not installed", bundleName);
        }
    }

    @Override
    public void uninstallBundle(String bundleName) {
        Bundle bundle = getBundle(bundleName);

        if (bundle != null) {
            try {
                bundle.uninstall();

                getLogger().info("Uninstall bundle {}", bundle.getSymbolicName());
            } catch (BundleException e) {
                getLogger().error("Unable to uninstall bundle ({}) due to {}", bundleName, e.getMessage());
                getLogger().error(e.getMessage(), e);
            }
        } else {
            getLogger().error("Unable to uninstall bundle ({}) because it's not installed", bundleName);
        }
    }

    @Override
    public Version getVersion(String bundleName) {
        return getVersion(getBundle(bundleName));
    }

    @Override
    public Version getVersion(Bundle bundle) {
        if (bundle != null) {
            return bundle.getVersion();
        }

        return null;
    }

    @Override
    public void debug() {
        getLogger().debug("Installed bundles : ");

        for (Bundle bundle : getBundles()) {
            getLogger().debug("{} ({}) : {}", new Object[]{bundle.getSymbolicName(), getVersion(bundle).toString(), getState(bundle)});
            getLogger().debug("Exported packages : {}", bundle.getHeaders().get("Export-Package"));
            getLogger().debug("Imported packages : {}", bundle.getHeaders().get("Import-Package"));
            getLogger().debug("Registered services: {}", Arrays.toString(bundle.getRegisteredServices()));
        }
    }

    @Override
    public BundleState getState(String bundle) {
        if (!isInstalled(bundle)) {
            return BundleState.NOTINSTALLED;
        }

        return getState(getBundle(bundle));
    }

    @Override
    public BundleState getState(Bundle bundle) {
        int state = bundle.getState();

        switch (state) {
            case 1:
                return BundleState.UNINSTALLED;
            case 2:
                return BundleState.INSTALLED;
            case 4:
                return BundleState.RESOLVED;
            case 8:
                return BundleState.STARTING;
            case 16:
                return BundleState.STOPPING;
            case 32:
                return BundleState.ACTIVE;
            default:
                getLogger().error("Undefined bundle state bundle : {}, state : {}", bundle.getSymbolicName(), state);
                return null;
        }
    }

    /**
     * Install the module with the given name if it's not installed.
     *
     * @param name The module name.
     * @param path The path to the module file.
     */
    private void installIfNecessary(String name, String path) {
        if (!isInstalled(name)) {
            installBundle(path);
        }
    }

    @Override
    public boolean isInstalled(String bundleName) {
        return getBundle(bundleName) != null;
    }

    @Override
    public Bundle getBundle(String bundleName) {
        return bundles.get(bundleName);
    }

    /**
     * Return the logger of the server.
     *
     * @return The logger of the server.
     */
    private Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }

    /**
     * A file name filter to keep only JAR files.
     *
     * @author Baptiste Wicht
     */
    private static class JarFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".jar");
        }
    }
}