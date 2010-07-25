package org.jtheque.views.impl.controllers;

import org.jtheque.core.able.ICore;
import org.jtheque.file.able.IFileService;
import org.jtheque.persistence.able.IPersistenceService;
import org.jtheque.ui.able.Action;
import org.jtheque.ui.able.IController;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.utils.DesktopUtils;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.SimpleSwingWorker;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.IViewService;
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

    /**
     * Construct a new GeneralController. 
     */
    public GeneralController() {
        super(IMainView.class);
    }

    /**
     * Backup the database.
     */
    @Action("menu.backup")
    public void backup() {
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
    @Action("menu.restore")
    public void restore() {
        final File file = SwingUtils.chooseFile(new SimpleFilter("XML(*.xml)", ".xml"));

        final boolean yes = uiUtils.askI18nUserForConfirmation(
                "dialogs.confirm.clear.database", "dialogs.confirm.clear.database.title");

        new RestoreWorker(yes, file).start();
    }

    /**
     * Open the help.
     */
    @Action("menu.help")
    public void help() {
        DesktopUtils.browse(ICore.HELP_URL);
    }

    /**
     * Send a request for a Bug.
     */
    @Action("menu.bug")
    public void bug() {
        DesktopUtils.browse(ICore.BUG_TRACKER_URL);
    }

    /**
     * Give an improvement proposal.
     */
    @Action("menu.improvement")
    public void improvement() {
        DesktopUtils.browse(ICore.BUG_TRACKER_URL);
    }

    /**
     * Display the messages view.
     */
    @Action("menu.messages")
    public void messages() {
        messageController.getView().display();
    }

    /**
     * Display the events view.
     */
    @Action("menu.events")
    public void events() {
        eventController.getView().display();
    }

    /**
     * Display the errors view.
     */
    @Action("menu.errors")
    public void errors() {
        errorController.getView().display();
    }

    /**
     * Display the about view.
     */
    @Action("menu.about")
    public void about() {
        viewService.displayAboutView();
    }

    /**
     * Display the config view.
     */
    @Action("menu.config")
    public void config() {
        configController.getView().display();
    }

    /**
     * Display the modules view.
     */
    @Action("menu.modules")
    public void modules() {
        moduleController.getView().display();
    }

    /**
     * Exit from the application.
     */
    @Action("menu.exit")
    public void exit() {
        core.getLifeCycle().exit();
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