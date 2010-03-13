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

/**
 * A JTheque Logger. The logger is based on SLF4J and the real implementation is LogBack functionality.
 *
 * @author Baptiste Wicht
 */
public interface IJThequeLogger {
    /**
     * Log a message.
     *
     * @param message  The message to log.
     * @param replaces The objects to replace the {} occurrences.
     */
    void message(String message, Object... replaces);

    /**
     * Log a message of level debug.
     *
     * @param message  The message to log
     * @param replaces The objects to replace the {} occurrences.
     */
    void debug(String message, Object... replaces);

    /**
     * Log a message of level trace.
     *
     * @param message  The message to log
     * @param replaces The objects to replace the {} occurrences.
     */
    void trace(String message, Object... replaces);

    /**
     * Log a message of level warning.
     *
     * @param message  The message to log
     * @param replaces The objects to replace the {} occurrences.
     */
    void warn(String message, Object... replaces);

    /**
     * Log an exception.
     *
     * @param e The exception to log
     */
    void error(Throwable e);

    /**
     * Log a message of level error.
     *
     * @param message  The message to log
     * @param replaces The objects to replace the {} occurrences.
     */
    void error(String message, Object... replaces);

    /**
     * Log a message and an exception with error level.
     *
     * @param message The message to log.
     * @param e       The exception to log.
     */
    void error(Exception e, String message);
}