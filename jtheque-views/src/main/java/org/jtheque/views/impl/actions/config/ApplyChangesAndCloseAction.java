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

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to apply the changes of the current configuration panel and close the configuration view.
 *
 * @author Baptiste Wicht
 */
public final class ApplyChangesAndCloseAction extends JThequeAction {
    private final IConfigView configView;

    /**
     * Construct a new AcApplyChangesAndClose.
     * @param configView
     */
    public ApplyChangesAndCloseAction(IConfigView configView) {
        super("config.actions.ok");

	    this.configView = configView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (configView.validateContent()) {
            configView.getSelectedPanelConfig().apply();
            configView.closeDown();
        }
    }
}