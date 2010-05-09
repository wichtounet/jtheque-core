package org.jtheque.views.impl.actions.backup;

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