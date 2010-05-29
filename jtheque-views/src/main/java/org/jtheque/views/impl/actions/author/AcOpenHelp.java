package org.jtheque.views.impl.actions.author;

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

import org.jtheque.core.able.ICore;
import org.jtheque.ui.utils.actions.AbstractBrowseAction;

import javax.swing.KeyStroke;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

/**
 * Action to open the help.
 *
 * @author Baptiste Wicht
 */
public final class AcOpenHelp extends AbstractBrowseAction {
    /**
     * Construct a new AcOpenHelp with a specific key.
     */
    public AcOpenHelp() {
        super("menu.help");

        putValue(ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_F1, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public String getUrl() {
        return ICore.HELP_URL;
    }
}