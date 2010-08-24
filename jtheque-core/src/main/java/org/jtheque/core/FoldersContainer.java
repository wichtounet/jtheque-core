package org.jtheque.core;

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

import org.jtheque.utils.annotations.ThreadSafe;

import java.io.File;

/**
 * The folders of the core. 
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public interface FoldersContainer {
    /**
     * Return the application folder. It seems the root folder where the application is launched.
     *
     * @return The File object who denotes the application folder or {@code null} if the application is not launched.
     */
    File getApplicationFolder();

    /**
     * Returns the logs folder. It seems the folder where the logs are located.
     *
     * @return the File object who denotes the logs folder or {@code null} if the application is not launched.
     */
    File getLogsFolder();

    /**
     * Return the modules folder. It seems the folder where the modules are located.
     *
     * @return The File object who denotes the modules folder or {@code null} if the application is not launched. 
     */
    File getModulesFolder();
}
