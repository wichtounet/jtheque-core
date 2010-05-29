package org.jtheque.i18n.able;

import org.jtheque.utils.bean.Version;

import java.util.Locale;

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

    /**
     * Register a resource.
     *
     * @param name      The name of the resource.
     * @param version   The version of the resource.
     * @param resources The resources to the i18n files (.properties) of the basename.
     */
    void registerResource(String name, Version version, I18NResource... resources);

    /**
     * Refresh all the internationalizables elements.
     */
    void refreshAll();

    /**
     * Release the specified resource.
     *
     * @param name The name of the resource.
     */
    void releaseResource(String name);
}