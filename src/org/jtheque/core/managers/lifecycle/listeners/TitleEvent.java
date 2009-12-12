package org.jtheque.core.managers.lifecycle.listeners;

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

import java.util.EventObject;

/**
 * Event fired when the title has changed.
 *
 * @author Baptiste Wicht
 */
public final class TitleEvent extends EventObject {
    private static final long serialVersionUID = 4356006373361686392L;

    private final String title;

    /**
     * Construct a new Title Event with the new title.
     *
     * @param source The source of the change
     * @param title  The new title
     */
    public TitleEvent(Object source, String title) {
        super(source);

        this.title = title;
    }

    /**
     * Return the new title.
     *
     * @return The new title
     */
    public String getTitle() {
        return title;
    }
}