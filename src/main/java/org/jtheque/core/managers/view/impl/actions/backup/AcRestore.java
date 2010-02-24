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
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.core.Core;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.file.IFileManager;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.persistence.IPersistenceManager;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.able.IMainView;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.SimpleFilter;

import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Action to restore from JTD.
 *
 * @author Baptiste Wicht
 */
public class AcRestore extends JThequeAction {
    /**
     * Construct a new AcRestore.
     */
    public AcRestore() {
        super("menu.restore");
        
        setIcon(Managers.getManager(IResourceManager.class).getIcon(Core.IMAGES_BASE_NAME, "xml", ImageType.PNG));

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public final void actionPerformed(ActionEvent arg0) {
        final File file = new File(CoreUtils.getBean(IViewManager.class).getDelegate().chooseFile(new SimpleFilter("XML(*.xml)", ".xml")));

        final boolean yes = CoreUtils.getBean(IViewManager.class).askI18nUserForConfirmation(
                "dialogs.confirm.clear.database", "dialogs.confirm.clear.database.title");

        CoreUtils.getBean(IViewManager.class).execute(new SimpleTask() {
            @Override
            public void run() {
                CoreUtils.getBean(IMainView.class).startWait();

                new Thread(new RestoreRunnable(yes, file)).start();
            }
        });
    }

    /**
     * A runnable to launch the restore process.
     *
     * @author Baptiste Wicht
     */
    private static final class RestoreRunnable implements Runnable {
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
                CoreUtils.getBean(IFileManager.class).restore(file);
            } catch (FileException e) {
                Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
                Managers.getManager(IErrorManager.class).addInternationalizedError("error.restore.error");
            }

            CoreUtils.getBean(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    CoreUtils.getBean(IMainView.class).stopWait();
                }
            });
        }
    }
}