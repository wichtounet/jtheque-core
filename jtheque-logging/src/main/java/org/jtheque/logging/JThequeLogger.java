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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A JTheque Logger. This logger is based on a SLF4J logger.
 *
 * @author Baptiste Wicht
 */
public final class JThequeLogger implements IJThequeLogger {
    private final Logger logger;

    /**
     * Construct a new <code>MyLogger</code>.
     *
     * @param callerClass The class who want to obtain a logger.
     */
    public JThequeLogger(Class<?> callerClass) {
        super();

        logger = LoggerFactory.getLogger(callerClass);
    }

    @Override
    public void message(String message, Object... replaces) {
        logger.info(message, replaces);
    }

    @Override
    public void debug(String message, Object... replaces) {
        logger.debug(message, replaces);
    }

    @Override
    public void warn(String message, Object... replaces) {
        logger.warn(message, replaces);
    }

    @Override
    public void trace(String message, Object... replaces) {
        logger.trace(message, replaces);
    }

    @Override
    public void error(String message, Object... replaces) {
        logger.error(message, replaces);
    }

    @Override
    public void error(Throwable e) {
        logger.error(e.getMessage(), e);
    }

    @Override
    public void error(Exception e, String message) {
        logger.error(message, e);
    }
}