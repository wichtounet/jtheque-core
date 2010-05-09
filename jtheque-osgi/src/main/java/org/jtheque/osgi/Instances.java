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
final class Instances {
    private ServerSocket socket;
    private final Collection<Client> clients = new ArrayList<Client>(5);
    private Thread thread;

    private static final String LOCALHOST = "127.0.0.1";
    private static final int PORT = 12345;

    /**
     * Launch the application. If an other instance of the application is soon launched,
     * call it and exit the current instance.
     */
    public void launchApplication() {
        try {
            socket = new ServerSocket(PORT);

            registerNewClient();
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to register socket", e);

            exit();
        }
    }

    /**
     * Register a new client.
     */
    private void registerNewClient() {
        thread = new ServerThread();

        thread.start();
    }

    /**
     * Exit.
     */
    private void exit() {
        callInstance();

        System.exit(1); //At this moment, nothing need to be released
    }

    /**
     * Call the another instance of JTheque.
     */
    private void callInstance() {
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
        SocketUtils.close(socket);
    }

    /**
     * Stop all the clients.
     */
    private void stopClients() {
        for (Client client : clients) {
            client.interrupt();
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
    @SuppressWarnings({"SocketOpenedButNotSafelyClosed"})
    private final class ServerThread extends Thread {
        @Override
        public void run() {
            while (!socket.isClosed() && socket != null) {
                try {
                    Client client = new Client(socket.accept());
                    clients.add(client);

                    client.start();
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
            SocketUtils.close(socket);

            super.interrupt();
        }
    }
}