package org.jtheque.lifecycle;

import org.jtheque.collections.CollectionListener;
import org.jtheque.collections.CollectionsService;
import org.jtheque.core.Core;
import org.jtheque.core.Folders;
import org.jtheque.core.application.Application;
import org.jtheque.core.lifecycle.LifeCycle;
import org.jtheque.events.EventLevel;
import org.jtheque.events.EventService;
import org.jtheque.events.Events;
import org.jtheque.lifecycle.application.XMLApplicationReader;
import org.jtheque.modules.ModuleService;
import org.jtheque.utils.SimplePropertiesCache;
import org.jtheque.utils.ThreadUtils;
import org.jtheque.utils.annotations.NotThreadSafe;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.utils.ui.edt.SimpleTask;
import org.jtheque.views.SplashService;
import org.jtheque.views.ViewService;
import org.jtheque.views.Views;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

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
 * The launcher of JTheque. This launcher must only be created (by Spring) after all the other core bundles are
 * started.
 *
 * @author Baptiste Wicht
 */
@NotThreadSafe
public class LifeCycleLauncher implements CollectionListener {
    @Resource
    private ViewService viewService;

    @Resource
    private SplashService splashService;

    @Resource
    private CollectionsService collectionsService;

    @Resource
    private EventService eventService;

    @Resource
    private ModuleService moduleService;

    @Resource
    private Core core;

    @Resource
    private LifeCycle lifeCycle;

    @Resource
    private Views views;

    /**
     * Start the application. Automatically launched by Spring.
     */
    @PostConstruct
    public void start() {
        configureDefaultsProperties();

        Application application = new XMLApplicationReader().readApplication(Folders.getApplicationFolder() + "/application.xml");

        core.launchApplication(application);

        splashService.initViews();
        splashService.displaySplashScreen();

        views.init();

        eventService.addEvent(Events.newEvent(EventLevel.INFO, "User", "events.start", EventService.CORE_EVENT_LOG));

        moduleService.load();

        if (moduleService.hasCollectionModule()) {
            SwingUtils.execute(new SimpleTask() {
                @Override
                public void run() {
                    splashService.closeSplashScreen();
                    collectionsService.addCollectionListener(LifeCycleLauncher.this);
                    viewService.displayCollectionView();
                }
            });
        } else {
            startSecondPhase();
        }
    }

    /**
     * Configure the default properties of the simple properties cache.
     */
    private static void configureDefaultsProperties() {
        SimplePropertiesCache.put("config-view-loaded", true);
        SimplePropertiesCache.put("statebar-loaded", true);
        SimplePropertiesCache.put("collectionChosen", true);
    }

    @Override
    public void collectionChosen() {
        viewService.closeCollectionView();
        collectionsService.removeCollectionListener(this);

        if (SwingUtils.isEDT()) {
            ThreadUtils.inNewThread(new Runnable() {
                @Override
                public void run() {
                    startSecondPhase();
                }
            });
        } else {
            startSecondPhase();
        }
    }

    /**
     * Start the second phase of the application.
     */
    private void startSecondPhase() {
        splashService.closeSplashScreen();
        splashService.fillMainView();

        moduleService.startModules();

        lifeCycle.refreshTitle();

        views.displayConditionalViews();
    }
}