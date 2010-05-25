package org.jtheque.core.able.lifecycle;

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
 * A lifecyle specification. This class is responsible of the lifecyle of the core.
 *
 * @author Baptiste Wicht
 */
public interface ILifeCycle {
    /**
     * Exit the application.
     */
    void exit();

    /**
     * Restart the application.
     */
    void restart();

    /**
     * Return the current title of the application.
     *
     * @return The current title.
     */
    String getTitle();

    /**
     * Add title listener to receive title events from the application. If the listener is null, no exception is
     * thrown and no action is performed.
     *
     * @param listener The title listener.
     */
    void addTitleListener(TitleListener listener);

    /**
     * Remove the specified title listener so that it no longer receives title events from the application. If
     * the listener is null, no exception is thrown and no action is performed.
     *
     * @param listener The title listener.
     */
    void removeTitleListener(TitleListener listener);

    /**
     * Add function listener to receive function events from the application. If the listener is null, no exception
     * is thrown and no action is performed.
     *
     * @param listener The function listener.
     */
    void addFunctionListener(FunctionListener listener);

    /**
     * Remove the specified function listener so that it no longer receives function events from the application. If
     * the listener is null, no exception is thrown and no action is performed.
     *
     * @param listener The function listener.
     */
    void removeFunctionListener(FunctionListener listener);

    /**
     * Return the current function of the application.
     *
     * @return The internationalize key of the current function.
     */
    String getCurrentFunction();

    /**
     * Set the current function of the application.
     *
     * @param function The internationalized key of the new current function. S
     */
    void setCurrentFunction(String function);

    /**
     * Init the title.
     */
    void initTitle();
}