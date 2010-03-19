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

import org.jtheque.ui.able.IView;
import org.jtheque.views.able.components.ConfigTabComponent;
import org.jtheque.views.able.components.MainComponent;
import org.jtheque.views.able.components.StateBarComponent;
import org.jtheque.views.able.panel.ICollectionView;
import org.jtheque.views.impl.WindowConfiguration;

import java.util.Collection;

/**
 * A view manager.
 *
 * @author Baptiste Wicht
 */
public interface IViewService {
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

    /**
     * Return the Views. It seems the class responsible for managing the different views.
     *
     * @return The Views object.
     */
    Views getViews();

    /**
     * Display the about view.
     */
    void displayAboutView();

    /**
     * Display the choose collection view.
     */
    void displayCollectionView();

    /**
     * Return the collection view.
     *
     * @return The collection view.
     */
    ICollectionView getCollectionView();

    /**
     * Return the view defaults.
     *
     * @return The view defaults.
     */
    ViewDefaults getViewDefaults();

    /**
     * Save the current state of the window.
     *
     * @param window The window.
     * @param name   The name of the view.
     */
    void saveState(IView window, String name);

    /**
     * Configure a view. It seems sets the size and location of the view.
     *
     * @param window        The window to configure.
     * @param name          The name of the view.
     * @param defaultWidth  The default width of the view.
     * @param defaultHeight The default height of the view.
     */
    void configureView(IView window, String name, int defaultWidth, int defaultHeight);



    /**
     * Set size of the view considering the configuration of the view.
     *
     * @param view          The view to configure. ̀
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

    void setSelectedMainComponent(MainComponent component);

    /**
     * Apply the glass pane.
     *
     * @param glassPane The glass pane.
     */
    void applyGlassPane(Object glassPane);
}