package org.jtheque.ui.able;

import org.jtheque.ui.utils.edt.SimpleTask;
import org.jtheque.ui.utils.edt.Task;

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

public interface IUIUtils {
    /**
     * Return the delegate view manager.
     *
     * @return The delegate view manager.
     */
    ViewDelegate getDelegate();

   /**
     * Ask the user for confirmation with internationalized message.
     *
     * @param textKey  The question key.
     * @param titleKey The title key.
     * @return true if the user has accepted else false.
     */
    boolean askI18nUserForConfirmation(String textKey, String titleKey);

    /**
     * Display a internationalized.
     *
     * @param key The internationalization key.
     */
    void displayI18nText(String key);

    /**
     * Execute a task in the EDT.
     *
     * @param task The task to execute.
     */
    void execute(SimpleTask task);

    /**
     * Execute a task in the EDT.
     *
     * @param <T>  The type of return.
     * @param task The task to execute.
     * @return The result of the task.
     */
    <T> T execute(Task<T> task);
}