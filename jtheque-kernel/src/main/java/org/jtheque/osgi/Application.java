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

import org.jtheque.core.utils.SimplePropertiesCache;
import org.jtheque.osgi.server.OSGiServer;
import org.jtheque.ui.able.IView;
import org.jtheque.utils.io.FileUtils;

import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * The JTheque application. It can only be one Application in the computer at the same time.
 *
 * @author Baptiste Wicht
 */
final class Application extends Thread {
    private final Socket socket;
    private final OSGiServer server;

    private BufferedReader reader;

    /**
     * Construct a new Application.
     *
     * @param socket The socket to listen the data.
     * @param server The OSGi server.
     */
    Application(Socket socket, OSGiServer server) {
        super();

        this.socket = socket;
        this.server = server;

        try {
            reader = new BufferedReader(new InputStreamReader(new DataInputStream(socket.getInputStream())));
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    @Override
    public void run() {
        if (!socket.isClosed()) {
            try {
                String command = reader.readLine();

                if ("open".equals(command)) {
                    SimplePropertiesCache.<IView>get("mainView").toFirstPlan();
                } else if ("restart".equals(command)) {
                    server.restart();
                }

                interrupt();
            } catch (IOException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void interrupt() {
        FileUtils.close(reader);

        try {
            socket.close();
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }

        super.interrupt();
    }
}