package org.jtheque.core.impl;

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

import org.jtheque.core.able.*;
import org.jtheque.core.able.Core;

import java.io.File;

/**
 * Give access to the files of the application.
 *
 * @author Baptiste Wicht
 */
public final class Files implements FilesContainer {
    private final Core core;

    /**
     * Construct a new Files.
     *
     * @param core The core.
     */
    public Files(org.jtheque.core.able.Core core) {
        super();

        this.core = core;
    }

    @Override
    public File getLauncherFile() {
        return new File(getLauncherPath());
    }

    /**
     * Return the path to the launcher.
     *
     * @return The path to the launcher.
     */
    private String getLauncherPath() {
        return core.getFolders().getApplicationFolder() + File.separator + "JTheque-Launcher.jar";
    }

    @Override
    public File getLogsFile() {
        return new File(core.getFolders().getLogsFolder(), "/jtheque.log");
    }
}