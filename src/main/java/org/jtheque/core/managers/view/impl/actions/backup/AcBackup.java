package org.jtheque.core.managers.view.impl.actions.backup;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.file.IFileManager;
import org.jtheque.core.managers.file.able.FileType;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.SimpleFilter;

import javax.annotation.Resource;
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
public abstract class AcBackup extends JThequeAction {
    private final SimpleFilter filter;
    private final FileType type;

    @Resource
    private IFileManager fileManager;

    @Resource
    private IViewManager viewManager;

    /**
     * Construct a new AcBackup.
     *
     * @param key    The internationalization key.
     * @param filter The file filter.
     * @param type   The file type.
     */
    AcBackup(String key, SimpleFilter filter, FileType type) {
        super(key);

        this.filter = filter;
        this.type = type;

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public final void actionPerformed(ActionEvent e) {
        if (fileManager.isBackupPossible(type)) {
            final boolean yes = viewManager.askI18nUserForConfirmation(
                    "dialogs.confirm.backup", "dialogs.confirm.backup.title");

            if (yes) {
                File file = new File(viewManager.chooseFile(filter));

                try {
                    fileManager.backup(type, file);
                } catch (FileException e1) {
                    Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e1);
                    Managers.getManager(IErrorManager.class).addInternationalizedError("error.backup.error");
                }
            }
        } else {
            viewManager.displayI18nText("error.backup.nothing");
        }
    }
}