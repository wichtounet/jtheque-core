package org.jtheque.errors.utils;

import org.jtheque.errors.able.IError;
import org.jtheque.i18n.able.ILanguageService;

import org.jdesktop.swingx.error.ErrorInfo;

import java.util.logging.Level;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * A basic error implementation.
 *
 * @author Baptiste Wicht
 */
public class JThequeError implements IError {
    private final String message;
    private Throwable exception;
    private String details;

    /**
     * Construct a new Error with a simple message.
     *
     * @param message The message of the error.
     */
    public JThequeError(String message) {
        super();

        this.message = message;
    }

    /**
     * Construct a new Error with a message and some details.
     *
     * @param message The message of the error.
     * @param details Some details about the error.
     */
    public JThequeError(String message, String details) {
        this(message);

        this.details = details;
    }

    /**
     * Construct a new Error from an existing exception. The message of the error will be the message of the exception.
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

    @Override
    public ErrorInfo toErrorInfo(ILanguageService languageService) {
        return new ErrorInfo(message, message, details, "", exception, Level.SEVERE, null);
    }
}