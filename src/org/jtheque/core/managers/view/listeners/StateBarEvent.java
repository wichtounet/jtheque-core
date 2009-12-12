package org.jtheque.core.managers.view.listeners;

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

import org.jtheque.core.managers.view.able.components.StateBarComponent;

import java.util.EventObject;

/**
 * An event on the state bar.
 *
 * @author Baptiste Wicht
 */
public final class StateBarEvent extends EventObject {
    private static final long serialVersionUID = -6212722763085056041L;

    private final StateBarComponent component;

    /**
     * Construct a new StateBarEvent.
     *
     * @param source    The source of the event.
     * @param component The StateBarComponent linked to the event.
     */
    public StateBarEvent(Object source, StateBarComponent component) {
        super(source);

        this.component = component;
    }

    /**
     * Return the component linked to the event.
     *
     * @return The component.
     */
    public StateBarComponent getComponent() {
        return component;
    }
}