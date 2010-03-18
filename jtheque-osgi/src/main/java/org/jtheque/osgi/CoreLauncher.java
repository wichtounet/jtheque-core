package org.jtheque.osgi;

import org.jtheque.osgi.server.BundleState;
import org.jtheque.osgi.server.FelixServer;
import org.jtheque.osgi.server.OSGiServer;

import java.io.Closeable;

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
 * A simple launcher for XML applications.
 *
 * @author Baptiste Wicht
 */
public final class CoreLauncher implements Closeable {
    private final OSGiServer server;

    private final Instances instances;

    /**
     * Utility class, not instanciable. 
     */
    private CoreLauncher() {
        super();

        instances = new Instances();
        server = new FelixServer();
    }

    public void launch(){
        instances.launchApplication();

        server.start();

        Runtime.getRuntime().addShutdownHook(new StopServerHook());

        launchSpring();
        launchJTheque();

        server.debug();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        server.stop();
    }

    @Override
    public void close() {
        instances.closeInstance();
        server.stop();
    }

    private void launchSpring() {
        //Spring OSGi bundles
        installIfNecessary("org.springframework.osgi.extender", "/bundles/spring-osgi-extender-1.2.1.jar");
        installIfNecessary("org.springframework.osgi.core", "/bundles/spring-osgi-core-1.2.1.jar");
        installIfNecessary("org.springframework.osgi.io", "/bundles/spring-osgi-io-1.2.1.jar");

        //Spring bundles
        installIfNecessary("org.springframework.core", "/bundles/spring-core-3.0.0.jar");
        installIfNecessary("org.springframework.context", "/bundles/spring-context-3.0.0.jar");
        installIfNecessary("org.springframework.beans", "/bundles/spring-beans-3.0.0.jar");
        installIfNecessary("org.springframework.aop", "/bundles/spring-aop-3.0.0.jar");
        installIfNecessary("org.springframework.asm", "/bundles/spring-asm-3.0.0.jar");
        installIfNecessary("org.springframework.expression", "/bundles/spring-expression-3.0.0.jar");

        //Logging bundles
        installIfNecessary("ch.qos.logback.core", "/bundles/logback-core-0.9.18.jar");
        installIfNecessary("ch.qos.logback.classic", "/bundles/logback-classic-0.9.18.jar");
        installIfNecessary("com.springsource.slf4j.api", "/bundles/com.springsource.slf4j.api-1.5.6.jar");
        installIfNecessary("com.springsource.slf4j.org.apache.commons.logging",
                "/bundles/com.springsource.slf4j.org.apache.commons.logging-1.5.6.jar");

        //Others bundles
        installIfNecessary("com.springsource.net.sf.cglib", "/bundles/com.springsource.net.sf.cglib-2.1.3.jar");
        installIfNecessary("com.springsource.org.aopalliance", "/bundles/com.springsource.org.aopalliance-1.0.0.jar");

        startIfNotStarted("org.springframework.osgi.extender");
        startIfNotStarted("org.springframework.osgi.core");
        startIfNotStarted("org.springframework.osgi.io");
    }

    private void launchJTheque() {
        installIfNecessary("jtheque-utils", "/bundles/jtheque-utils-1.1.4-SNAPSHOT.jar");
        installIfNecessary("jtheque-core", "/bundles/jtheque-core-2.1-SNAPSHOT.jar");
        installIfNecessary("jtheque-lifecycle", "/bundles/jtheque-lifecycle-2.1-SNAPSHOT.jar");
        
        startIfNotStarted("jtheque-lifecycle");
    }

    private void startIfNotStarted(String name) {
        if(server.getState(name) != BundleState.ACTIVE){
            server.startBundle(name);
        }
    }

    private void installIfNecessary(String name, String path) {
        if(!server.isInstalled(name)){
            server.installBundle(System.getProperty("user.dir") + path);
        }
    }

    private class StopServerHook extends Thread {
        @Override
        public void run(){
            close();
        }
    }

    /**
     * Launch the application. The application is read from the "application.xml" file at the same location than the
     * core.
     *
     * @param args No args will be read.
     */
    public static void main(String[] args){
        if(args.length > 0){
            System.setProperty("user.dir", args[0]);
        }

        new CoreLauncher().launch();
    }
}