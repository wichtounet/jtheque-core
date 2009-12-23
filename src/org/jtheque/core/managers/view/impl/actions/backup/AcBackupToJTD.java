package org.jtheque.core.managers.view.impl.actions.backup;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.core.Core;
import org.jtheque.core.managers.file.able.FileType;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.utils.io.SimpleFilter;

/**
 * Action to backup to JTD.
 *
 * @author Baptiste Wicht
 */
public final class AcBackupToJTD extends AcBackup {
    /**
     * Construct a new AcBackupToJTD.
     */
    public AcBackupToJTD() {
        super("file.actions.to.jtd", new SimpleFilter("JTD(*.jtd)", ".jtd"), FileType.JTD);

        setIcon(Managers.getManager(IResourceManager.class).getIcon(Core.IMAGES_BASE_NAME, "text", ImageType.PNG));
    }
}