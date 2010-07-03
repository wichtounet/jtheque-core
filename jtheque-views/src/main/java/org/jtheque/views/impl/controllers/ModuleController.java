package org.jtheque.views.impl.controllers;

import org.jtheque.collections.able.CollectionListener;
import org.jtheque.collections.able.ICollectionsService;
import org.jtheque.core.able.ICore;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.SimpleSwingWorker;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.IViews;
import org.jtheque.views.able.panel.IModuleView;
import org.jtheque.views.able.windows.IUpdateView;

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

public class ModuleController extends AbstractController {
    @Resource
    private IModuleView moduleView;

    @Resource
    private IUIUtils uiUtils;

    @Resource
    private IModuleService moduleService;

    @Resource
    private ICollectionsService collectionsService;

    @Resource
    private IViewService viewService;

    @Resource
    private IViews views;

    @Resource
    private IUpdateService updateService;

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

        return translations;
    }

    private void disable() {
        Module module = moduleView.getSelectedModule();

        String error = moduleService.canBeDisabled(module);

        if (StringUtils.isEmpty(error)) {
            moduleService.disableModule(module);
            moduleView.refreshList();
        } else {
            uiUtils.getDelegate().displayText(error);
        }
    }

    private void enable() {
        Module module = moduleView.getSelectedModule();

        if (module.getState() == ModuleState.DISABLED) {
            if (module.getCoreVersion().isGreaterThan(ICore.VERSION)) {
                uiUtils.displayI18nText("modules.message.versionproblem");
            } else {
                moduleService.enableModule(module);
                moduleView.refreshList();
            }
        } else {
            uiUtils.displayI18nText("error.module.not.disabled");
        }
    }

    private void installFile() {
        File file = SwingUtils.chooseFile(new SimpleFilter("JAR File (*.jar)", "jar"));

        if (file != null) {
            moduleService.installModule(file);
        }
    }

    private void installURL() {
        String url = uiUtils.askI18nText("dialogs.modules.install.url");

        if (StringUtils.isNotEmpty(url)) {
            moduleService.install(url);
        }
    }

    private void uninstall() {
        Module module = moduleView.getSelectedModule();

        String error = moduleService.canBeUninstalled(module);

        if (StringUtils.isEmpty(error)) {
            boolean confirm = uiUtils.askI18nUserForConfirmation(
                    "dialogs.confirm.uninstall",
                    "dialogs.confirm.uninstall.title");

            if (confirm) {
                moduleService.uninstallModule(module);
                moduleView.refreshList();
            }
        } else {
            uiUtils.getDelegate().displayText(error);
        }
    }

    private void stop() {
        final Module module = moduleView.getSelectedModule();

        String error = moduleService.canBeStopped(module);

        if (StringUtils.isEmpty(error)) {
            new StopModuleWorker(module).start();
        } else {
            uiUtils.getDelegate().displayText(error);
        }
    }

    private void start() {
        Module module = moduleView.getSelectedModule();

        String error = moduleService.canBeStarted(module);

        if (StringUtils.isEmpty(error)) {
            if (moduleService.needTwoPhasesLoading(module)) {
                moduleView.closeDown();
                viewService.displayCollectionView();
                collectionsService.addCollectionListener(new StartModuleWorker(module));
            } else {
                new StartModuleWorker(module).start();
            }
        } else {
            uiUtils.getDelegate().displayText(error);
        }
    }

    private void updateModule() {
        final Module module = moduleView.getSelectedModule();

        update(module, updateService.isUpToDate(module), "module");
    }

    private void updateCore() {
        update(null, updateService.isCurrentVersionUpToDate(), "kernel");
    }

    private void update(Object object, boolean upToDate, String message) {
        IUpdateView updateView = views.getUpdateView();

        if (upToDate) {
            uiUtils.displayI18nText("message.update.no.version");
        } else {
            updateView.sendMessage(message, object);
            updateView.display();
        }
    }

    private abstract class ModuleWorker extends SimpleSwingWorker {
        @Override
        protected void before() {
            moduleView.startWait();
        }

        @Override
        protected void done() {
            moduleView.refreshList();
            moduleView.stopWait();
        }
    }

    private class StartModuleWorker extends ModuleWorker implements CollectionListener {
        private final Module module;

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

            moduleView.display();
        }
    }

    private class StopModuleWorker extends ModuleWorker {
        private final Module module;

        private StopModuleWorker(Module module) {
            this.module = module;
        }

        @Override
        protected void doWork() {
            moduleService.stopModule(module);
        }
    }
}