package org.jtheque.views.impl.actions.event;

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.windows.IEventView;

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
 * An action to org.jtheque.update the event view.
 *
 * @author Baptiste Wicht
 */
public final class UpdateAction extends JThequeAction {
    private final IEventView eventView;

    /**
     * Construct a new UpdateAction.
     *
     * @param eventView The log view.
     */
    public UpdateAction(IEventView eventView) {
        super("log.view.actions.update");

        this.eventView = eventView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        eventView.refresh();
    }
}