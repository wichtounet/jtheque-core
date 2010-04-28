package org.jtheque.i18n;

import org.jtheque.utils.bean.Version;

import java.util.Locale;

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
 * @author Baptiste Wicht
 */
public interface ILanguageService {
    /**
     * Set the current language.
     *
     * @param language The language.
     */
    void setCurrentLanguage(String language);

    /**
     * Return the current locale.
     *
     * @return The current locale.
     */
    Locale getCurrentLocale();

    /**
     * Return the current language.
     *
     * @return The current language.
     */
    String getCurrentLanguage();

    /**
     * Return the message of the key and effect the replaces.
     *
     * @param key      The message key.
     * @param replaces The replacements.
     * @return The message of the current locale with the replacements.
     */
    String getMessage(String key, Object... replaces);

    /**
     * Return all the lines of a message. The end line character used is \n.
     *
     * @param key The key of the message.
     * @return An array containing all the lines of the internationalized message.
     */
    String[] getLinesMessage(String key);

    /**
     * Add an internationalizable element.
     *
     * @param internationalizable The internationalizable to add.
     */
    void addInternationalizable(Internationalizable internationalizable);

    void registerResource(String name, Version version, I18nResource... resources);

    /**
     * Refresh all the internationalizables elements.
     */
    void refreshAll();
}