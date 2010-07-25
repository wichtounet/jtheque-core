package org.jtheque.ui.able;

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
 * A Swing controller. It simply handle an action name and execute the corresponding
 * action. It is associated to a view to manage its creation (normally via Spring Framework), this avoid
 * EDT violations due to creation of views out of the EDT from the Spring container. 
 *
 * @author Baptiste Wicht
 */
public interface IController<T extends IView> {
    /**
     * Handle the given action and execute the corresponding method. This method must be called in EDT to
     * works well.
     *
     * @param actionName The i18n action name.
     *
     * @throws ControllerException If the action cannot be executed
     */
    void handleAction(String actionName);

    /**
     * Return the view associated to the controller. This method must be called in EDT to works well.
     *
     * @return The view of the controller.
     */
    T getView();
}