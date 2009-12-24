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

import java.util.EventListener;

/**
 * This listener enable an objet to be up to date with the current function of the
 * application.
 *
 * @author Baptiste Wicht
 */
public interface FunctionListener extends EventListener {
    /**
     * Call when the function has been updated.
     *
     * @param event The function event.
     */
    void functionUpdated(FunctionEvent event);
}