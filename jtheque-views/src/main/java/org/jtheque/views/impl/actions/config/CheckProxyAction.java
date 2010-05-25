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

import org.jtheque.ui.utils.actions.JThequeSimpleAction;
import org.jtheque.views.able.config.INetworkConfigView;

import java.awt.event.ActionEvent;

/**
 * An action to enable or disable the fields for proxy when the state of the checkbox change.
 *
 * @author Baptiste Wicht
 */
public final class CheckProxyAction extends JThequeSimpleAction {
    private final INetworkConfigView configView;

	/**
	 * Construct a new CheckProxyAction.
	 *
	 * @param configView The config view. 
	 */
    public CheckProxyAction(INetworkConfigView configView) {
        super();

        this.configView = configView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean selected = configView.getBoxProxy().isSelected();
        configView.getFieldAddress().setEnabled(selected);
        configView.getFieldPort().setEnabled(selected);
    }
}