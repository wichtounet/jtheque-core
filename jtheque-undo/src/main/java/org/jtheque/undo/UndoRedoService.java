package org.jtheque.undo;

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

import org.slf4j.LoggerFactory;

import javax.swing.Action;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

/**
 * An undo-redo manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class UndoRedoService extends UndoManager implements IUndoRedoService {
    private Action undoAction;
    private Action redoAction;

    public UndoRedoService() {
        super();

        stateChanged();
    }

    @Override
    public synchronized boolean addEdit(UndoableEdit arg0) {
        boolean add = super.addEdit(arg0);

        stateChanged();

        return add;
    }

    @Override
    public void setUndoAction(Action undoAction){
        this.undoAction = undoAction;
    }

    @Override
    public void setRedoAction(Action redoAction){
        this.redoAction = redoAction;
    }

    @Override
    public synchronized void undo() {
        try {
            super.undo();
        } catch (CannotUndoException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        } finally {
            stateChanged();
        }
    }

    @Override
    public synchronized void redo() {
        try {
            super.redo();
        } catch (CannotUndoException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        } finally {
            stateChanged();
        }
    }

    /**
     * Update the state of the undo/redo action.
     */
    private void stateChanged() {
        undoAction.putValue(Action.NAME, getUndoPresentationName());
        redoAction.putValue(Action.NAME, getRedoPresentationName());
        undoAction.setEnabled(canUndo());
        redoAction.setEnabled(canRedo());
    }
}