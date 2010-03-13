package org.jtheque.core.lifecycle;

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
 * This listener enable an object to be up to date with the title of the application.
 *
 * @author Baptiste Wicht
 */
public interface TitleListener extends EventListener {
    /**
     * Call when the title has been updated.
     *
     * @param event The new current object.
     */
    void titleUpdated(TitleEvent event);
}