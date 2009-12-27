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
import org.jtheque.core.utils.CoreUtils;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to cancel the changes of the current configuration panel.
 *
 * @author Baptiste Wicht
 */
public final class CancelChangesAction extends JThequeAction {
    /**
     * Construct a new AcCancelChanges.
     */
    public CancelChangesAction() {
        super("config.actions.cancel");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IConfigView configView = CoreUtils.getBean("configView");

        configView.getSelectedPanelConfig().cancel();
        configView.closeDown();
    }
}