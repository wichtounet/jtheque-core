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

import org.jtheque.core.managers.undo.IUndoRedoManager;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * An undo action.
 *
 * @author Baptiste Wicht
 */
public final class UndoAction extends AbstractAction {
    private static final long serialVersionUID = -586567020816762895L;

    @Resource
    private IUndoRedoManager undoRedoManager;

    /**
     * Construct a new UndoAction.
     */
    public UndoAction() {
        super("undo.actions.undo", "undo", KeyEvent.VK_Z);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        undoRedoManager.undo();
    }
}
