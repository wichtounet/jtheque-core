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
import org.jtheque.core.able.FilesContainer;
import org.jtheque.utils.annotations.ThreadSafe;

import java.io.File;

/**
 * Give access to the files of the application.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class Files implements FilesContainer {
    private final Core core;

    /**
     * Construct a new Files.
     *
     * @param core The core.
     */
    public Files(Core core) {
        super();

        this.core = core;
    }

    @Override
    public File getLogsFile() {
        return new File(core.getFolders().getLogsFolder(), "/jtheque.log");
    }
}