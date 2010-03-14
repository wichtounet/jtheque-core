package org.jtheque.osgi.server;

import org.apache.felix.framework.Felix;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

public class FelixServer implements OSGiServer {
    private Felix felix;

    private final Map<String, Bundle> bundles = new HashMap<String, Bundle>(10);

    @Override
    public void start(){
        Map<String, Object> configMap = new HashMap<String, Object>(2);

        configMap.put("felix.cache.bufsize", "8192");
        configMap.put("org.osgi.framework.storage", System.getProperty("user.dir") + "/bundles/cache/");

        try{
            felix = new Felix(configMap);
            felix.start();
        } catch (Exception e){
            getLogger().error("Unable to start Felix due to {}", e.getMessage());
            getLogger().error(e.getMessage(), e);
        }

        getBundle("org.apache.felix.framework").getBundleContext().registerService(OSGiServer.class.getName(), this, null);

        for(Bundle bundle : felix.getBundleContext().getBundles()){
            bundles.put(bundle.getSymbolicName(), bundle);
        }

        getLogger().info("Felix started with {} cached bundles : {}", bundles.size(), bundles);
    }

    @Override
    public void stop(){
        try {
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
    public Bundle[] getBundles(){
        return felix.getBundleContext().getBundles();
    }

    @Override
    public void installBundle(String path){
        try {
            String url = path.startsWith("file:") ? path : "file:" + path;

            Bundle bundle = felix.getBundleContext().installBundle(url);

            bundles.put(bundle.getSymbolicName(), bundle);

            getLogger().info("Installed bundle {}", bundle.getSymbolicName());
        } catch (BundleException e) {
            getLogger().error("Unable to install bundle at path {} due to {}", path,  e.getMessage());
            getLogger().error(e.getMessage(), e);
        }
    }

    @Override
    public void startBundle(String bundleName) {
        Bundle bundle = getBundle(bundleName);

        if(bundle != null){
            try {
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

        if(bundle != null){
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

        if(bundle != null){
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
        if(bundle != null){
            return bundle.getVersion();
        }

        return null;
    }

    @Override
    public void debug() {
        getLogger().debug("Installed bundles : ");

        for(Bundle bundle : getBundles()){
            getLogger().debug("{} ({}) : {}", new Object[]{bundle.getSymbolicName(), getVersion(bundle).toString(), getState(bundle)});
            getLogger().debug("Exported packages : " + bundle.getHeaders().get("Export-Package"));
            getLogger().debug("Imported packages : " + bundle.getHeaders().get("Import-Package"));
            getLogger().debug("Registered services: " + Arrays.toString(bundle.getRegisteredServices()));
        }
    }

    @Override
    public BundleState getState(String bundle) {
        if(!isInstalled(bundle)){
            return BundleState.NOTINSTALLED;
        }

        return getState(getBundle(bundle));
    }

    @Override
    public BundleState getState(Bundle bundle){
        int state = bundle.getState();

        switch (state){
            case 1 : return BundleState.UNINSTALLED;
            case 2 : return BundleState.INSTALLED;
            case 4 : return BundleState.RESOLVED;
            case 8 : return BundleState.STARTING;
            case 16 : return BundleState.STOPPING;
            case 32 : return BundleState.ACTIVE;
            default :
                getLogger().error("Undefined bundle state bundle : {}, state : {}", bundle.getSymbolicName(), state);
                return null;
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

    private Logger getLogger(){
        return LoggerFactory.getLogger(getClass());
    }
}