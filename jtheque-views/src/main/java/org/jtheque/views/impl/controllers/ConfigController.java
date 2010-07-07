package org.jtheque.views.impl.controllers;

import org.jtheque.ui.utils.AbstractController;
import org.jtheque.views.able.windows.IConfigView;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

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
public class ConfigController extends AbstractController {
    @Resource
    private IConfigView configView;

    /**
     * Apply the changes.
     */
    private void apply(){
        if (configView.validateContent()) {
            configView.getSelectedPanelConfig().apply();
        }
    }

    /**
     * Apply the changes and close the view.
     */
    private void applyClose(){
        if (configView.validateContent()) {
            configView.getSelectedPanelConfig().apply();
            configView.closeDown();
        }
    }

    /**
     * Cancel the changes. 
     */
    private void cancel(){
        configView.getSelectedPanelConfig().cancel();
        configView.closeDown();
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(3);

        translations.put("config.actions.ok", "applyClose");
        translations.put("config.actions.apply", "apply");
        translations.put("config.actions.cancel", "cancel");

        return translations;
    }
}