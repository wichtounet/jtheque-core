package org.jtheque.views.impl.actions.undo;

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

import org.jtheque.i18n.LanguageService;
import org.jtheque.undo.IUndoRedoService;
import org.jtheque.undo.StateListener;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * An undo action.
 *
 * @author Baptiste Wicht
 */
public final class UndoAction extends AbstractAction implements StateListener {
    private static final long serialVersionUID = -7472749854626200137L;

    private final transient IUndoRedoService undoRedoService;
    private final transient LanguageService languageService;

    /**
     * Construct a new UndoAction.
     *
     * @param undoRedoService The undo redo service.
     * @param languageService The language service.
     */
    public UndoAction(IUndoRedoService undoRedoService, LanguageService languageService) {
        super("undo.actions.undo", KeyEvent.VK_Z);

        this.undoRedoService = undoRedoService;
        this.languageService = languageService;

        undoRedoService.addStateListener(this);

        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        undoRedoService.undo();
    }

    @Override
    public void stateChanged(String undoName, boolean canUndo, String redoName, boolean canRedo) {
        setText(languageService.getMessage(undoName));
        setEnabled(canUndo);
    }
}
