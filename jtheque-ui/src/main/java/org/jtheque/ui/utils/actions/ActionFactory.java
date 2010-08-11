package org.jtheque.ui.utils.actions;

import org.jtheque.ui.Controller;

import javax.swing.Action;
import javax.swing.ImageIcon;

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
     * Create an action linked to the controller.
     *
     * @param key        The i18n key of the action.
     * @param controller The controller to bind the action to.
     *
     * @return The JThequeAction for this controller binding.
     */
    public static JThequeAction createAction(String key, Controller<?> controller) {
        return new ControllerAction(key, controller);
    }


    /**
     * Create an action linked to the controller.
     *
     * @param key        The i18n key of the action.
     * @param icon       The icon of the action
     * @param controller The controller to bind the action to.
     *
     * @return The JThequeAction for this controller binding.
     */
    public static Action createAction(String key, ImageIcon icon, Controller<?> controller) {
        return new ControllerAction(key, icon, controller);
    }
}