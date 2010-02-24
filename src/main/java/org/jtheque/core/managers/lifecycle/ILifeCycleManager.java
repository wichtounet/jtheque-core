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

import org.jtheque.core.managers.lifecycle.listeners.FunctionListener;
import org.jtheque.core.managers.lifecycle.listeners.TitleListener;

/**
 * A JTheque application.
 *
 * @author Baptiste Wicht
 */
public interface ILifeCycleManager {
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
     * Choose a collection.
     *
     * @param name     The name of the collection.
     * @param password The password of the collection.
     * @param create   A boolean flag indicating if we must create the collection or not.
     */
    void chooseCollection(String name, String password, boolean create);

    /**
     * Launch the next phase.
     */
    void launchNextPhase();

    /**
     * Init the title.
     */
    void initTitle();

    /**
     * Init the cycles.
     */
    void initCycles();
}