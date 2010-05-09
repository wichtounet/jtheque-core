package org.jtheque.views.impl.components.menu;

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

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.plaf.basic.BasicMenuItemUI;

/**
 * A JTheque menu item.
 *
 * @author Baptiste Wicht
 */
public final class JThequeMenuItem extends JMenuItem {
    /**
     * Construct a new JThequeMenuItem.
     *
     * @param action The action of the menu item.
     */
    public JThequeMenuItem(Action action) {
        super(action);

        setUI(new BasicMenuItemUI());
    }
}