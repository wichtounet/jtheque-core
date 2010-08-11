package org.jtheque.views.impl.controllers;

import org.jtheque.collections.able.CollectionListener;
import org.jtheque.collections.able.CollectionsService;
import org.jtheque.core.able.Core;
import org.jtheque.core.able.lifecycle.LifeCycle;
import org.jtheque.modules.able.ModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.ui.able.Action;
import org.jtheque.ui.able.Controller;
import org.jtheque.ui.able.UIUtils;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.update.able.UpdateService;
import org.jtheque.update.able.InstallationResult;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.SimpleSwingWorker;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.ViewService;
import org.jtheque.views.able.panel.ModuleView;
import org.jtheque.views.able.panel.RepositoryView;

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
 * A controller for the module view.
 *
 * @author Baptiste Wicht
 */
public class ModuleController extends AbstractController<ModuleView> {
    @Resource
    private UIUtils uiUtils;

    @Resource
    private ModuleService moduleService;

    @Resource
    private LifeCycle lifeCycle;

    @Resource
    private CollectionsService collectionsService;

    @Resource
    private ViewService viewService;

    @Resource
    private UpdateService updateService;

    @Resource
    private Controller<RepositoryView> repositoryController;

    /**
     * Create a new ModuleController. 
     */
    public ModuleController() {
        super(ModuleView.class);
    }

    /**
     * Disable the selected module.
     */
    @Action("modules.actions.enable")
    public void disable() {
        Module module = getView().getSelectedModule();

        String error = moduleService.canBeDisabled(module);

        if (StringUtils.isEmpty(error)) {
            moduleService.disableModule(module);
            getView().refreshList();
        } else {
            uiUtils.displayI18nText(error);
        }
    }

    /**
     * Enable the selected module.
     */
    @Action("modules.actions.disable")
    public void enable() {
        Module module = getView().getSelectedModule();

        if (module.getState() == ModuleState.DISABLED) {
            if (module.getCoreVersion().isGreaterThan(Core.VERSION)) {
                uiUtils.displayI18nText("modules.message.versionproblem");
            } else {
                moduleService.enableModule(module);
                getView().refreshList();
            }
        } else {
            uiUtils.displayI18nText("error.module.not.disabled");
        }
    }

    /**
     * Install a module from a file.
     */
    @Action("modules.actions.file.new")
    public void installFile() {
        File file = SwingUtils.chooseFile(new SimpleFilter("JAR File (*.jar)", "jar"));

        if (file != null) {
            moduleService.installModule(file);
        }
    }

    /**
     * Install a module from an URL.
     */
    @Action("modules.actions.url.new")
    public void installURL() {
        String url = uiUtils.askI18nText("dialogs.modules.install.url");

        if (StringUtils.isNotEmpty(url)) {
            InstallationResult result = updateService.installModule(url);

            moduleService.install(result);
        }
    }

    /**
     * Uninstall the given module.
     */
    @Action("modules.actions.uninstall")
    public void uninstall() {
        Module module = getView().getSelectedModule();

        String error = moduleService.canBeUninstalled(module);

        if (StringUtils.isEmpty(error)) {
            boolean confirm = uiUtils.askI18nUserForConfirmation(
                    "dialogs.confirm.uninstall",
                    "dialogs.confirm.uninstall.title");

            if (confirm) {
                moduleService.uninstallModule(module);
                getView().refreshList();
            }
        } else {
            uiUtils.displayI18nText(error);
        }
    }

    /**
     * Stop the selected module.
     */
    @Action("modules.actions.stop")
    public void stop() {
        final Module module = getView().getSelectedModule();

        String error = moduleService.canBeStopped(module);

        if (StringUtils.isEmpty(error)) {
            new StopModuleWorker(module).start();
        } else {
            uiUtils.displayI18nText(error);
        }
    }

    /**
     * Start the selected module.
     */
    @Action("modules.actions.start")
    public void start() {
        Module module = getView().getSelectedModule();

        String error = moduleService.canBeStarted(module);

        if (StringUtils.isEmpty(error)) {
            if (moduleService.needTwoPhasesLoading(module)) {
                getView().closeDown();
                viewService.displayCollectionView();
                collectionsService.addCollectionListener(new StartModuleWorker(module));
            } else {
                new StartModuleWorker(module).start();
            }
        } else {
            uiUtils.displayI18nText(error);
        }
    }

    /**
     * Update the selected module.
     */
    @Action("modules.actions.update")
    public void updateModule() {
        final Module module = getView().getSelectedModule();

        if (updateService.isUpToDate(module)) {
            uiUtils.displayI18nText("message.update.no.version");
        } else {
            new UpdateModuleWorker(module).start();
        }
    }

    /**
     * Update the core.
     */
    @Action("modules.actions.update.kernel")
    public void updateCore() {
        if (updateService.isCurrentVersionUpToDate()) {
            uiUtils.displayI18nText("message.update.no.version");
        } else {
            new UpdateCoreWorker().start();
        }
    }

    /**
     * Display the repository.
     */
    @Action("modules.actions.repository")
    public void repository() {
        repositoryController.getView().display();
    }

    /**
     * A simple swing worker to make work in the module view. The module view is waiting during the operations.
     *
     * @author Baptiste Wicht
     */
    private abstract class ModuleWorker extends SimpleSwingWorker {
        @Override
        protected final void before() {
            getView().getWindowState().startWait();
        }

        @Override
        protected final void done() {
            getView().refreshList();
            getView().getWindowState().stopWait();
        }
    }

    /**
     * A simple swing worker to start the module.
     *
     * @author Baptiste Wicht
     */
    private final class StartModuleWorker extends ModuleWorker implements CollectionListener {
        private final Module module;

        /**
         * Construct a new StartModuleWorker for the given module.
         *
         * @param module The module to start.
         */
        private StartModuleWorker(Module module) {
            this.module = module;
        }

        @Override
        protected void doWork() {
            moduleService.startModule(module);
        }

        @Override
        public void collectionChosen() {
            collectionsService.removeCollectionListener(this);

            viewService.closeCollectionView();

            start();

            getView().display();
        }
    }

    /**
     * A simple swing worker to stop the module.
     *
     * @author Baptiste Wicht
     */
    private final class StopModuleWorker extends ModuleWorker {
        private final Module module;

        /**
         * Construct a new StopModuleWorker for the given module.
         *
         * @param module The module to start.
         */
        private StopModuleWorker(Module module) {
            this.module = module;
        }

        @Override
        protected void doWork() {
            moduleService.stopModule(module);
        }
    }

    /**
     * A simple swing worker to update the core.
     *
     * @author Baptiste Wicht
     */
    private final class UpdateCoreWorker extends ModuleWorker {
        @Override
        protected void doWork() {
            updateService.updateCore();
            lifeCycle.restart();
        }
    }

    /**
     * A simple swing worker to update a module.
     *
     * @author Baptiste Wicht
     */
    private final class UpdateModuleWorker extends ModuleWorker {
        private final Module module;

        /**
         * Construct a new UpdateModuleWorker for the given module.
         *
         * @param module The module to start.
         */
        private UpdateModuleWorker(Module module) {
            this.module = module;
        }

        @Override
        protected void doWork() {
            boolean restart = false;

            if (module.getState() == ModuleState.STARTED) {
                moduleService.stopModule(module);

                restart = true;
            }

            updateService.update(module);

            if (restart) {
                moduleService.startModule(module);
            }
        }
    }
}