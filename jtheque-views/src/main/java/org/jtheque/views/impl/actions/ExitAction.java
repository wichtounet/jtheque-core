package org.jtheque.views.impl.actions;

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

import org.jtheque.core.ICore;
import org.jtheque.ui.utils.actions.JThequeAction;

import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * An action to exit of the application.
 *
 * @author Baptiste Wicht
 */
public final class ExitAction extends JThequeAction {
    private final ICore core;

    /**
     * Construct a new ExitAction.
     */
    public ExitAction(ICore core) {
        super("menu.exit", core.getApplication().getName());

        this.core = core;

        putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        core.getLifeCycle().exit();
    }
}