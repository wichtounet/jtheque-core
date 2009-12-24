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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.utils.io.FileUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Represent a network client. It's an instance of JTheque who listen to new open of the program.
 *
 * @author Baptiste Wicht
 */
final class Client extends Thread {
    private BufferedReader reader;
    private final Socket socket;

    /**
     * Construct a new Client.
     *
     * @param socket The socket to listen the data.
     */
    @SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
    Client(Socket socket) {
        super();

        this.socket = socket;

        try {
            reader = new BufferedReader(new InputStreamReader(new DataInputStream(socket.getInputStream())));
        } catch (IOException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                if ("open".equals(reader.readLine())) {
                    Managers.getManager(IViewManager.class).getViews().getMainView().toFirstPlan();

                    interrupt();
                }
            } catch (IOException e) {
                Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();

        FileUtils.close(reader);

        try {
            socket.close();
        } catch (IOException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }
    }
}