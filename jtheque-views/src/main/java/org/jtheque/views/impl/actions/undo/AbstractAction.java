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

import org.jtheque.ui.utils.actions.JThequeAction;

import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.Toolkit;

/**
 * An abstract undo action.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractAction extends JThequeAction {
    /**
     * Construct a new AbstractAction.
     *
     * @param key         The internationalization key.
     * @param accelerator The accelerator key.
     */
    AbstractAction(String key, int accelerator) {
        super(key);

        putValue(Action.ACCELERATOR_KEY
                , KeyStroke.getKeyStroke(accelerator, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }
}
