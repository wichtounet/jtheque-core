package org.jtheque.ui.able;

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
public interface ViewDelegate {
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
     * Refresh the object.
     *
     * @param c The object to refresh.
     */
    void refresh(Object c);//TODO See if interesting to inline this method

    /**
     * Ask the user for text.
     *
     * @param title The question to ask to the user.
     *
     * @return The text of the user.
     */
    String askText(String title);
}
