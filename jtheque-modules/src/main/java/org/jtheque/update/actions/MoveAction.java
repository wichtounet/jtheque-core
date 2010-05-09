package org.jtheque.update.actions;

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

import org.jtheque.utils.io.CopyException;
import org.jtheque.utils.io.FileUtils;
import org.slf4j.LoggerFactory;

/**
 * An org.jtheque.update action that move a file.
 *
 * @author Baptiste Wicht
 */
public final class MoveAction extends AbstractUpdateAction {
    private String sourceFile;
    private String sourceFolder;

    @Override
    public void execute() {
        try {
            FileUtils.move(getSource(), getDestination());
        } catch (CopyException e) {
            LoggerFactory.getLogger(getClass()).debug("The file ({}) can not be moved.", getSource());
        }
    }

    /**
     * Return the path of the source's file.
     *
     * @return The source's path.
     */
    private String getSource() {
        return buildFilePath(sourceFolder, sourceFile);
    }

    /**
     * Set the path to the source's file.
     *
     * @param sourceFile The path to the source's file.
     */
    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * Set the path to the source's folder.
     *
     * @param sourceFolder The path to the source's folder.
     */
    public void setSourceFolder(String sourceFolder) {
        this.sourceFolder = sourceFolder;
    }
}