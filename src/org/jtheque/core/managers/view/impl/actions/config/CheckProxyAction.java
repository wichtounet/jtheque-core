package org.jtheque.core.managers.view.impl.actions.config;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.core.managers.view.able.IConfigView;
import org.jtheque.core.managers.view.able.config.INetworkConfigView;
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to enable or disable the fields for proxy when the state of the checkbox change.
 *
 * @author Baptiste Wicht
 */
public final class CheckProxyAction extends JThequeSimpleAction {
    private static final long serialVersionUID = -5097742091545119996L;

    @Resource
    private IConfigView configView;

    @Override
    public void actionPerformed(ActionEvent e) {
        INetworkConfigView config = (INetworkConfigView) configView.getSelectedPanelConfig();

        boolean selected = config.getBoxProxy().isSelected();
        config.getFieldAddress().setEnabled(selected);
        config.getFieldPort().setEnabled(selected);
    }
}