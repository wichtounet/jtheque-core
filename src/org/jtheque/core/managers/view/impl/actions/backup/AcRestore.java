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
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.file.IFileManager;
import org.jtheque.core.managers.file.able.FileType;
import org.jtheque.core.managers.log.IJThequeLogger;
import org.jtheque.core.managers.log.Logger;
import org.jtheque.core.managers.persistence.IPersistenceManager;
import org.jtheque.core.managers.view.able.IMainView;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.SimpleFilter;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Action to restore from JTD.
 *
 * @author Baptiste Wicht
 */
public class AcRestore extends JThequeAction {
    private static final long serialVersionUID = 8422616659589497685L;

    private final SimpleFilter filter;
    private final FileType type;

    @Resource
    private IMainView mainView;

    @Resource
    private IFileManager fileManager;

    @Resource
    private IViewManager viewManager;

    @Logger
    private IJThequeLogger logger;

    /**
     * Construct a new AcRestoreFromJTD.
     *
     * @param key    The internationalization key.
     * @param filter The file filter.
     * @param type   The file type.
     */
    AcRestore(String key, SimpleFilter filter, FileType type) {
        super(key);

        this.filter = filter;
        this.type = type;
    }

    @Override
    public final void actionPerformed(ActionEvent arg0) {
        if (fileManager.isRestorePossible(type)) {
            final File file = new File(viewManager.chooseFile(filter));

            final boolean yes = viewManager.askI18nUserForConfirmation(
                    "dialogs.confirm.clear.database", "dialogs.confirm.clear.database.title");

            viewManager.execute(new SimpleTask() {
                @Override
                public void run() {
                    mainView.startWait();

                    new Thread(new RestoreRunnable(yes, file)).start();
                }
            });
        } else {
            viewManager.displayI18nText("error.restore.nothing");
        }
    }

    /**
     * A runnable to launch the restore process.
     *
     * @author Baptiste Wicht
     */
    private final class RestoreRunnable implements Runnable {
        private final boolean clear;
        private final File file;

        /**
         * Create a new RestoreRunnable.
         *
         * @param clear indicate if we must clear the database or not.
         * @param file  The file to restore from.
         */
        RestoreRunnable(boolean clear, File file) {
            super();

            this.clear = clear;
            this.file = file;
        }

        @Override
        public void run() {
            if (clear) {
                Managers.getManager(IPersistenceManager.class).clearDatabase();
            }

            try {
                fileManager.restore(type, file);
            } catch (FileException e) {
                logger.error(e);
                Managers.getManager(IErrorManager.class).addInternationalizedError("error.restore.error");
            }

            viewManager.execute(new SimpleTask() {
                @Override
                public void run() {
                    mainView.stopWait();
                }
            });
        }
    }
}