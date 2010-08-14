package org.jtheque.ui;

import org.jtheque.utils.annotations.ThreadSafe;

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
 * UI utils specification.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public interface UIUtils {
    String LIGHT_IMAGE = "jtheque-ui-light";

    /**
     * Ask the user for confirmation with internationalized message.
     *
     * @param textKey  The question key.
     * @param titleKey The title key.
     *
     * @return true if the user has accepted else false.
     */
    boolean askI18nUserForConfirmation(String textKey, String titleKey);

    /**
     * Ask the user for confirmation with internationalized message using replaces.
     *
     * @param textKey       The text key.
     * @param textReplaces  The text replaces.
     * @param titleKey      The title key.
     * @param titleReplaces The title replaces.
     *
     * @return true if the user has accepted else false.
     */
    boolean askI18nUserForConfirmation(String textKey, Object[] textReplaces, String titleKey, Object[] titleReplaces);

    /**
     * Display a internationalized.
     *
     * @param key The internationalization key.
     */
    void displayI18nText(String key);

    /**
     * Ask the user for text using the internationalized key as the prompt.
     *
     * @param key The i18n key.
     *
     * @return The text the user has entered. 
     */
    String askI18nText(String key);

    /**
     * Ask the user for a yes or no answer.
     *
     * @param text  The question.
     * @param title The question title.
     *
     * @return true if the user has answered yes else false.
     */
    boolean askUserForConfirmation(String text, String title);

    /**
     * Display the text.
     *
     * @param text The text to display.
     */
    void displayText(String text);

    /**
     * Run the runnable in the view.
     *
     * @param runnable The runnable to run in the view.
     */
    void run(Runnable runnable);

    /**
     * Ask the user for text.
     *
     * @param title The question to ask to the user.
     *
     * @return The text of the user.
     */
    String askText(String title);
}