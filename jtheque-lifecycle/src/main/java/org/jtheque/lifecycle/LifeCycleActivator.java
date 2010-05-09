package org.jtheque.lifecycle;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.jtheque.collections.CollectionListener;
import org.jtheque.collections.ICollectionsService;
import org.jtheque.core.ICore;
import org.jtheque.core.utils.SystemProperty;
import org.jtheque.core.application.Application;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.errors.IErrorService;
import org.jtheque.events.EventLevel;
import org.jtheque.events.Event;
import org.jtheque.events.IEventService;
import org.jtheque.lifecycle.application.XMLApplicationReader;
import org.jtheque.messages.IMessageService;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.update.IUpdateService;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.ISplashService;
import org.jtheque.views.able.IViewService;
import org.jtheque.utils.ui.edt.SimpleTask;
import org.jtheque.views.able.IViews;
import org.jtheque.views.impl.MacOSXConfiguration;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.slf4j.LoggerFactory;

import java.util.List;

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

public class LifeCycleActivator implements BundleActivator, CollectionListener {
    private BundleContext context;

    @Override
    public void start(BundleContext context) throws BundleException {
        this.context = context;

        configureLogging();

        Application application = new XMLApplicationReader().readApplication(SystemProperty.USER_DIR.get() + "/application.xml");

        getService(ICore.class).setApplication(application);

        MacOSXConfiguration.configureForMac(getService(IViewService.class), getService(ICore.class), getService(IViews.class));

        getService(ISplashService.class).initViews();
        getService(ISplashService.class).displaySplashScreen();
        
        getService(IViews.class).init();

        getService(IEventService.class).addEvent("JTheque Core", new Event(EventLevel.INFO, "User", "events.start"));

        getService(IModuleService.class).load();

        if(OSGiUtils.getService(context, IModuleService.class).hasCollectionModule()){
            SwingUtils.execute(new SimpleTask() {
                @Override
                public void run() {
                    getService(ISplashService.class).closeSplashScreen();
                    getService(ICollectionsService.class).addCollectionListener(LifeCycleActivator.this);
                    getService(IViewService.class).displayCollectionView();
                }
            });
        } else {
            startSecondPhase();
        }
    }

    private static void configureLogging() {
        Logger rootLogger = (Logger)LoggerFactory.getLogger("root");

        String level = "ERROR";

        if (SystemProperty.JTHEQUE_LOG.get() != null) {
            level = SystemProperty.JTHEQUE_LOG.get();
        }

        rootLogger.setLevel(Level.toLevel(level));
    }

    private <T> T getService(Class<T> classz) {
        return OSGiUtils.getService(context, classz);
    }

    @Override
    public void collectionChoosed() {
        startSecondPhase();

        getService(ICollectionsService.class).removeCollectionListener(this);
    }

    private void startSecondPhase() {
        OSGiUtils.getService(context, IModuleService.class).plugModules();

        getService(ICore.class).getLifeCycle().initTitle();

        getService(ISplashService.class).closeSplashScreen();
        getService(ISplashService.class).fillMainView();

        getService(IErrorService.class).displayErrors();

        getService(IMessageService.class).loadMessages();

        if(getService(IMessageService.class).isDisplayNeeded()){
            getService(IViews.class).getMessagesView().display();
        }

        if (getService(ICore.class).getConfiguration().verifyUpdateOnStartup()) {
            List<String> messages = getService(IUpdateService.class).getPossibleUpdates();

            for(String message : messages){
                if(getService(IUIUtils.class).askI18nUserForConfirmation(message, message + ".title")){
                    getService(IViews.class).getModuleView().display();
                    break;
                }
            }
        }
    }

    @Override
    public void stop(BundleContext bundleContext){
        //Release all
    }
}