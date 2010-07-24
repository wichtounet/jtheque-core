package org.jtheque.views.impl.controllers;

import org.jtheque.core.able.ICore;
import org.jtheque.file.able.IFileService;
import org.jtheque.persistence.able.IPersistenceService;
import org.jtheque.ui.able.IController;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.utils.DesktopUtils;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.SimpleSwingWorker;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.IViews;
import org.jtheque.views.able.panel.IModuleView;
import org.jtheque.views.able.windows.IConfigView;
import org.jtheque.views.able.windows.IErrorView;
import org.jtheque.views.able.windows.IEventView;
import org.jtheque.views.able.windows.IMainView;
import org.jtheque.views.able.windows.IMessageView;
import org.jtheque.xml.utils.XMLException;

import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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

/**
 * The global controller.
 *
 * @author Baptiste Wicht
 */
public class GeneralController extends AbstractController<IMainView> {
    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IFileService fileService;

    @Resource
    private IPersistenceService persistenceService;

    @Resource
    private IViewService viewService;

    @Resource
    private ICore core;

    @Resource
    private IController<IConfigView> configController;

    @Resource
    private IController<IMessageView> messageController;

    @Resource
    private IController<IEventView> eventController;

    @Resource
    private IController<IErrorView> errorController;

    @Resource
    private IController<IModuleView> moduleController;

    public GeneralController() {
        super(IMainView.class);
    }

    /**
     * Backup the database.
     */
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

    /**
     * Restore the database.
     */
    private void restore() {
        final File file = SwingUtils.chooseFile(new SimpleFilter("XML(*.xml)", ".xml"));

        final boolean yes = uiUtils.askI18nUserForConfirmation(
                "dialogs.confirm.clear.database", "dialogs.confirm.clear.database.title");

        new RestoreWorker(yes, file).start();
    }

    /**
     * Open the help.
     */
    private void help() {
        DesktopUtils.browse(ICore.HELP_URL);
    }

    /**
     * Send a request for a Bug.
     */
    private void bug() {
        DesktopUtils.browse(ICore.HELP_URL);
    }

    /**
     * Give an improvement proposal.
     */
    private void improvement() {
        DesktopUtils.browse(ICore.HELP_URL); //TODO REview that
    }

    /**
     * Display the messages view.
     */
    private void messages() {
        messageController.getView().display();
    }

    /**
     * Display the events view.
     */
    private void events() {
        eventController.getView().display();
    }

    /**
     * Display the errors view.
     */
    private void errors() {
        errorController.getView().display();
    }

    /**
     * Display the about view.
     */
    private void about() {
        viewService.displayAboutView();
    }

    /**
     * Display the config view.
     */
    private void config() {
        configController.getView().display();
    }

    /**
     * Display the modules view.
     */
    private void modules() {
        moduleController.getView().display();
    }

    /**
     * Exit from the application.
     */
    private void exit() {
        core.getLifeCycle().exit();
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(12);

        translations.put("menu.backup", "backup");
        translations.put("menu.restore", "restore");
        translations.put("menu.exit", "exit");
        translations.put("menu.config", "config");
        translations.put("menu.modules", "modules");
        translations.put("menu.help", "help");
        translations.put("menu.bug", "bug");
        translations.put("menu.improvement", "improvement");
        translations.put("menu.events", "events");
        translations.put("menu.messages", "messages");
        translations.put("menu.errors", "errors");
        translations.put("menu.events", "events");

        return translations;
    }

    /**
     * A swing worker to restore the database.
     *
     * @author Baptiste Wicht
     */
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
            getView().getWindowState().startWait();
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
            getView().getWindowState().stopWait();
        }
    }
}