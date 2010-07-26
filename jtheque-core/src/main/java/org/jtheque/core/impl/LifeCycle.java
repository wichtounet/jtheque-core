package org.jtheque.core.impl;

import org.jtheque.core.able.ICore;
import org.jtheque.core.able.lifecycle.FunctionEvent;
import org.jtheque.core.able.lifecycle.FunctionListener;
import org.jtheque.core.able.lifecycle.ILifeCycle;
import org.jtheque.core.able.lifecycle.TitleEvent;
import org.jtheque.core.able.lifecycle.TitleListener;
import org.jtheque.core.utils.SystemProperty;
import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.events.able.EventLevel;
import org.jtheque.events.able.IEventService;
import org.jtheque.events.utils.Event;
import org.jtheque.utils.io.FileUtils;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
 * The lifecycle implementation of the core.
 *
 * @author Baptiste Wicht
 */
public class LifeCycle implements ILifeCycle {
    private final WeakEventListenerList listenerList = new WeakEventListenerList();

    private String currentFunction;
    private String title = "JTheque";

    private final ICore core;
    private final IEventService eventService;

    private final ApplicationShutDownHook hook;

    /**
     * Construct a new LifeCycle.
     *
     * @param eventService The event service.
     * @param core         The core.
     */
    public LifeCycle(IEventService eventService, ICore core) {
        super();

        this.eventService = eventService;
        this.core = core;

        hook = new ApplicationShutDownHook();

        Runtime.getRuntime().addShutdownHook(hook);
    }

    @Override
    public void initTitle() {
        refreshTitle();
    }

    @Override
    public void exit() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                exit(0);
            }
        });

        executorService.shutdown();
    }

    @Override
    public void restart() {
        FileUtils.clearFolder(new File(SystemProperty.USER_DIR.get() + "cache"));

        exit(666);
    }

    /**
     * Exit from JTheque with the given exit code.
     *
     * @param code The exit code. 
     */
    private void exit(int code) {
        Runtime.getRuntime().removeShutdownHook(hook);

        addEventClose();

        Runtime.getRuntime().exit(code);
    }

    /**
     * Add the event to indicate the close of JTheque. 
     */
    private void addEventClose() {
        eventService.addEvent(IEventService.CORE_EVENT_LOG, Event.newEvent(EventLevel.INFO, "User", "events.close"));
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
     * This class is a hook on the shutdown of JTheque. When a shutdown is detected, the thread detect it and if this
     * isn't JTheque who has executed the close, we properly close the resources. This is for prevent accident kill of
     * JTheque.
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
            addEventClose();
        }
    }
}