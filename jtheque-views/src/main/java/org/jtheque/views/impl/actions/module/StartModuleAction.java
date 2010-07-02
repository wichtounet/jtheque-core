package org.jtheque.views.impl.actions.module;

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

import org.jtheque.collections.able.CollectionListener;
import org.jtheque.collections.able.ICollectionsService;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.utils.ui.edt.SimpleTask;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.panel.IModuleView;

import java.awt.event.ActionEvent;

/**
 * An action to load a module.
 *
 * @author Baptiste Wicht
 */
public final class StartModuleAction extends JThequeAction implements CollectionListener {
    private final IModuleView moduleView;
    private final IUIUtils uiUtils;
    private final IModuleService moduleService;
    private final ICollectionsService collectionsService;
    private final IViewService viewService;

    private Module module;

    /**
     * Construct a new StartModuleAction.
     *
     * @param moduleService      The module service.
     * @param uiUtils            The UI Utils.
     * @param moduleView         The module view.
     * @param collectionsService The collection services.
     * @param viewService        The view service
     */
    public StartModuleAction(IModuleService moduleService, IUIUtils uiUtils, IModuleView moduleView,
                             ICollectionsService collectionsService, IViewService viewService) {
        super("modules.actions.start");

        this.moduleService = moduleService;
        this.uiUtils = uiUtils;
        this.moduleView = moduleView;
        this.collectionsService = collectionsService;
        this.viewService = viewService;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        module = moduleView.getSelectedModule();

        String error = moduleService.canBeStarted(module);

        if (StringUtils.isEmpty(error)) {
            if (moduleService.needTwoPhasesLoading(module)) {
                moduleView.closeDown();
                viewService.displayCollectionView();
                collectionsService.addCollectionListener(this);
            } else {
                loadModule(module);
            }
        } else {
            uiUtils.getDelegate().displayText(error);
        }
    }

    @Override
    public void collectionChoosed() {
        collectionsService.removeCollectionListener(this);

        viewService.closeCollectionView();

        loadModule(module);

        moduleView.display();
    }

    private void loadModule(Module module) {
        SwingUtils.execute(new SimpleTask() {
            @Override
            public void run() {
                moduleView.startWait();
            }
        });

        new Thread(new StartModuleRunnable(module), "Module loader").start();
    }

    private class StartModuleRunnable implements Runnable {
        private final Module module;

        private StartModuleRunnable(Module module) {
            this.module = module;
        }

        @Override
        public void run() {
            moduleService.startModule(module);

            SwingUtils.execute(new SimpleTask() {
                @Override
                public void run() {
                    moduleView.refreshList();
                    moduleView.stopWait();
                }
            });
        }
    }
}