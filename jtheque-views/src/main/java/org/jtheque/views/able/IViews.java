package org.jtheque.views.able;

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

import org.jtheque.views.able.components.ConfigTabComponent;
import org.jtheque.views.able.components.IStateBarComponent;
import org.jtheque.views.able.components.MainComponent;
import org.jtheque.views.able.panel.IModuleView;
import org.jtheque.views.able.panel.IRepositoryView;
import org.jtheque.views.able.windows.IConfigView;
import org.jtheque.views.able.windows.IErrorView;
import org.jtheque.views.able.windows.IEventView;
import org.jtheque.views.able.windows.ILicenceView;
import org.jtheque.views.able.windows.IMainView;
import org.jtheque.views.able.windows.IMessageView;
import org.jtheque.views.able.windows.IUpdateView;

import java.util.Collection;

/**
 * A views manager.
 *
 * @author Baptiste Wicht
 */
public interface IViews {
    /**
     * Return the main view.
     *
     * @return the main view.
     */
    IMainView getMainView();

    /**
     * Return the licence view.
     *
     * @return The licence view.
     */
    ILicenceView getLicenceView();

    /**
     * Return the config view.
     *
     * @return The config view.
     */
    IConfigView getConfigView();

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
     * Return the module view.
     *
     * @return the module view.
     */
    IModuleView getModuleView();

    /**
     * Return the messages view.
     *
     * @return the messages view.
     */
    IMessageView getMessagesView();

    /**
     * Return the event view.
     *
     * @return The event view.
     */
    IEventView getEventView();

    /**
     * Return the repository view.
     *
     * @return The repository view.
     */
    IRepositoryView getRepositoryView();

    /**
     * Return the update view.
     *
     * @return The update view.
     */
    IUpdateView getUpdateView();

    /**
     * Return the error view.
     *
     * @return The error view.
     */
    IErrorView getErrorView();

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
    void addStateBarComponent(String moduleId, IStateBarComponent component);

    /**
     * Return all the state bar components.
     *
     * @return A List containing all the state bar components.
     */
    Collection<IStateBarComponent> getStateBarComponents();

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
}