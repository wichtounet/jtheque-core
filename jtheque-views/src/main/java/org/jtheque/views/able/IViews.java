package org.jtheque.views.able;

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

import org.jtheque.views.able.components.ConfigTabComponent;
import org.jtheque.views.able.components.MainComponent;
import org.jtheque.views.able.components.StateBarComponent;
import org.jtheque.views.able.panel.IModuleView;
import org.jtheque.views.able.panel.IRepositoryView;
import org.jtheque.views.able.windows.IConfigView;
import org.jtheque.views.able.windows.ILicenceView;
import org.jtheque.views.able.windows.IEventView;
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

    IEventView getEventView();

    IRepositoryView getRepositoryView();

    IUpdateView getUpdateView();

    /**
     * Return all the main components.
     *
     * @return A List containing all the main components.
     */
    Collection<MainComponent> getMainComponents();

    /**
     * Add a main component.
     *
     * @param moduleId The id of the module who adds this state bar.
     * @param component The main component to add.
     */
    void addMainComponent(String moduleId, MainComponent component);

    void setSelectedMainComponent(MainComponent component);

    /**
     * Add a state bar component.
     *
     * @param moduleId The id of the module who adds this state bar.
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
     * @param moduleId The id of the module who adds this state bar.
     * @param component The config tab component to add.
     */
    void addConfigTabComponent(String moduleId, ConfigTabComponent component);

    /**
     * Return all the config tab components.
     *
     * @return A List containing all the config tab components.
     */
    Collection<ConfigTabComponent> getConfigTabComponents();

    void init();
}