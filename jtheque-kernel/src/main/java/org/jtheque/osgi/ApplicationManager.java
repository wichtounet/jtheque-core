package org.jtheque.osgi;

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

import org.jtheque.utils.io.FileUtils;
import org.jtheque.utils.io.SocketUtils;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class permit to have only one launched instance of the application.
 *
 * @author Baptiste Wicht
 */
final class ApplicationManager {
    private static final String LOCALHOST = "127.0.0.1";
    private static final int PORT = 12345;

    private final Collection<Application> applications = new ArrayList<Application>(2);
    private final Kernel kernel;

    private ServerSocket serverSocket;
    private Thread thread;

    /**
     * Construct a new ApplicationManager for the given OSGiServer.
     *
     * @param kernel The OSGi server.
     */
    ApplicationManager(Kernel kernel) {
        super();

        this.kernel = kernel;
    }

    /**
     * Launch the application. If an other instance of the application is soon launched, call it and exit the current
     * instance.
     */
    public void tryLaunchApplication() {
        try {
            serverSocket = new ServerSocket(PORT);

            registerApplication();
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).debug("The application is already launched");

            wakeUpApplication();

            System.exit(1); //At this moment, nothing need to be released
        }
    }

    /**
     * Register the application. .
     */
    private void registerApplication() {
        thread = new ServerThread();
        thread.start();
    }

    /**
     * Call the another instance of JTheque.
     */
    private void wakeUpApplication() {
        Socket clientSocket = null;
        PrintStream stream = null;

        try {
            clientSocket = new Socket(LOCALHOST, PORT);
            stream = new PrintStream(clientSocket.getOutputStream());

            stream.println("open");
        } catch (UnknownHostException e) {
            LoggerFactory.getLogger(getClass()).error("The host is unknown", e);
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error("I/O error", e);
        } finally {
            FileUtils.close(stream);
            SocketUtils.close(clientSocket);
        }
    }

    /**
     * Close definitively all the open flows.
     */
    public void closeInstance() {
        stopClients();
        stopServer();
        SocketUtils.close(serverSocket);
    }

    /**
     * Stop all the applications.
     */
    private void stopClients() {
        for (Application application : applications) {
            application.interrupt();
        }
    }

    /**
     * Stop the server.
     */
    private void stopServer() {
        if (thread != null) {
            thread.interrupt();
        }
    }

    /**
     * A thread for a client.
     *
     * @author Baptiste Wicht
     */
    private final class ServerThread extends Thread {
        @Override
        public void run() {
            while (!serverSocket.isClosed() && serverSocket != null) {
                try {


                    Application application = new Application(serverSocket.accept(), kernel);
                    applications.add(application);

                    application.start();
                } catch (SocketException e1) {
                    LoggerFactory.getLogger(getClass()).debug("The socket has been closed. Normally no problems. ");
                } catch (IOException e2) {
                    LoggerFactory.getLogger(getClass()).error(e2.getMessage(), e2);
                    interrupt();
                }
            }
        }

        @Override
        public void interrupt() {
            SocketUtils.close(serverSocket);

            super.interrupt();
        }
    }
}