package org.jtheque.views.impl.controllers;

import org.jtheque.core.able.ICore;
import org.jtheque.file.able.IFileService;
import org.jtheque.persistence.able.IPersistenceService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.utils.DesktopUtils;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.SimpleSwingWorker;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.IViews;
import org.jtheque.views.able.windows.IMainView;
import org.jtheque.xml.utils.XMLException;

import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

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

public class GeneralController extends AbstractController {
    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IFileService fileService;

    @Resource
    private IPersistenceService persistenceService;

    @Resource
    private IMainView mainView;

    @Resource
    private IViews views;

    @Resource
    private IViewService viewService;

    private void backup() {
        final boolean yes = uiUtils.askI18nUserForConfirmation(
                "dialogs.confirm.backup", "dialogs.confirm.backup.title");

        if (yes) {
            File file = SwingUtils.chooseFile(new SimpleFilter("XML(*.xml)", ".xml"));

            if (file != null) {
                fileService.backup(file);
            }
        }
    }

    private void restore() {
        final File file = SwingUtils.chooseFile(new SimpleFilter("XML(*.xml)", ".xml"));

        final boolean yes = uiUtils.askI18nUserForConfirmation(
                "dialogs.confirm.clear.database", "dialogs.confirm.clear.database.title");

        new RestoreWorker(yes, file).start();
    }

    private void help(){
        DesktopUtils.browse(ICore.HELP_URL);
    }

    private void bug() {
        DesktopUtils.browse(ICore.HELP_URL);
    }

    private void improvement() {
        DesktopUtils.browse(ICore.HELP_URL); //TODO REview that
    }

    private void messages(){
        views.getMessagesView().display();
    }

    private void events() {
        views.getEventView().display();
    }

    private void errors() {
        views.getErrorView().display();
    }

    private void about() {
        viewService.displayAboutView();
    }

    private void config() {
        views.getConfigView().display();
    }

    private void modules() {
        viewService.displayAboutView();
    }

    private final class RestoreWorker extends SimpleSwingWorker {
        private final boolean clear;
        private final File file;

        /**
         * Create a new RestoreWorker.
         *
         * @param clear indicate if we must clear the database or not.
         * @param file  The file to restore from.
         */
        RestoreWorker(boolean clear, File file) {
            super();

            this.clear = clear;
            this.file = file;
        }

        @Override
        protected void before() {
            mainView.startWait();
        }

        @Override
        protected void doWork() {
            if (clear) {
                persistenceService.clearDatabase();
            }

            try {
                fileService.restore(file);
            } catch (XMLException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }
        }

        @Override
        protected void done() {
            mainView.stopWait();
        }
    }
}