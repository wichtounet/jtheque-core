package org.jtheque.views.impl.actions.config;

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

import org.jtheque.ui.utils.actions.JThequeSimpleAction;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewManager;
import org.jtheque.views.able.config.INetworkConfigView;

import java.awt.event.ActionEvent;

/**
 * An action to enable or disable the fields for proxy when the state of the checkbox change.
 *
 * @author Baptiste Wicht
 */
public final class CheckProxyAction extends JThequeSimpleAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        INetworkConfigView config = (INetworkConfigView) ViewsServices.get(IViewManager.class).getViews().getConfigView().getSelectedPanelConfig();

        boolean selected = config.getBoxProxy().isSelected();
        config.getFieldAddress().setEnabled(selected);
        config.getFieldPort().setEnabled(selected);
    }
}