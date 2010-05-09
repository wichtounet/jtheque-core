package org.jtheque.core.lifecycle;

import org.jtheque.core.ICore;
import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.events.EventLevel;
import org.jtheque.events.Event;
import org.jtheque.events.IEventService;
import org.jtheque.utils.DesktopUtils;
import org.osgi.framework.BundleContext;

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

public class LifeCycle implements ILifeCycle {
    private final WeakEventListenerList listenerList = new WeakEventListenerList();

    private final Thread shutdownHook = new ApplicationShutDownHook();

    private String currentFunction;
    private String title = "JTheque";

    private final ICore core;
    private final IEventService eventService;

    public LifeCycle(IEventService eventService, ICore core) {
        super();

        this.eventService = eventService;
        this.core = core;
    }

    @Override
    public void initTitle() {
        refreshTitle();
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
        eventService.addEvent("JTheque Core", new Event(EventLevel.INFO, "User", "events.close"));
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