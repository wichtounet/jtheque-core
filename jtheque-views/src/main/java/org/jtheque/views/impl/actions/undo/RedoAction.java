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

import org.jtheque.undo.able.IUndoRedoService;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * An redo action.
 *
 * @author Baptiste Wicht
 */
public final class RedoAction extends AbstractAction {
    private static final long serialVersionUID = 3698122235572353522L;

    private final transient IUndoRedoService undoRedoService;

    /**
     * Construct a new RedoAction.
     *
     * @param undoRedoService The undo redo service.
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
