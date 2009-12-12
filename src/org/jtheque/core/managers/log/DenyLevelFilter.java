package org.jtheque.core.managers.log;

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

import org.apache.log4j.Level;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * A logging filter to deny a specific level.
 *
 * @author Baptiste Wicht
 */
final class DenyLevelFilter extends Filter {
    private final Level level;

    /**
     * Construct a new DenyLevelFilter for a specific level.
     *
     * @param level The level to deny.
     */
    DenyLevelFilter(Level level) {
        super();

        this.level = level;
    }

    @Override
    public int decide(LoggingEvent event) {
        if (event.getLevel().equals(level)) {
            return -1;
        }

        return 0;
    }
}