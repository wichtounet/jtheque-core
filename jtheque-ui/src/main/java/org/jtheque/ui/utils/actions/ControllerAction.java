package org.jtheque.ui.utils.actions;

import org.jtheque.ui.able.Controller;

import javax.swing.ImageIcon;

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

/**
 * A controller action. It's an action bound to a controller.
 *
 * @author Baptiste Wicht
 */
public final class ControllerAction extends JThequeAction {
    private static final long serialVersionUID = -749830303290101705L;
    
    private final String key;
    private final transient Controller<?> controller;

    /**
     * Construct a new controller action.
     *
     * @param key        The i18n key of the action.
     * @param controller The controller to bind the action.
     */
    public ControllerAction(String key, Controller<?> controller) {
        super(key);

        this.key = key;
        this.controller = controller;
    }

    /**
     * Construct a new controller action.
     *
     * @param key        The i18n key of the action.
     * @param icon       The icon of the action
     * @param controller The controller to bind the action.
     */
    public ControllerAction(String key, ImageIcon icon, Controller<?> controller) {
        super();

        setIcon(icon);

        this.key = key;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.handleAction(key);
    }
}