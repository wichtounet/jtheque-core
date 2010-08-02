package org.jtheque.errors.able;

import org.jtheque.errors.able.Error.Level;
import org.jtheque.i18n.able.LanguageService;
import org.jtheque.utils.annotations.Immutable;
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
 * A builder for Error objects.
 *
 * @author Baptiste Wicht
 * @see Error
 */
public final class Errors {
    public static final Object[] EMPTY_REPLACES = ArrayUtils.EMPTY_ARRAY;

    /**
     * Utility class, not instantiable. 
     */
    private Errors() {
        throw new AssertionError();
    }

    /**
     * Construct a new Error with a simple message.
     *
     * @param title The message of the error.
     *
     * @return The created error. 
     */
    public static Error newError(String title) {
        return new JThequeError(title, Level.ERROR, null, null);
    }

    /**
     * Construct a new Error with a message and a specific level of error.
     *
     * @param title The title of the error.
     * @param level The level of error.
     *
     * @return The created error.
     */
    public static Error newError(String title, Level level) {
        return new JThequeError(title, level, null, null);
    }

    /**
     * Construct a new Error with a message and some details.
     *
     * @param title   The message of the error.
     * @param details Some details about the error.
     *
     * @return The created error.
     */
    public static Error newError(String title, String details) {
        return new JThequeError(title, Level.ERROR, details, null);
    }

    /**
     * Construct a new Error from an existing exception. The message of the error will be the message of the exception.
     *
     * @param exception The existing exception.
     *
     * @return The created error.
     */
    public static Error newError(Throwable exception) {
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
    public static Error newError(String title, Throwable exception) {
        return new JThequeError(title, Level.ERROR, null, exception);
    }

    /**
     * Construct a new i18n Error.
     *
     * @param message The message key.
     *
     * @return The created error.
     */
    public static Error newI18nError(String message) {
        return new InternationalizedError(message, EMPTY_REPLACES, null, EMPTY_REPLACES);
    }

    /**
     * Construct a new i18n Error.
     *
     * @param message  The message key.
     * @param replaces The replaces for the internationalization variable arguments of the message.
     *
     * @return The created error.
     */
    public static Error newI18nError(String message, Object[] replaces) {
        return new InternationalizedError(message, replaces, null, EMPTY_REPLACES);
    }

    /**
     * Construct a new i18n Error.
     *
     * @param message  The message key.
     * @param replaces The replaces for the internationalization variable arguments of the message.
     * @param details  The details key.
     *
     * @return The created error.
     */
    public static Error newI18nError(String message, Object[] replaces, String details) {
        return new InternationalizedError(message, replaces, details, EMPTY_REPLACES);
    }

    /**
     * Construct a new i18n Error.
     *
     * @param message         The message key.
     * @param replaces        The replaces for the internationalization variable arguments of the message.
     * @param details         The details key.
     * @param replacesDetails The replaces for the internationalization variable arguments of the details.
     *
     * @return The created error.
     */
    public static Error newI18nError(String message, Object[] replaces, String details, Object[] replacesDetails) {
        return new InternationalizedError(message, replaces, details, replacesDetails);
    }

    /**
     * A basic error implementation. This class is immutable.
     *
     * @author Baptiste Wicht
     * @see Errors
     */
    @Immutable
    private static class JThequeError implements Error {
        private final String title;
        private final Throwable exception;
        private final String details;
        private final Level level;

        /**
         * Create a new error.
         *
         * @param title     The title of the error.
         * @param level     The level of error.
         * @param details   The details of the error.
         * @param exception The exception that caused this error.
         */
        private JThequeError(String title, Level level, String details, Throwable exception) {
            super();

            this.title = title;
            this.level = level;
            this.details = details;
            this.exception = exception;
        }

        @Override
        public Level getLevel() {
            return level;
        }

        @Override
        public String getTitle(LanguageService languageService) {
            return title;
        }

        @Override
        public String getDetails(LanguageService languageService) {
            if (exception != null) {
                return details == null ? "" : details +
                        '\n' + exception.getMessage() +
                        '\n' + getCustomStackTrace(exception);
            }

            return details;
        }

        /**
         * Return the base title of the error.
         *
         * @return The base title of the error.
         */
        String getTitle() {
            return title;
        }

        /**
         * Return the base details of the error.
         *
         * @return The base details of the error.
         */
        String getDetails() {
            return details;
        }

        /**
         * Return the base details of the error.
         *
         * @return The base details of the error.
         */
        Throwable getException() {
            return exception;
        }

        /**
         * Return the stack trace into a String.
         *
         * @param throwable The throwable to extract the stack trace from.
         *
         * @return A String representing the stack trace.
         */
        static String getCustomStackTrace(Throwable throwable) {
            final StringBuilder result = new StringBuilder(500);

            result.append(throwable.toString());
            result.append('\n');

            for (StackTraceElement element : throwable.getStackTrace()) {
                result.append(element);
                result.append('\n');
            }

            return result.toString();
        }
    }

    /**
     * An internationalized error implementation. This class is immutable.
     *
     * @author Baptiste Wicht
     * @see Errors
     */
    @Immutable
    private static final class InternationalizedError extends JThequeError {
        private final Object[] titleReplaces;
        private final Object[] detailsReplaces;

        /**
         * Construct a new InternationalizedError.
         *
         * @param message         The message key.
         * @param replaces        The replaces for the internationalization variable arguments of the message.
         * @param details         The details key.
         * @param replacesDetails The replaces for the internationalization variable arguments of the details.
         */
        private InternationalizedError(String message, Object[] replaces, String details, Object[] replacesDetails) {
            super(message, Level.ERROR, details, null);

            titleReplaces = ArrayUtils.copyOf(replaces);
            detailsReplaces = ArrayUtils.copyOf(replacesDetails);
        }

        @Override
        public String getTitle(LanguageService languageService) {
            return languageService.getMessage(getTitle(), titleReplaces);
        }

        @Override
        public String getDetails(LanguageService languageService) {
            if (getException() != null) {
                return languageService.getMessage(getDetails(), detailsReplaces) +
                        '\n' + getException().getMessage() +
                        '\n' + getCustomStackTrace(getException());
            }

            return languageService.getMessage(getDetails(), detailsReplaces);
        }
    }
}