package org.jtheque.core.impl;

import org.jtheque.core.able.Core;
import org.jtheque.core.able.lifecycle.FunctionListener;
import org.jtheque.core.able.lifecycle.TitleListener;
import org.jtheque.core.utils.SystemProperty;
import org.jtheque.events.able.EventLevel;
import org.jtheque.events.able.EventService;
import org.jtheque.events.able.Events;
import org.jtheque.utils.ThreadUtils;
import org.jtheque.utils.collections.WeakEventListenerList;
import org.jtheque.utils.io.FileUtils;

import java.io.File;

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
public class LifeCycleImpl implements org.jtheque.core.able.lifecycle.LifeCycle {
    private final WeakEventListenerList<FunctionListener> functionListeners = WeakEventListenerList.create();
    private final WeakEventListenerList<TitleListener> titleListeners = WeakEventListenerList.create();

    private String currentFunction;
    private String title = "JTheque";

    private final Core core;
    private final EventService eventService;

    private final ApplicationShutDownHook hook;

    /**
     * Construct a new LifeCycleImpl.
     *
     * @param eventService The event service.
     * @param core         The core.
     */
    public LifeCycleImpl(EventService eventService, Core core) {
        super();

        this.eventService = eventService;
        this.core = core;

        hook = new ApplicationShutDownHook();

        Runtime.getRuntime().addShutdownHook(hook);
    }

    @Override
    public void exit() {
        ThreadUtils.inNewThread(new Runnable() {
            @Override
            public void run() {
                exit(0);
            }
        });
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
        eventService.addEvent(Events.newEvent(EventLevel.INFO, "User", "events.close", EventService.CORE_EVENT_LOG));
    }

    @Override
    public void setCurrentFunction(String function) {
        currentFunction = function;

        fireFunctionUpdated(function);

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
            titleListeners.add(listener);
        }
    }

    @Override
    public void removeTitleListener(TitleListener listener) {
        if (listener != null) {
            titleListeners.remove(listener);
        }
    }

    @Override
    public void addFunctionListener(FunctionListener listener) {
        if (listener != null) {
            functionListeners.add(listener);
        }
    }

    @Override
    public void removeFunctionListener(FunctionListener listener) {
        if (listener != null) {
            functionListeners.remove(listener);
        }
    }
    
    @Override
    public void refreshTitle() {
        updateTitle();

        fireTitleUpdated(title);
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

        builder.append(core.getApplication().getI18nProperties().getName());
        builder.append(' ');
        builder.append(core.getApplication().getVersion());

        title = builder.toString();
    }

    /**
     * Fire a titleUpdated event. This method avert the listeners that the title of the application has changed.
     *
     * @param title The new title.
     */
    private void fireTitleUpdated(String title) {
        for (TitleListener listener : titleListeners) {
            listener.titleUpdated(title);
        }
    }

    /**
     * Fire a functionUpdated event. This method avert the listeners that the current function has changed.
     *
     * @param function The new function.
     */
    private void fireFunctionUpdated(String function) {
        for (FunctionListener listener : functionListeners) {
            listener.functionUpdated(function);
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