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

import java.util.EventListener;

/**
 * A listener who listen to the config tab add and remove.
 *
 * @author Baptiste Wicht
 */
public interface ConfigTabListener extends EventListener {
    /**
     * A tab has been added.
     *
     * @param event The event.
     */
    void tabAdded(ConfigTabEvent event);

    /**
     * A tab has been removed.
     *
     * @param event The event.
     */
    void tabRemoved(ConfigTabEvent event);
}