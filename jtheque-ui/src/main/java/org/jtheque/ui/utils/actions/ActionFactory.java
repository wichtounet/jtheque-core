package org.jtheque.ui.utils.actions;

import org.jtheque.ui.able.IController;
import org.jtheque.ui.able.IView;

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
 * A factory to create generic actions.
 *
 * @author Baptiste Wicht
 */
public final class ActionFactory {
    /**
     * Construct a new ActionFactory.
     */
    private ActionFactory() {
        super();
    }

    /**
     * Create an action to close the view.
     *
     * @param key  The i18n key.
     * @param view The view to close.
     *
     * @return An action to close the view.
     */
    public static JThequeAction createCloseViewAction(String key, IView view) {
        return new CloseViewAction(key, view);
    }

    /**
     * Create an action to display the view.
     *
     * @param key  The i18n key.
     * @param view The view to close.
     *
     * @return An action to close the view.
     */
    public static JThequeAction createDisplayViewAction(String key, IView view) {
        return new DisplayViewAction(key, view);
    }


    /**
     * Create an action linked to the controller.
     *
     * @param key The i18n key of the action.
     * @param controller The controller to bind the action to.
     *
     * @return The JThequeAction for this controller binding.
     */
    public static JThequeAction createControllerAction(String key, IController controller) {
        return new ControllerAction(key, controller);
    }
}