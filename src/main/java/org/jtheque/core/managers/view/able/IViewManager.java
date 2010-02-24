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

import org.jtheque.core.managers.view.Views;
import org.jtheque.core.managers.view.able.components.StateBarComponent;
import org.jtheque.core.managers.view.able.components.MainComponent;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.edt.Task;
import org.jtheque.core.managers.view.impl.components.config.ConfigTabComponent;

import java.util.Collection;

/**
 * A view manager.
 *
 * @author Baptiste Wicht
 */
public interface IViewManager {
    /**
     * Return the manager responsible of the splash screen.
     *
     * @return The manager responsible of the splash screen.
     */
    ISplashManager getSplashManager();

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
     * Return the delegate view manager.
     *
     * @return The delegate view manager.
     */
    ViewDelegate getDelegate();

    void setSelectedMainComponent(MainComponent component);
}