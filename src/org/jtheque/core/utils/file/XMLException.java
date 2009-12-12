package org.jtheque.core.utils.file;

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
 * An XML exception.
 *
 * @author Baptiste Wicht
 */
public final class XMLException extends Exception {
    private static final long serialVersionUID = -2917421221194430303L;

    /**
     * Construct a new XMLException.
     *
     * @param message The message of the exception.
     */
    public XMLException(String message) {
        super(message);
    }

    /**
     * Construct a new XMLException
     *
     * @param message The message of the exception.
     * @param cause   The cause of the exception.
     */
    public XMLException(String message, Throwable cause) {
        super(message, cause);
    }
}