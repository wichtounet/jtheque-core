package org.jtheque.lifecycle;

import org.jtheque.collections.able.CollectionListener;
import org.jtheque.collections.able.ICollectionsService;
import org.jtheque.core.able.ICore;
import org.jtheque.core.able.application.Application;
import org.jtheque.core.utils.SimplePropertiesCache;
import org.jtheque.core.utils.SystemProperty;
import org.jtheque.events.able.EventLevel;
import org.jtheque.events.able.IEventService;
import org.jtheque.events.utils.Event;
import org.jtheque.lifecycle.application.XMLApplicationReader;
import org.jtheque.messages.able.IMessageService;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.utils.ui.edt.SimpleTask;
import org.jtheque.views.able.ISplashService;
import org.jtheque.views.able.IViewService;
import org.jtheque.views.able.IViews;

import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

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
 * An activator to launch the life cycle of the application.
 *
 * @author Baptiste Wicht
 */
public class LifeCycleLauncher implements CollectionListener {
    @Resource
    private IViewService viewService;

    @Resource
    private ISplashService splashService;

    @Resource
    private IMessageService messageService;

    @Resource
    private ICollectionsService collectionsService;

    @Resource
    private IEventService eventService;

    @Resource
    private IModuleService moduleService;

    @Resource
    private ICore core;

    @Resource
    private IViews views;

    /**
     * Start the application. Automatically launched by Spring.
     */
    @PostConstruct
    public void start() {
        configureDefaultsProperties();
        configureLogging();

        Application application = new XMLApplicationReader().readApplication(SystemProperty.USER_DIR.get() + "/application.xml");

        core.setApplication(application);

        splashService.initViews();
        splashService.displaySplashScreen();

        views.init();

        eventService.addEvent(IEventService.CORE_EVENT_LOG, Event.newEvent(EventLevel.INFO, "User", "events.start"));

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

    /**
     * Configure the logging.
     */
    private static void configureLogging() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger("root");

        String level = "DEBUG";

        if (SystemProperty.JTHEQUE_LOG.get() != null) {
            level = SystemProperty.JTHEQUE_LOG.get();
        }

        rootLogger.setLevel(Level.toLevel(level));
    }

    @Override
    public void collectionChosen() {
        viewService.closeCollectionView();
        collectionsService.removeCollectionListener(this);

        if(SwingUtils.isEDT()){
            ExecutorService executor = Executors.newSingleThreadExecutor();

            executor.submit(new Runnable(){
                @Override
                public void run() {
                    startSecondPhase();
                }
            });

            executor.shutdown();
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

        core.getLifeCycle().initTitle();

        messageService.loadMessages();

        views.displayConditionalViews();
    }
}