package org.jtheque.lifecycle;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.jtheque.collections.CollectionListener;
import org.jtheque.collections.ICollectionsService;
import org.jtheque.core.ICore;
import org.jtheque.core.utils.SystemProperty;
import org.jtheque.core.application.Application;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.errors.IErrorManager;
import org.jtheque.events.EventLevel;
import org.jtheque.events.EventLog;
import org.jtheque.events.IEventManager;
import org.jtheque.lifecycle.application.XMLApplicationReader;
import org.jtheque.messages.IMessageManager;
import org.jtheque.modules.able.IModuleManager;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.update.IUpdateManager;
import org.jtheque.views.able.ISplashManager;
import org.jtheque.views.able.IViewManager;
import org.jtheque.ui.utils.edt.SimpleTask;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.LoggerFactory;

import java.util.List;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

public class LifeCycleActivator implements BundleActivator, CollectionListener {
    private BundleContext context;

    @Override
    public void start(BundleContext context) throws Exception {
        this.context = context;

        configureLogging();

        Application application = new XMLApplicationReader().readApplication(SystemProperty.USER_DIR.get() + "/application.xml");

        getService(ICore.class).setApplication(application);

        getService(ISplashManager.class).displaySplashScreen();

        getService(IEventManager.class).addEventLog("JTheque Core", new EventLog(EventLevel.INFO, "User", "events.start"));

        OSGiUtils.getBundle(context, "jtheque-modules").start();

        getService(IModuleManager.class).load();

        if(OSGiUtils.getService(context, IModuleManager.class).hasCollectionModule()){
            OSGiUtils.getBundle(context, "jtheque-collections").start();

            getService(IUIUtils.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    getService(ISplashManager.class).closeSplashScreen();
                    getService(ICollectionsService.class).addCollectionListener(LifeCycleActivator.this);
                    getService(IViewManager.class).displayCollectionView();
                }
            });
        } else {
            startSecondPhase();
        }
    }

    private void configureLogging() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger("root");

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
        OSGiUtils.getService(context, IModuleManager.class).plugModules();

        getService(ICore.class).getLifeCycle().initTitle();

        getService(ISplashManager.class).closeSplashScreen();
        getService(ISplashManager.class).fillMainView();

        getService(IErrorManager.class).displayErrors();

        getService(IMessageManager.class).loadMessages();

        if(getService(IMessageManager.class).isDisplayNeeded()){
            getService(IViewManager.class).getViews().getMessagesView().display();
        }

        if (getService(ICore.class).getConfiguration().verifyUpdateOnStartup()) {
            List<String> messages = getService(IUpdateManager.class).getPossibleUpdates();

            for(String message : messages){
                if(getService(IUIUtils.class).askI18nUserForConfirmation(message, message + ".title")){
                    getService(IViewManager.class).getViews().getModuleView().display();
                    break;
                }
            }
        }
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        //Release all
    }
}