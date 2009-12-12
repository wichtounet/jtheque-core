package org.jtheque.core.managers.view.able;

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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.WindowConfiguration;
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
    boolean askYesOrNo(String text, String title);

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
     * Apply the glass pane.
     *
     * @param glassPane The glass pane.
     */
    void applyGlassPane(Object glassPane);

    /**
     * Set size of the view considering the configuration of the view.
     *
     * @param view          The view to configure. Ì€
     * @param defaultWidth  The default width of the view.
     * @param defaultHeight The default height of the view.
     */
    void setSize(IView view, int defaultWidth, int defaultHeight);

    /**
     * Fill the configuration with the view informations.
     *
     * @param configuration The configuration to fill.
     * @param view          The view to fill the configuration with.
     */
    void fill(WindowConfiguration configuration, IView view);

    /**
     * Configure the view with the window configuration.
     *
     * @param configuration The window configuration.
     * @param view          The view to configure.
     */
    void configure(WindowConfiguration configuration, IView view);

    /**
     * Ask the user for text.
     *
     * @param title The question to ask to the user.
     * @return The text of the user.
     */
    String askText(String title);
}
