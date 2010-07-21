package org.jtheque.views.impl.controllers;

import org.jtheque.collections.able.CollectionListener;
import org.jtheque.collections.able.ICollectionsService;
import org.jtheque.core.able.ICore;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.ui.able.IController;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.SimpleSwingWorker;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.panel.IModuleView;
import org.jtheque.views.able.panel.IRepositoryView;

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
 * A controller for the module view.
 *
 * @author Baptiste Wicht
 */
public class ModuleController extends AbstractController<IModuleView> {
    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IModuleService moduleService;

    @Resource
    private ICollectionsService collectionsService;

    @Resource
    private IViewService viewService;

    @Resource
    private IUpdateService updateService;

    @Resource
    private IController<IRepositoryView> repositoryController;

    public ModuleController() {
        super(IModuleView.class);
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(8);

        translations.put("modules.actions.stop", "stop");
        translations.put("modules.actions.start", "start");
        translations.put("modules.actions.enable", "enable");
        translations.put("modules.actions.update", "updateModule");
        translations.put("modules.actions.disable", "disable");
        translations.put("modules.actions.url.new", "installURL");
        translations.put("modules.actions.file.new", "installFile");
        translations.put("modules.actions.uninstall", "uninstall");
        translations.put("modules.actions.update.kernel", "updateCore");
        translations.put("modules.actions.repository", "repository");

        return translations;
    }

    /**
     * Disable the selected module.
     */
    private void disable() {
        Module module = getView().getSelectedModule();

        String error = moduleService.canBeDisabled(module);

        if (StringUtils.isEmpty(error)) {
            moduleService.disableModule(module);
            getView().refreshList();
        } else {
            uiUtils.getDelegate().displayText(error);
        }
    }

    /**
     * Enable the selected module.
     */
    private void enable() {
        Module module = getView().getSelectedModule();

        if (module.getState() == ModuleState.DISABLED) {
            if (module.getCoreVersion().isGreaterThan(ICore.VERSION)) {
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
    private void installFile() {
        File file = SwingUtils.chooseFile(new SimpleFilter("JAR File (*.jar)", "jar"));

        if (file != null) {
            moduleService.installModule(file);
        }
    }

    /**
     * Install a module from an URL.
     */
    private void installURL() {
        String url = uiUtils.askI18nText("dialogs.modules.install.url");

        if (StringUtils.isNotEmpty(url)) {
            moduleService.install(url);
        }
    }

    /**
     * Uninstall the given module.
     */
    private void uninstall() {
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
            uiUtils.getDelegate().displayText(error);
        }
    }

    /**
     * Stop the selected module.
     */
    private void stop() {
        final Module module = getView().getSelectedModule();

        String error = moduleService.canBeStopped(module);

        if (StringUtils.isEmpty(error)) {
            new StopModuleWorker(module).start();
        } else {
            uiUtils.getDelegate().displayText(error);
        }
    }

    /**
     * Start the selected module.
     */
    private void start() {
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
            uiUtils.getDelegate().displayText(error);
        }
    }

    /**
     * Update the selected module.
     */
    private void updateModule() {
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
    private void updateCore() {
        if (updateService.isCurrentVersionUpToDate()) {
            uiUtils.displayI18nText("message.update.no.version");
        } else {
            new UpdateCoreWorker().start();
        }
    }

    /**
     * Display the repository.
     */
    private void repository() {
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
            updateService.updateCore(updateService.getMostRecentCoreVersion());
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
            updateService.updateToMostRecentVersion(module);
        }
    }
}