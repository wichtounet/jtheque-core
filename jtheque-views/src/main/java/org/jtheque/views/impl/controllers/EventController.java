package org.jtheque.views.impl.controllers;

import org.jtheque.ui.able.Action;
import org.jtheque.ui.utils.AbstractController;
import org.jtheque.views.able.windows.IEventView;

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
 * The controller of the event view.
 *
 * @author Baptiste Wicht
 */
public class EventController extends AbstractController<IEventView> {
    /**
     * Construct a new EventController. 
     */
    public EventController() {
        super(IEventView.class);
    }

    /**
     * Update the event view.
     */
    @Action("log.view.actions.update")
    public void update() {
        getView().refresh();
    }
}