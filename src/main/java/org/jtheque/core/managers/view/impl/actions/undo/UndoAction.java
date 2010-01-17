package org.jtheque.core.managers.view.impl.actions.undo;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.undo.IUndoRedoManager;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * An undo action.
 *
 * @author Baptiste Wicht
 */
public final class UndoAction extends AbstractAction {
    /**
     * Construct a new UndoAction.
     */
    public UndoAction() {
        super("undo.actions.undo", "undo", KeyEvent.VK_Z);

        Managers.getManager(IUndoRedoManager.class).setUndoAction(this);

        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Managers.getManager(IUndoRedoManager.class).undo();
    }
}
