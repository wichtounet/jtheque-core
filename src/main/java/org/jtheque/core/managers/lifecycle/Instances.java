package org.jtheque.core.managers.lifecycle;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.ILoggingManager;
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
    @SuppressWarnings({"SocketOpenedButNotSafelyClosed"})
    public void launchApplication() {
        boolean canLaunch = true;

        try {
            socket = new ServerSocket(PORT);

            registerNewClient();
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to register socket", e);

            canLaunch = false;
        }

        if (!canLaunch) {
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
    @SuppressWarnings({"SocketOpenedButNotSafelyClosed", "IOResourceOpenedButNotSafelyClosed"})
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
        private boolean closing;

        @Override
        public void run() {
            while (!socket.isClosed() && socket != null) {
                try {
                    Client client = new Client(socket.accept());
                    clients.add(client);

                    client.start();
                } catch (SocketException e1) {
                    if (!closing) {
                        Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e1, "Socket closed");
                    }
                } catch (IOException e2) {
                    Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e2);
                    interrupt();
                }
            }
        }

        @Override
        public void interrupt() {
            closing = true;

            SocketUtils.close(socket);

            super.interrupt();
        }
    }
}