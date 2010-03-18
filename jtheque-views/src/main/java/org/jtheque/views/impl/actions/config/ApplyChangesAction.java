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

import org.jtheque.ui.utils.actions.JThequeAction;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewManager;
import org.jtheque.views.able.windows.IConfigView;

import java.awt.event.ActionEvent;

/**
 * An action to apply the changes of the current configuration panel.
 *
 * @author Baptiste Wicht
 */
public final class ApplyChangesAction extends JThequeAction {
    /**
     * Construct a new AcApplyChanges.
     */
    public ApplyChangesAction() {
        super("config.actions.apply");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IConfigView configView = ViewsServices.get(IViewManager.class).getViews().getConfigView();

        if (configView.validateContent()) {
            configView.getSelectedPanelConfig().apply();
        }
    }
}