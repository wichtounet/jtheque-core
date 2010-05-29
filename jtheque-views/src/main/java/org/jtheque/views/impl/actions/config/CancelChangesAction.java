package org.jtheque.views.impl.actions.config;

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

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.able.windows.IConfigView;

import java.awt.event.ActionEvent;

/**
 * An action to cancel the changes of the current configuration panel.
 *
 * @author Baptiste Wicht
 */
public final class CancelChangesAction extends JThequeAction {
    private final IConfigView configView;

    /**
     * Construct a new AcCancelChanges.
     *
     * @param configView The config view.
     */
    public CancelChangesAction(IConfigView configView) {
        super("config.actions.cancel");

        this.configView = configView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        configView.getSelectedPanelConfig().cancel();
        configView.closeDown();
    }
}