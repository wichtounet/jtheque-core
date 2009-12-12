package org.jtheque.core.managers.error;

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
 * An error.
 *
 * @author Baptiste Wicht
 */
public class JThequeError {
    private final String message;
    private Throwable exception;
    private String details;

    /**
     * Construct a new Error with a simple message.
     *
     * @param message The message of the error.
     */
    JThequeError(String message) {
        super();

        this.message = message;
    }

    /**
     * Construct a new Error with a message and some details.
     *
     * @param message The message of the error.
     * @param details Some details about the error.
     */
    JThequeError(String message, String details) {
        this(message);

        this.details = details;
    }

    /**
     * Construct a new Error from an existing exception. The message of the error will be the message
     * of the exception.
     *
     * @param exception The existing exception.
     */
    public JThequeError(Throwable exception) {
        this(exception.getMessage());

        this.exception = exception;
    }

    /**
     * Construct a new Error from an existing exception with a specific message.
     *
     * @param exception The exception to encapsulate in the error.
     * @param message   The message.
     */
    public JThequeError(Throwable exception, String message) {
        this(message);

        this.exception = exception;
    }

    /**
     * Return the message of the error.
     *
     * @return The message of the error.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Return the exception of the error.
     *
     * @return The exception who cause this error.
     */
    public final Throwable getException() {
        return exception;
    }

    /**
     * Return the details of the error.
     *
     * @return The details of the error.
     */
    public String getDetails() {
        return details;
    }
}