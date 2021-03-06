package org.jtheque.views.impl.controllers;

import org.jtheque.ui.Action;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.views.windows.ConfigView;

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
 * A controller for the config view.
 *
 * @author Baptiste Wicht
 */
public class ConfigController extends AbstractController<ConfigView> {
    /**
     * Construct a new ConfigController. 
     */
    public ConfigController() {
        super(ConfigView.class);
    }

    /**
     * Apply the changes.
     */
    @Action("config.actions.apply")
    public void apply() {
        if (getView().validateContent()) {
            getView().getSelectedPanelConfig().apply();
        }
    }

    /**
     * Apply the changes and close the view.
     */
    @Action("config.actions.ok")
    public void applyClose() {
        if (getView().validateContent()) {
            getView().getSelectedPanelConfig().apply();
            getView().closeDown();
        }
    }

    /**
     * Cancel the changes.
     */
    @Action("config.actions.cancel")
    public void cancel() {
        getView().getSelectedPanelConfig().cancel();
        getView().closeDown();
    }
}