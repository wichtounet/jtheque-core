package org.jtheque.errors;

import org.jtheque.i18n.ILanguageService;
import org.jtheque.utils.StringUtils;

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
 * An error with its message and details internationalized.
 *
 * @author Baptiste Wicht
 */
public final class InternationalizedError extends JThequeError {
    private Object[] messageReplaces;
    private Object[] detailsReplaces;

    private final ILanguageService languageService;

    /**
     * Construct a new InternationalizedError.
     *
     * @param languageService The language manager to use to internationalize the error.
     * @param message The message key.
     */
    public InternationalizedError(ILanguageService languageService, String message) {
        super(message);

        this.languageService = languageService;
    }

    /**
     * Construct a new InternationalizedError.
     *
     * @param languageService The language manager to use to internationalize the error.
     * @param message  The message key.
     * @param replaces The replaces for the internationalization variable arguments of the message.
     */
    public InternationalizedError(ILanguageService languageService, String message, Object... replaces) {
        super(message);

        messageReplaces = replaces.clone();
        this.languageService = languageService;
    }

    /**
     * Construct a new InternationalizedError.
     *
     * @param languageService The language manager to use to internationalize the error.
     * @param message The message key.
     * @param details The details key.
     */
    public InternationalizedError(ILanguageService languageService, String message, String details) {
        super(message, details);

        this.languageService = languageService;
    }

    /**
     * Construct a new InternationalizedError.
     *
     * @param languageService The language manager to use to internationalize the error.
     * @param message  The message key.
     * @param replaces The replaces for the internationalization variable arguments of the message.
     * @param details  The details key.
     */
    public InternationalizedError(ILanguageService languageService, String message, Object[] replaces, String details) {
        super(message, details);

        messageReplaces = replaces.clone();
        this.languageService = languageService;
    }

    /**
     * Construct a new InternationalizedError.
     *
     * @param languageService The language manager to use to internationalize the error.
     * @param message         The message key.
     * @param replaces        The replaces for the internationalization variable arguments of the message.
     * @param details         The details key.
     * @param replacesDetails The replaces for the internationalization variable arguments of the details.
     */
    public InternationalizedError(ILanguageService languageService, String message, Object[] replaces, String details, Object[] replacesDetails) {
        super(message, details);

        messageReplaces = replaces.clone();
        detailsReplaces = replacesDetails.clone();
        this.languageService = languageService;
    }

    @Override
    public String getDetails() {
        String details = null;

        if (!StringUtils.isEmpty(super.getDetails())) {
            details = languageService.getMessage(super.getDetails(), detailsReplaces);
        }

        return details;
    }

    @Override
    public String getMessage() {
        return languageService.getMessage(super.getMessage(), messageReplaces);
    }
}