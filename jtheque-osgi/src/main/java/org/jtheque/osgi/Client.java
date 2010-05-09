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
import org.jtheque.ui.able.IView;
import org.jtheque.utils.io.FileUtils;
import org.slf4j.LoggerFactory;

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
    Client(Socket socket) {
        super();

        this.socket = socket;

        try {
            reader = new BufferedReader(new InputStreamReader(new DataInputStream(socket.getInputStream())));
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                if ("open".equals(reader.readLine())) {
                    SimplePropertiesCache.<IView>get("mainView").toFirstPlan();

                    interrupt();
                }
            } catch (IOException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
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
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }
}