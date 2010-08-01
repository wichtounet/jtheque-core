package org.jtheque.undo.able;

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

import org.jtheque.utils.annotations.ThreadSafe;

import javax.swing.undo.UndoableEdit;

/**
 * An undo-redo manager specification.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public interface IUndoRedoService {
    /**
     * Undo the last action.
     */
    void undo();

    /**
     * Redo the last action that has been undo.
     */
    void redo();

    /**
     * Add an <code>UndoableEdit</code>.
     *
     * @param edit The edit to add.
     *
     * @return true if the action has been added else false.
     */
    boolean addEdit(UndoableEdit edit);

    /**
     * Add the state listener.
     *
     * @param stateListener The state listener.
     */
    void addStateListener(StateListener stateListener);

    /**
     * Remove the state listener.
     *
     * @param stateListener The state listener to remove.
     */
    void removeStateListener(StateListener stateListener);
}