package org.jtheque.views.impl.controllers;

import org.jtheque.ui.utils.AbstractController;
import org.jtheque.views.able.windows.IEventView;

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

public class EventController extends AbstractController {
    @Resource
    private IEventView eventView;

    /**
     * Update the event view. 
     */
    private void update() {
        eventView.refresh();
    }

    @Override
    protected Map<String, String> getTranslations() {
        Map<String, String> translations = new HashMap<String, String>(1);

        translations.put("log.view.actions.update", "update");

        return translations;
    }
}