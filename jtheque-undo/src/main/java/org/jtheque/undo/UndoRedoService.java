package org.jtheque.undo;

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

import org.jtheque.i18n.ILanguageService;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
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

    private ILanguageService languageService;

    public UndoRedoService(ILanguageService languageService) {
        super();

        this.languageService = languageService;

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

        stateChanged();
    }

    @Override
    public void setRedoAction(Action redoAction){
        this.redoAction = redoAction;

        stateChanged();
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
        if(undoAction != null){
            undoAction.putValue(Action.NAME, languageService.getMessage(getUndoPresentationName()));
            undoAction.setEnabled(canUndo());
        }

        if(redoAction != null){
            redoAction.putValue(Action.NAME, languageService.getMessage(getRedoPresentationName()));
            redoAction.setEnabled(canRedo());
        }
    }
}