package org.jtheque.core.managers.undo;

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

import org.jtheque.core.managers.ManagerException;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.resource.IResourceManager;

import javax.swing.Action;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

/**
 * An undo-redo manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class UndoRedoManager extends UndoManager implements IUndoRedoManager {
    private Action undoAction;
    private Action redoAction;

    private boolean enabled = true;

    @Override
    public void preInit() {
        //Nothing to do
    }

    @Override
    public void close(){
        //Nothing to do
    }

    @Override
    public void init() throws ManagerException {
        undoAction = Managers.getManager(IResourceManager.class).getAction("undoAction");
        redoAction = Managers.getManager(IResourceManager.class).getAction("redoAction");

        stateChanged();
    }


    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public synchronized boolean addEdit(UndoableEdit arg0) {
        boolean add = super.addEdit(arg0);

        stateChanged();

        return add;
    }

    @Override
    public synchronized void undo() {
        try {
            super.undo();
        } catch (CannotUndoException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        } finally {
            stateChanged();
        }
    }

    @Override
    public synchronized void redo() {
        try {
            super.redo();
        } catch (CannotUndoException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
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