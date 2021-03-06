package org.jtheque.errors;

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

import org.jtheque.i18n.LanguageService;
import org.jtheque.utils.annotations.Immutable;

/**
 * An error.
 *
 * @author Baptiste Wicht
 * @see Errors
 */
@Immutable
public interface Error {
    /**
     * The level of the error.
     *
     * @author Baptiste Wicht
     */
    enum Level {
        ERROR,
        WARNING
    }

    /**
     * Return the level of the errors.
     *
     * @return The level of the errors.
     */
    Level getLevel();

    /**
     * Return the title of the error.
     *
     * @param languageService The language service to internationalize the title if necessary.
     *
     * @return The title, internationalized or not.
     */
    String getTitle(LanguageService languageService);

    /**
     * Return the details of the error.
     *
     * @param languageService The language service to internationalize the details if necessary.
     *
     * @return The details, internationalized or not.
     */
    String getDetails(LanguageService languageService);
}
