package org.jtheque.ui.utils.actions;

import org.jtheque.ui.able.IController;

import java.awt.event.ActionEvent;

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

public class ControllerAction extends JThequeAction {
    private final String key;
    private final transient IController controller;

    /**
     * Construct a new controller action.
     *
     * @param key The i18n key of the action. 
     * @param controller The controller to bind the action. 
     */
    public ControllerAction(String key, IController controller) {
        super(key);

        this.key = key;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.handleAction(key);
    }
}