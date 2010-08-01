package org.jtheque.undo.able;

import java.util.EventListener;

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

/**
 * A state listener for the undo-redo service.
 *
 * @author Baptiste Wicht
 */
public interface StateListener extends EventListener {
    /**
     * Indicate that the state has changed.
     *
     * @param undoName The name of the undo action.
     * @param canUndo  A boolean tag indicating if there is an edit to undo.
     * @param redoName The name of the redo action.
     * @param canRedo  A boolean tag indicating if there is an edit to redo.
     */
    void stateChanged(String undoName, boolean canUndo, String redoName, boolean canRedo);
}