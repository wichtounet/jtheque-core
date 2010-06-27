package org.jtheque.errors.utils;

import org.jtheque.errors.able.IError;
import org.jtheque.i18n.able.ILanguageService;

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
    final String title;
    Throwable exception;
    String details;
    Level level;

    /**
     * Construct a new Error with a simple message.
     *
     * @param title The message of the error.
     */
    public JThequeError(String title) {
        super();

        this.title = title;

        level = Level.ERROR;
    }

    public JThequeError(String title, Level level) {
        super();

        this.title = title;
        this.level = level;
    }

    /**
     * Construct a new Error with a message and some details.
     *
     * @param title The message of the error.
     * @param details Some details about the error.
     */
    public JThequeError(String title, String details) {
        this(title);

        this.details = details;

        level = Level.ERROR;
    }

    /**
     * Construct a new Error from an existing exception. The message of the error will be the message of the exception.
     *
     * @param exception The existing exception.
     */
    public JThequeError(Throwable exception) {
        this(exception.getMessage());

        this.exception = exception;

        level = Level.ERROR;
    }

    /**
     * Construct a new Error from an existing exception with a specific message.
     *
     * @param exception The exception to encapsulate in the error.
     * @param title   The message.
     */
    public JThequeError(Throwable exception, String title) {
        this(title);

        this.exception = exception;

        level = Level.ERROR;
    }

    /**
     * Return the exception of the error.
     *
     * @return The exception who cause this error.
     */
    final Throwable getException() {
        return exception;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public String getTitle(ILanguageService languageService) {
        return title;
    }

    @Override
    public String getDetails(ILanguageService languageService) {
        if(exception != null){
            return details == null ? "" : details +
                    '\n' + exception.getMessage() +
                    '\n' + getCustomStackTrace(exception);
        }

        return details;
    }

    static String getCustomStackTrace(Throwable aThrowable) {
        final StringBuilder result = new StringBuilder(500);

        result.append(aThrowable.toString());
        result.append('\n');

        for (StackTraceElement element : aThrowable.getStackTrace()) {
            result.append(element);
            result.append('\n');
        }

        return result.toString();
    }
}