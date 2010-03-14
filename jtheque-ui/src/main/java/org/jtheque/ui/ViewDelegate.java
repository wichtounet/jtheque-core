package org.jtheque.ui;

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

import org.jtheque.errors.JThequeError;
import org.jtheque.utils.io.SimpleFilter;

/**
 * @author Baptiste Wicht
 */
public interface ViewDelegate {
    /**
     * Ask the user for a yes or no answer.
     *
     * @param text  The question.
     * @param title The question title.
     * @return true if the user has answered yes else false.
     */
    boolean askUserForConfirmation(String text, String title);

    /**
     * Display an error.
     *
     * @param error The error to display.
     */
    void displayError(JThequeError error);

    /**
     * Display the text.
     *
     * @param text The text to display.
     */
    void displayText(String text);

    /**
     * Choose a file.
     *
     * @param filter The filter.
     * @return The chosen file.
     */
    String chooseFile(SimpleFilter filter);

    /**
     * Choose a directory.
     *
     * @return The chosen directory.
     */
    String chooseDirectory();

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
    void refresh(Object c);

    /**
     * Ask the user for text.
     *
     * @param title The question to ask to the user.
     * @return The text of the user.
     */
    String askText(String title);
}
