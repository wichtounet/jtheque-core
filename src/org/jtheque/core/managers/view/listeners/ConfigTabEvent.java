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

import org.jtheque.core.managers.view.impl.components.config.ConfigTabComponent;

import java.util.EventObject;

/**
 * An event on config tab.
 *
 * @author Baptiste Wicht
 */
public final class ConfigTabEvent extends EventObject {
    private final ConfigTabComponent component;

    /**
     * Construct a new ConfigTabEvent.
     *
     * @param source    The source of the event.
     * @param component The ConfigTabComponent linked to the event.
     */
    public ConfigTabEvent(Object source, ConfigTabComponent component) {
        super(source);

        this.component = component;
    }

    /**
     * Return the component linked to the event.
     *
     * @return The component.
     */
    public ConfigTabComponent getComponent() {
        return component;
    }
}