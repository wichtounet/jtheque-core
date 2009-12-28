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
        if (CoreUtils.<IConfigView>getBean("configView").validateContent()) {
            CoreUtils.<IConfigView>getBean("configView").getSelectedPanelConfig().apply();
        }
    }
}