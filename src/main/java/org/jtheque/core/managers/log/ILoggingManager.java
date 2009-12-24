package org.jtheque.core.managers.log;

import org.jtheque.core.managers.IManager;

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

/**
 * A logging manager. This manager is responsible of the different loggers.
 *
 * @author Baptiste Wicht
 */
public interface ILoggingManager extends IManager {
    /**
     * Return the logger for a class.
     *
     * @param classz The class for which we want the logger.
     * @return The appropriate logger.
     */
    IJThequeLogger getLogger(Class<?> classz);
}