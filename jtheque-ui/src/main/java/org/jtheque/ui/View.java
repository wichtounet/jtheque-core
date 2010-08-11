package org.jtheque.ui;


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
 * Represents a view.
 *
 * @author Baptiste Wicht
 */
public interface View extends ViewComponent {
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
    Model getModel();

    /**
     * Validate the view.
     *
     * @return true if the data are valid else false.
     */
    boolean validateContent();
}