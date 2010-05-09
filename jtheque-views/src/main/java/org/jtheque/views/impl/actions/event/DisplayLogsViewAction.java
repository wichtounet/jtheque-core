package org.jtheque.views.impl.actions.event;

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.IViews;

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

public class DisplayLogsViewAction extends JThequeAction {
    private final IViews views;

    public DisplayLogsViewAction(IViews views) {
        super("log.view.actions.display");

        this.views = views;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        views.getEventView().display();
    }
}