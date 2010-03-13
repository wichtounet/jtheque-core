package org.jtheque.logging;

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

import java.util.HashMap;
import java.util.Map;

/**
 * A logging manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class LoggingManager implements ILoggingManager {
    private static final Map<Class<?>, IJThequeLogger> LOGGERS = new HashMap<Class<?>, IJThequeLogger>(100);

    @Override
    public IJThequeLogger getLogger(Class<?> classz) {
        if (!LOGGERS.containsKey(classz)) {
            LOGGERS.put(classz, new JThequeLogger(classz));
        }

        return LOGGERS.get(classz);
    }


}