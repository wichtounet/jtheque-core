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

import org.jtheque.core.ApplicationListener;
import org.jtheque.core.Core;
import org.jtheque.core.FoldersContainer;
import org.jtheque.core.application.Application;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.io.FileUtils;

import java.io.File;

/**
 * Give access to the folders of the application. Pay attention that if the application has not been launched,
 * the folders are all {@code null}. 
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class Folders implements FoldersContainer, ApplicationListener {
    private volatile File applicationFolder;
    private volatile File modulesFolder;
    private volatile File logsFolder;

    /**
     * Construct a new Folders.
     *
     * @param core The core.
     */
    public Folders(Core core) {
        super();

        core.addApplicationListener(this);
    }

    @Override
    public File getApplicationFolder() {
        if (applicationFolder == null) {
            throw new IllegalStateException("The application has not been launched. ");
        }

        return applicationFolder;
    }

    @Override
    public File getLogsFolder() {
        if (logsFolder == null) {
            throw new IllegalStateException("The application has not been launched. ");
        }

        return logsFolder;
    }

    @Override
    public File getModulesFolder() {
        if(modulesFolder == null){
            throw new IllegalStateException("The application has not been launched. ");
        }

        return modulesFolder;
    }

    @Override
    public void applicationLaunched(Application application) {
        applicationFolder = new File(application.getFolderPath());
        FileUtils.createIfNotExists(applicationFolder);

        logsFolder = new File(applicationFolder, "logs");
        FileUtils.createIfNotExists(logsFolder);

        modulesFolder = new File(applicationFolder, "modules");
        FileUtils.createIfNotExists(modulesFolder);
    }
}