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

import javax.swing.Action;
import javax.swing.undo.UndoableEdit;

/**
 * An undo-redo manager specification.
 *
 * @author Baptiste Wicht
 */
public interface IUndoRedoManager {
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
     * @return true if the action has been added else false.
     */
    boolean addEdit(UndoableEdit edit);

    /**
     * Set the undo action.
     *
     * @param undoAction The undo action.
     */
    void setUndoAction(Action undoAction);

    /**
     * Set the redo action.
     *
     * @param redoAction The redo action.
     */
    void setRedoAction(Action redoAction);
}