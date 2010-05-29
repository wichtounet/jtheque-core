package org.jtheque.update.impl.actions;

import org.jtheque.utils.StringUtils;

import java.io.File;

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

/**
 * @author Baptiste Wicht
 */
public abstract class AbstractUpdateAction implements UpdateAction {
    private String file;
    private String folder;

    /**
     * Set the name of the file.
     *
     * @param file The name of the file.
     */
    public final void setFile(String file) {
        this.file = file;
    }

    /**
     * Set the name of the folder.
     *
     * @param folder The name of folder.
     */
    public final void setFolder(String folder) {
        this.folder = folder;
    }

    /**
     * Return the complete path to the destination file.
     *
     * @return The complete path.
     */
    final String getDestination() {
        return buildFilePath(folder, file);
    }

    /**
     * Return the name of the file.
     *
     * @return The name of the file.
     */
    public String getFile() {
        return file;
    }

    /**
     * Build the file path with the specified folder and file.
     *
     * @param folder The folder of the file path.
     * @param file   The file part of the path.
     * @return The file path.
     */
    static String buildFilePath(String folder, String file) {
        StringBuilder builder = new StringBuilder(200);

        builder.append(new File(System.getProperty("user.dir")).getAbsolutePath());

        if (!StringUtils.isEmpty(folder)) {
            builder.append(File.separator);
            builder.append(folder);
        }

        builder.append(File.separator);
        builder.append(file);

        return builder.toString();
    }
}
