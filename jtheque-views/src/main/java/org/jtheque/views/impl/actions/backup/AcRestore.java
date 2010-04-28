package org.jtheque.views.impl.actions.backup;

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

import org.jtheque.file.IFileService;
import org.jtheque.io.XMLException;
import org.jtheque.persistence.able.IPersistenceService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.windows.IMainView;
import org.jtheque.utils.ui.edt.SimpleTask;
import org.jtheque.utils.io.SimpleFilter;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Action to restore from JTD.
 *
 * @author Baptiste Wicht
 */
public class AcRestore extends JThequeAction {
    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IFileService fileService;

    @Resource
    private IPersistenceService persistenceService;

    @Resource
    private IMainView mainView;

    /**
     * Construct a new AcRestore.
     */
    public AcRestore() {
        super("menu.restore");
    }

    @Override
    public final void actionPerformed(ActionEvent arg0) {
        final File file = new File(SwingUtils.chooseFile(new SimpleFilter("XML(*.xml)", ".xml")));

        final boolean yes = uiUtils.askI18nUserForConfirmation(
                "dialogs.confirm.clear.database", "dialogs.confirm.clear.database.title");

        SwingUtils.execute(new SimpleTask() {
            @Override
            public void run() {
                mainView.startWait();

                new Thread(new RestoreRunnable(yes, file)).start();
            }
        });
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
                persistenceService.clearDatabase();
            }

            try {
                fileService.restore(file);
            } catch (XMLException e){
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }

            SwingUtils.execute(new SimpleTask() {
                @Override
                public void run() {
                    mainView.stopWait();
                }
            });
        }
    }
}