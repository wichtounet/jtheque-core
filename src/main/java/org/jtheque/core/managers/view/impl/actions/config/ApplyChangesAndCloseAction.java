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
import org.jtheque.core.managers.view.impl.actions.JThequeAction;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to apply the changes of the current configuration panel and close the configuration view.
 *
 * @author Baptiste Wicht
 */
public final class ApplyChangesAndCloseAction extends JThequeAction {
    @Resource
    private IConfigView configView;

    /**
     * Construct a new AcApplyChangesAndClose.
     */
    public ApplyChangesAndCloseAction() {
        super("config.actions.ok");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (configView.validateContent()) {
            configView.getSelectedPanelConfig().apply();
            configView.closeDown();
        }
    }
}