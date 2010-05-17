package org.jtheque.ui.utils.actions;

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

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

/**
 * A simple JTheque action.
 *
 * @author Baptiste Wicht
 */
public abstract class JThequeSimpleAction extends AbstractAction {
    /**
     * Set the text of the action.
     *
     * @param text The text.
     */
    protected final void setText(String text) {
        putValue(NAME, text);
    }

    /**
     * Set the icon of te action.
     *
     * @param icon The icon.
     */
    public final void setIcon(ImageIcon icon) {
        putValue(SMALL_ICON, icon);
    }

    @Override
    public final Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}