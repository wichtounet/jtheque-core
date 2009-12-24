package org.jtheque.core.managers.module;

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

import org.jtheque.core.managers.module.beans.ModuleContainer;

import java.util.EventListener;

/**
 * A Module Listener.
 *
 * @author Baptiste Wicht
 */
public interface ModuleListener extends EventListener {
    /**
     * A module has been added.
     */
    void moduleAdded();

    /**
     * A module has been removed.
     *
     * @param module The removed module.
     */
    void moduleRemoved(ModuleContainer module);
}