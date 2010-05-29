package org.jtheque.osgi;

import org.jtheque.osgi.server.BundleState;
import org.jtheque.osgi.server.FelixServer;
import org.jtheque.osgi.server.OSGiServer;

import java.io.Closeable;

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

    /**
     * Launch the application.
     */
    public void launch() {
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

    /**
     * Launch the spring bundles.
     */
    private void launchSpring() {
        startIfNotStarted("org.springframework.osgi.extender");
        startIfNotStarted("org.springframework.osgi.core");
        startIfNotStarted("org.springframework.osgi.io");
    }

    /**
     * Launch the JTheque bundles.
     */
    private void launchJTheque() {
        startIfNotStarted("org.jtheque.spring.utils");
        startIfNotStarted("org.jtheque.states");
        startIfNotStarted("org.jtheque.events");
        startIfNotStarted("org.jtheque.resources");
        startIfNotStarted("org.jtheque.core");
        startIfNotStarted("org.jtheque.i18n");
        startIfNotStarted("org.jtheque.ui");
        startIfNotStarted("org.jtheque.errors");
        startIfNotStarted("org.jtheque.features");
        startIfNotStarted("org.jtheque.undo");
        startIfNotStarted("org.jtheque.schemas");
        startIfNotStarted("org.jtheque.persistence");
        startIfNotStarted("org.jtheque.modules");
        startIfNotStarted("org.jtheque.messages");
        startIfNotStarted("org.jtheque.file");
        startIfNotStarted("org.jtheque.collections");
        startIfNotStarted("org.jtheque.views");
        startIfNotStarted("org.jtheque.lifecycle");
    }

    /**
     * Start the specified bundle if it's not already started.
     *
     * @param name The name of the bundle to start.
     */
    private void startIfNotStarted(String name) {
        if (server.getState(name) != BundleState.ACTIVE) {
            server.startBundle(name);
        }
    }

    /**
     * A hook to stop the server.
     */
    private class StopServerHook extends Thread {
        @Override
        public void run() {
            close();
        }
    }

    /**
     * Launch the application. The application is read from the "application.xml" file at the same location than the
     * core.
     *
     * @param args No args will be read.
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            System.setProperty("user.dir", args[0]);
        }

        new CoreLauncher().launch();
    }
}