package org.jtheque.undo.impl;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.undo.IUndoRedoService;
import org.jtheque.undo.StateListener;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.collections.WeakEventListenerList;

import org.slf4j.LoggerFactory;

import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

/**
 * An undo-redo manager implementation.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class UndoRedoService extends UndoManager implements IUndoRedoService {
    private static final long serialVersionUID = 6050388256567189094L;

    private final WeakEventListenerList<StateListener> eventListenerList = WeakEventListenerList.create();

    @Override
    public boolean addEdit(UndoableEdit arg0) {
        boolean add = super.addEdit(arg0);

        fireStateChanged();

        return add;
    }

    @Override
    public void undo() {
        try {
            super.undo();
        } catch (CannotUndoException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        } finally {
            fireStateChanged();
        }
    }

    @Override
    public void redo() {
        try {
            super.redo();
        } catch (CannotUndoException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        } finally {
            fireStateChanged();
        }
    }

    @Override
    public void addStateListener(StateListener stateListener) {
        eventListenerList.add(stateListener);
    }

    @Override
    public void removeStateListener(StateListener stateListener) {
        eventListenerList.remove(stateListener);
    }

    /**
     * Update the state of the undo/redo action.
     */
    private void fireStateChanged() {
        String undoName = getUndoPresentationName();
        String redoName = getRedoPresentationName();
        boolean canRedo = canRedo();
        boolean canUndo = canUndo();

        for (StateListener stateListener : eventListenerList) {
            stateListener.stateChanged(undoName, canUndo, redoName, canRedo);
        }
    }
}