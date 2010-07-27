package org.jtheque.errors.impl;

import org.jtheque.i18n.able.ILanguageService;
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
 * An internationalized error implementation. This class is immutable. 
 *
 * @author Baptiste Wicht
 * @see org.jtheque.errors.able.Errors
 */
@Immutable
public final class InternationalizedError extends JThequeError {
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
    public InternationalizedError(String message, Object[] replaces, String details, Object[] replacesDetails) {
        super(message, Level.ERROR, details, null);

        titleReplaces = ArrayUtils.copyOf(replaces);
        detailsReplaces = ArrayUtils.copyOf(replacesDetails);
    }

    @Override
    public String getTitle(ILanguageService languageService) {
        return languageService.getMessage(getTitle(), titleReplaces);
    }

    @Override
    public String getDetails(ILanguageService languageService) {
        if (getException() != null) {
            return languageService.getMessage(getDetails(), detailsReplaces) +
                    '\n' + getException().getMessage() +
                    '\n' + getCustomStackTrace(getException());
        }

        return languageService.getMessage(getDetails(), detailsReplaces);
    }
}