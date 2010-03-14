package org.jtheque.update.actions;

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

import org.jtheque.logging.ILoggingManager;
import org.jtheque.update.UpdateServices;
import org.jtheque.utils.io.CopyException;
import org.jtheque.utils.io.FileUtils;

/**
 * An update action that move a file.
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
            UpdateServices.get(ILoggingManager.class).getLogger(getClass()).debug("The file ({}) can not be moved.", getSource());
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