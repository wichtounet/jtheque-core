package org.jtheque.core.managers.view.impl.actions.backup;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.core.Core;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.file.IFileManager;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.SimpleFilter;

import java.awt.event.ActionEvent;
import java.io.File;

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

/**
 * An abstract action to backup.
 *
 * @author Baptiste Wicht
 */
public class AcBackup extends JThequeAction {
    /**
     * Construct a new AcBackup.
     */
    public AcBackup() {
        super("menu.backup");

        setIcon(Managers.getManager(IResourceManager.class).getIcon(Core.IMAGES_BASE_NAME, "xml", ImageType.PNG));

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public final void actionPerformed(ActionEvent e) {
        final boolean yes = CoreUtils.getBean(IViewManager.class).askI18nUserForConfirmation(
                    "dialogs.confirm.backup", "dialogs.confirm.backup.title");

        if (yes) {
            File file = new File(CoreUtils.getBean(IViewManager.class).chooseFile(new SimpleFilter("XML(*.xml)", ".xml")));

            try {
                CoreUtils.getBean(IFileManager.class).backup(file);
            } catch (FileException e1) {
                Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e1);
                Managers.getManager(IErrorManager.class).addInternationalizedError("error.backup.error");
            }
        }
    }
}