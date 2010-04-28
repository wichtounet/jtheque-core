package org.jtheque.views.impl.actions.undo;

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

import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.Toolkit;

/**
 * An abstract undo action.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractAction extends JThequeAction {
    /**
     * Construct a new AbstractAction.
     *
     * @param key         The internationalization key.
     * @param accelerator The accelerator key.
     */
    AbstractAction(String key, int accelerator) {
        super(key);

        putValue(Action.ACCELERATOR_KEY
                , KeyStroke.getKeyStroke(accelerator, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }
}
