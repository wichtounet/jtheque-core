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
    }

    @Override
    public void close() {
        instances.closeInstance();

        server.stop();
    }

    private void launchSpring() {
        startIfNotStarted("org.springframework.osgi.extender");
        startIfNotStarted("org.springframework.osgi.core");
        startIfNotStarted("org.springframework.osgi.io");
    }

    private void launchJTheque() {
        startIfNotStarted("org.jtheque.defaults");
        startIfNotStarted("org.jtheque.spring.utils");
        startIfNotStarted("org.jtheque.states");
        startIfNotStarted("org.jtheque.events");
        startIfNotStarted("org.jtheque.resources");
        startIfNotStarted("org.jtheque.core");
        startIfNotStarted("org.jtheque.i18n");
        startIfNotStarted("org.jtheque.ui");
        startIfNotStarted("org.jtheque.errors");
        startIfNotStarted("org.jtheque.messages");
        startIfNotStarted("org.jtheque.features");
        startIfNotStarted("org.jtheque.undo");
        startIfNotStarted("org.jtheque.schemas");
        startIfNotStarted("org.jtheque.persistence");
        startIfNotStarted("org.jtheque.modules");
        startIfNotStarted("org.jtheque.file");
        startIfNotStarted("org.jtheque.collections");
        startIfNotStarted("org.jtheque.views");
        startIfNotStarted("org.jtheque.lifecycle");
    }

    private void startIfNotStarted(String name) {
        if(server.getState(name) != BundleState.ACTIVE){
            server.startBundle(name);
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