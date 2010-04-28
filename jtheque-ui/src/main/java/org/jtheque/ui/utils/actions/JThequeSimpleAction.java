package org.jtheque.ui.utils.actions;

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

import javax.swing.AbstractAction;
import javax.swing.Action;
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
        putValue(Action.NAME, text);
    }

    /**
     * Set the icon of te action.
     *
     * @param icon The icon.
     */
    public final void setIcon(ImageIcon icon) {
        putValue(Action.SMALL_ICON, icon);
    }

    @Override
    public final Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}