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

import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.undo.able.IUndoRedoService;
import org.jtheque.undo.able.StateListener;
import org.jtheque.utils.annotations.ThreadSafe;

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

    private final WeakEventListenerList eventListenerList = new WeakEventListenerList();

    @Override
    public synchronized boolean addEdit(UndoableEdit arg0) {
        boolean add = super.addEdit(arg0);

        fireStateChanged();

        return add;
    }

    @Override
    public synchronized void undo() {
        try {
            super.undo();
        } catch (CannotUndoException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        } finally {
            fireStateChanged();
        }
    }

    @Override
    public synchronized void redo() {
        try {
            super.redo();
        } catch (CannotUndoException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        } finally {
            fireStateChanged();
        }
    }

    @Override
    public synchronized void addStateListener(StateListener stateListener) {
        eventListenerList.add(StateListener.class, stateListener);
    }

    @Override
    public synchronized void removeStateListener(StateListener stateListener) {
        eventListenerList.remove(StateListener.class, stateListener);
    }

    /**
     * Update the state of the undo/redo action.
     */
    private synchronized void fireStateChanged() {
        StateListener[] listeners = eventListenerList.getListeners(StateListener.class);

        for (StateListener stateListener : listeners) {
            stateListener.stateChanged(
                    getUndoPresentationName(), canUndo(),
                    getRedoPresentationName(), canRedo());
        }
    }
}