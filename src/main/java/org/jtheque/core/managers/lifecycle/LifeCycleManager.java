package org.jtheque.core.managers.lifecycle;

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

import org.jdesktop.swingx.event.WeakEventListenerList;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.beans.ioc.Ioc;
import org.jtheque.core.managers.collection.ICollectionsService;
import org.jtheque.core.managers.event.EventLevel;
import org.jtheque.core.managers.event.EventLog;
import org.jtheque.core.managers.event.IEventManager;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.language.Internationalizable;
import org.jtheque.core.managers.lifecycle.listeners.FunctionEvent;
import org.jtheque.core.managers.lifecycle.listeners.FunctionListener;
import org.jtheque.core.managers.lifecycle.listeners.TitleEvent;
import org.jtheque.core.managers.lifecycle.listeners.TitleListener;
import org.jtheque.core.managers.lifecycle.phases.IPhasesManager;
import org.jtheque.core.managers.log.LoggerConfigurator;
import org.jtheque.core.managers.module.IModuleManager;
import org.jtheque.core.managers.module.loaders.ModuleLoader;
import org.jtheque.core.managers.view.SplashManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.utils.DesktopUtils;

/**
 * A JTheque application implementation. This class manage the cycle life of JTheque.
 *
 * @author Baptiste Wicht
 */
public final class LifeCycleManager implements ILifeCycleManager, Internationalizable {
    private final WeakEventListenerList listenerList = new WeakEventListenerList();

    private final Thread shutdownHook = new ApplicationShutDownHook();

    private String currentFunction;
    private String title = "JTheque";

    private final Instances instances = new Instances();

    private IPhasesManager phasesManager;

    @Override
    public void initCycles() {
        registerApplication();

        SplashManager.getInstance().displaySplashScreen();

        LoggerConfigurator.configure();

        ModuleLoader.loadModules();

        Ioc.getContainer().loadContext();

        phasesManager = (IPhasesManager) Ioc.getContainer().getApplicationContext().getBean("phasesManager");
    }

    /**
     * Register the application.
     */
    private void registerApplication() {
        instances.launchApplication();

        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    @Override
    public void launchNextPhase() {
        phasesManager.launchNextPhase();
    }

    @Override
    public void initTitle() {
        refreshTitle();
        Managers.getManager(ILanguageManager.class).addInternationalizable(new TitleUpdater());
    }

    @Override
    public void exit() {
        releaseAll();

        closeVM();
    }

    @Override
    public void restart() {
        releaseAll();

        DesktopUtils.open(Managers.getCore().getFiles().getLauncherFile());

        closeVM();
    }

    @Override
    public void setCurrentFunction(String function) {
        currentFunction = function;

        fireFunctionUpdated(new FunctionEvent(this, function));

        refreshTitle();
    }

    @Override
    public String getCurrentFunction() {
        return currentFunction;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void addTitleListener(TitleListener listener) {
        if (listener != null) {
            listenerList.add(TitleListener.class, listener);
        }
    }

    @Override
    public void removeTitleListener(TitleListener listener) {
        if (listener != null) {
            listenerList.remove(TitleListener.class, listener);
        }
    }

    @Override
    public void addFunctionListener(FunctionListener listener) {
        if (listener != null) {
            listenerList.add(FunctionListener.class, listener);
        }
    }

    @Override
    public void removeFunctionListener(FunctionListener listener) {
        if (listener != null) {
            listenerList.remove(FunctionListener.class, listener);
        }
    }

    @Override
    public void chooseCollection(String collection, String password, boolean create) {
        final ICollectionsService service = Managers.getManager(IBeansManager.class).getBean(ICollectionsService.class);

        boolean chosen = service.chooseCollection(collection, password, create);

        if (chosen) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Managers.getManager(IViewManager.class).getSplashManager().displaySplashScreen();

                    Managers.getManager(IModuleManager.class).plugModules();

                    launchNextPhase();
                }
            }).start();
        } else {
            Managers.getManager(IViewManager.class).getCollectionView().setErrorMessage(Managers.getManager(ILanguageManager.class).getMessage("error.module.collection"));
        }
    }

    /**
     * Start the exit process but not stop the application.
     */
    private void releaseAll() {
        Managers.getManager(IEventManager.class).addEventLog("JTheque Core", new EventLog(EventLevel.INFO, "User", "events.close"));

        if (phasesManager != null && phasesManager.isDone()) {
            Managers.getManager(IModuleManager.class).unplugModules();
        }

        Managers.closeManagers();

        if (instances != null) {
            instances.closeInstance();
        }
    }

    /**
     * Close the VM.
     */
    private void closeVM() {
        Runtime.getRuntime().removeShutdownHook(shutdownHook);
        System.exit(0);
    }

    /**
     * Refresh the title of the application. Call this method when the title is subject to change.
     */
    private void refreshTitle() {
        updateTitle();

        fireTitleUpdated(new TitleEvent(this, title));
    }

    /**
     * Update the title.
     */
    private void updateTitle() {
        StringBuilder builder = new StringBuilder(50);

        if (currentFunction != null && !currentFunction.isEmpty()) {
            builder.append(currentFunction);
            builder.append(" - ");
        }

        builder.append(Managers.getCore().getApplication().getName());
        builder.append(' ');
        builder.append(Managers.getCore().getApplication().getVersion());

        title = builder.toString();
    }

    /**
     * Fire a titleUpdated event. This method avert the listeners that the title of the application has changed.
     *
     * @param event The title event.
     */
    private void fireTitleUpdated(TitleEvent event) {
        TitleListener[] listeners = listenerList.getListeners(TitleListener.class);

        for (TitleListener listener : listeners) {
            listener.titleUpdated(event);
        }
    }

    /**
     * Fire a functionUpdated event. This method avert the listeners that the current function has changed.
     *
     * @param event The function event.
     */
    private void fireFunctionUpdated(FunctionEvent event) {
        FunctionListener[] listeners = listenerList.getListeners(FunctionListener.class);

        for (FunctionListener listener : listeners) {
            listener.functionUpdated(event);
        }
    }

    /* Listeners methods */

    @Override
    public void refreshText() {
        refreshTitle();
    }

    /**
     * An updater to reflect the locale in the title when a change occurs.
     *
     * @author Baptiste Wicht
     */
    private final class TitleUpdater implements Internationalizable {
        @Override
        public void refreshText() {
            refreshTitle();
        }
    }

    /**
     * This class is a hook on the shutdown of JTheque. When a shutdown is detected, the thread
     * detect it and if this isn't JTheque who has executed the close, we properly close the
     * resources. This is for prevent accident kill of JTheque.
     *
     * @author Baptiste Wicht
     */
    private final class ApplicationShutDownHook extends Thread {
        /**
         * Construct a new <code>ApplicationShutDownHook</code>.
         */
        ApplicationShutDownHook() {
            super();
        }

        @Override
        public void run() {
            exit();
        }
    }
}