package org.jtheque.osgi;

import org.jtheque.osgi.server.BundleState;
import org.jtheque.osgi.server.FelixServer;
import org.jtheque.osgi.server.OSGiServer;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.SystemProperty;
import org.jtheque.utils.io.FileUtils;

import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.File;
import java.util.Collection;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

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
 * The kernel for JTheque Core.
 *
 * @author Baptiste Wicht
 */
public final class Kernel implements Closeable {
    private final ApplicationManager applicationManager;
    private final OSGiServer server;

    /**
     * Utility class, not instanciable.
     */
    private Kernel() {
        super();

        server = new FelixServer();
        applicationManager = new ApplicationManager();
    }

    /**
     * Start the kernel.
     */
    private void start() {
        applicationManager.tryLaunchApplication();

        server.start();

        Runtime.getRuntime().addShutdownHook(new StopServerHook());

        startBundles();
    }

    /**
     * Start all the bundles. The bundles are started in the order of the "start" file.
     */
    private void startBundles() {
        Collection<String> bundles = FileUtils.getLinesOf(new File(SystemProperty.USER_DIR.get() + "core/bundles", "start"));

        for(String bundle : bundles){
            startIfNotStarted(bundle);
        }
    }

    @Override
    public void close() {
        applicationManager.closeInstance();

        server.stop();
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
    private final class StopServerHook extends Thread {
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
            SystemProperty.USER_DIR.set(args[0]);
        }

        configureLogging();
        
        Thread.currentThread().setName("JTheque-MainThread");

        new Kernel().start();
    }

    /**
     * Configure the logging.
     */
    private static void configureLogging() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger("root");

        if (StringUtils.isNotEmpty(System.getProperty("jtheque.log"))) {
            rootLogger.setLevel(Level.toLevel(System.getProperty("jtheque.log")));
        }
    }
}