package org.jtheque.core.lifecycle;

import org.jtheque.core.CoreServices;
import org.jtheque.core.ICore;
import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.i18n.Internationalizable;
import org.jtheque.utils.DesktopUtils;

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

public class LifeCycle implements ILifeCycle {
    private final WeakEventListenerList listenerList = new WeakEventListenerList();

    private final Thread shutdownHook = new ApplicationShutDownHook();

    private String currentFunction;
    private String title = "JTheque";

    private final ICore core;

    public LifeCycle(ICore core) {
        super();

        this.core = core;
    }

    @Override
    public void initTitle() {
        refreshTitle();
        
        CoreServices.get(ILanguageService.class).addInternationalizable(new TitleUpdater());
    }

    @Override
    public void exit() {
        releaseAll();

        closeVM();
    }

    @Override
    public void restart() {
        releaseAll();

        DesktopUtils.open(core.getFiles().getLauncherFile());

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

    /**
     * Start the exit process but not stop the application.
     */
    private void releaseAll() {
        //TODO : Managers.getManager(IEventManager.class).addEventLog("JTheque Core", new EventLog(EventLevel.INFO, "User", "events.close"));
    }

    /**
     * Close the VM.
     */
    private void closeVM() {
        Runtime.getRuntime().removeShutdownHook(shutdownHook);

        //TODO : Quitter le serveur
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

        builder.append(core.getApplication().getName());
        builder.append(' ');
        builder.append(core.getApplication().getVersion());

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