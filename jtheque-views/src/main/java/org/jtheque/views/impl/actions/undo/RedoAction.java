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

import org.jtheque.undo.IUndoRedoService;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * An redo action.
 *
 * @author Baptiste Wicht
 */
public final class RedoAction extends AbstractAction {
    private final IUndoRedoService undoRedoService;

    /**
     * Construct a new RedoAction.
     */
    public RedoAction(IUndoRedoService undoRedoService) {
        super("undo.actions.redo", KeyEvent.VK_Y);

        this.undoRedoService = undoRedoService;

        undoRedoService.setRedoAction(this);

        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        undoRedoService.redo();
    }
}
