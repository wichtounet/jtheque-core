package org.jtheque.core.managers.view.able;

import org.jtheque.core.managers.view.able.components.ViewComponent;
import org.jtheque.core.managers.view.able.components.IModel;


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
 * Represents a view.
 *
 * @author Baptiste Wicht
 */
public interface IView extends ViewComponent {
    /**
     * Display the view.
     */
    void display();

    /**
     * Close the view.
     */
    void closeDown();

    /**
     * Display the view in first plan.
     */
    void toFirstPlan();

    /**
     * Enable or disable the view.
     *
     * @param enabled If true, the interface will be enabled else the interface will be disabled
     */
    void setEnabled(boolean enabled);

    /**
     * Indicate if the view is enabled or not.
     *
     * @return <code>true</code> if the view is enabled else <code>false</code>
     */
    boolean isEnabled();

    /**
     * Send a message to the view.
     *
     * @param message The message key.
     * @param value   The value of the message.
     */
    void sendMessage(String message, Object value);

    /**
     * Refresh the view.
     */
    void refresh();

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    IModel getModel();

    /**
     * Validate the view.
     *
     * @return true if the data are valid else false.
     */
    boolean validateContent();
}