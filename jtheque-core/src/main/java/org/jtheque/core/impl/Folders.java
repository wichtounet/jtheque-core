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

import org.jtheque.core.able.Core;
import org.jtheque.core.able.FoldersContainer;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.io.FileUtils;

import java.io.File;

/**
 * Give access to the folders of the application.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class Folders implements FoldersContainer {
    private final File applicationFolder;
    private final File modulesFolder;
    private final File logsFolder;

    /**
     * Construct a new Folders.
     *
     * @param core The core.
     */
    public Folders(Core core) {
        super();

        applicationFolder = new File(core.getApplication().getFolderPath());
        FileUtils.createIfNotExists(applicationFolder);

        logsFolder = new File(applicationFolder, "logs");
        FileUtils.createIfNotExists(logsFolder);

        modulesFolder = new File(applicationFolder, "modules");
        FileUtils.createIfNotExists(modulesFolder);
    }

    @Override
    public File getApplicationFolder() {
        return applicationFolder;
    }

    @Override
    public File getLogsFolder() {
        return logsFolder;
    }

    @Override
    public File getModulesFolder() {
        return modulesFolder;
    }
}