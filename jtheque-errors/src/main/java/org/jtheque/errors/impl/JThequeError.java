package org.jtheque.errors.impl;

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
    final Throwable exception;
    final String details;
    final Level level;

    /**
     * Create a new error.
     *
     * @param title The title of the error.
     * @param level The level of error.
     */
    public JThequeError(String title, Level level, String details, Throwable exception) {
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
    public String getTitle(ILanguageService languageService) {
        return title;
    }

    @Override
    public String getDetails(ILanguageService languageService) {
        if (exception != null) {
            return details == null ? "" : details +
                    '\n' + exception.getMessage() +
                    '\n' + getCustomStackTrace(exception);
        }

        return details;
    }

    /**
     * Return the stacktrace into a String.
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