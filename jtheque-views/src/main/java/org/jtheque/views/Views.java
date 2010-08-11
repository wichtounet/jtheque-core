package org.jtheque.views;

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

import org.jtheque.views.components.ConfigTabComponent;
import org.jtheque.views.components.StateBarComponent;
import org.jtheque.views.components.MainComponent;
import org.jtheque.views.windows.MainView;

import java.util.Collection;

/**
 * A views manager.
 *
 * @author Baptiste Wicht
 */
public interface Views {
    /**
     * Return the main view.
     *
     * @return the main view.
     */
    MainView getMainView();

    /**
     * Set the selected view.
     *
     * @param component The view to select.
     */
    void setSelectedView(MainComponent component);

    /**
     * Return the selected view.
     *
     * @return The selected view.
     */
    MainComponent getSelectedView();

    /**
     * Return all the main components.
     *
     * @return A List containing all the main components.
     */
    Collection<MainComponent> getMainComponents();

    /**
     * Add a main component.
     *
     * @param moduleId  The id of the module who adds this state bar.
     * @param component The main component to add.
     */
    void addMainComponent(String moduleId, MainComponent component);

    /**
     * Set the selected main component.
     *
     * @param component The main component to select.
     */
    void setSelectedMainComponent(MainComponent component);

    /**
     * Add a state bar component.
     *
     * @param moduleId  The id of the module who adds this state bar.
     * @param component The component to add to the state bar.
     */
    void addStateBarComponent(String moduleId, StateBarComponent component);

    /**
     * Return all the state bar components.
     *
     * @return A List containing all the state bar components.
     */
    Collection<StateBarComponent> getStateBarComponents();

    /**
     * Add config tab component.
     *
     * @param moduleId  The id of the module who adds this state bar.
     * @param component The config tab component to add.
     */
    void addConfigTabComponent(String moduleId, ConfigTabComponent component);

    /**
     * Return all the config tab components.
     *
     * @return A List containing all the config tab components.
     */
    Collection<ConfigTabComponent> getConfigTabComponents();

    /**
     * Init the views.
     */
    void init();

    /**
     * Display the conditional views. This method display the update view if there is some updates and the
     * messages view if there is unread messages. 
     */
    void displayConditionalViews();
}