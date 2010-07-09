package org.jtheque.errors.utils;

import org.jtheque.errors.able.IError;
import org.jtheque.errors.able.IError.Level;
import org.jtheque.errors.impl.InternationalizedError;
import org.jtheque.errors.impl.JThequeError;
import org.jtheque.utils.collections.ArrayUtils;

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
 * IError builder. 
 *
 * @author Baptiste Wicht
 * @see org.jtheque.errors.able.IError
 */
public class Errors {
    public static final Object[] EMPTY_REPLACES = ArrayUtils.EMPTY_ARRAY;

    /**
     * Utility class, not instantiable. 
     */
    private Errors() {
        throw new AssertionError();
    }

    /**
     * Construct a new IError with a simple message.
     *
     * @param title The message of the error.
     *
     * @return The created error. 
     */
    public static IError newError(String title) {
        return new JThequeError(title, Level.ERROR, null, null);
    }

    /**
     * Construct a new IError with a message and a specific level of error.
     *
     * @param title The title of the error.
     * @param level The level of error.
     *
     * @return The created error.
     */
    public static IError newError(String title, Level level) {
        return new JThequeError(title, level, null, null);
    }

    /**
     * Construct a new IError with a message and some details.
     *
     * @param title   The message of the error.
     * @param details Some details about the error.
     *
     * @return The created error.
     */
    public static IError newError(String title, String details) {
        return new JThequeError(title, Level.ERROR, details, null);
    }

    /**
     * Construct a new Error from an existing exception. The message of the error will be the message of the exception.
     *
     * @param exception The existing exception.
     *
     * @return The created error.
     */
    public static IError newError(Throwable exception) {
        return new JThequeError(exception.getMessage(), Level.ERROR, null, exception);
    }

    /**
     * Construct a new Error from an existing exception with a specific message.
     *
     * @param title     The message.
     * @param exception The exception to encapsulate in the error.
     *
     * @return The created error.
     */
    public static IError newError(String title, Throwable exception) {
        return new JThequeError(title, Level.ERROR, null, exception);
    }

    /**
     * Construct a new i18n IError.
     *
     * @param message The message key.
     *
     * @return The created error.
     */
    public static IError newI18nError(String message) {
        return new InternationalizedError(message, EMPTY_REPLACES, null, EMPTY_REPLACES);
    }

    /**
     * Construct a new i18n IError.
     *
     * @param message  The message key.
     * @param replaces The replaces for the internationalization variable arguments of the message.
     *
     * @return The created error.
     */
    public static IError newI18nError(String message, Object[] replaces) {
        return new InternationalizedError(message, replaces, null, EMPTY_REPLACES);
    }

    /**
     * Construct a new i18n IError.
     *
     * @param message  The message key.
     * @param replaces The replaces for the internationalization variable arguments of the message.
     * @param details  The details key.
     *
     * @return The created error.
     */
    public static IError newI18nError(String message, Object[] replaces, String details) {
        return new InternationalizedError(message, replaces, details, EMPTY_REPLACES);
    }

    /**
     * Construct a new i18n IError.
     *
     * @param message         The message key.
     * @param replaces        The replaces for the internationalization variable arguments of the message.
     * @param details         The details key.
     * @param replacesDetails The replaces for the internationalization variable arguments of the details.
     *
     * @return The created error.
     */
    public static IError newI18nError(String message, Object[] replaces, String details, Object[] replacesDetails) {
        return new InternationalizedError(message, replaces, details, replacesDetails);
    }
}