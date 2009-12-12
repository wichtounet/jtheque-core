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
import org.jtheque.core.managers.view.ViewComponent;
import org.jtheque.core.managers.view.Views;
import org.jtheque.core.managers.view.able.components.StateBarComponent;
import org.jtheque.core.managers.view.able.components.TabComponent;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.edt.Task;
import org.jtheque.core.managers.view.impl.WindowsConfiguration;
import org.jtheque.core.managers.view.impl.components.config.ConfigTabComponent;
import org.jtheque.core.managers.view.listeners.ConfigTabListener;
import org.jtheque.core.managers.view.listeners.StateBarListener;
import org.jtheque.core.managers.view.listeners.TabListener;
import org.jtheque.utils.io.SimpleFilter;

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
     * @param component The component to add to the state bar.
     */
    void addStateBarComponent(StateBarComponent component);

    /**
     * Remove the specified state bar component.
     *
     * @param component The component to remove of to the state bar.
     */
    void removeStateBarComponent(StateBarComponent component);

    /**
     * Add a state bar listener.
     *
     * @param listener The listener to add.
     */
    void addStateBarListener(StateBarListener listener);

    /**
     * Remove a state bar listener.
     *
     * @param listener The listener to remove.
     */
    void removeStateBarListener(StateBarListener listener);

    /**
     * Return all the state bar components.
     *
     * @return A List containing all the state bar components.
     */
    Collection<StateBarComponent> getStateBarComponents();

    /**
     * Return all the tab components.
     *
     * @return A List containing all the tab components.
     */
    Collection<TabComponent> getTabComponents();

    /**
     * Add a tab component.
     *
     * @param component The tab to add.
     */
    void addTabComponent(TabComponent component);

    /**
     * Remove a tab component.
     *
     * @param component The tab to remove.
     */
    void removeTabComponent(TabComponent component);

    /**
     * Ask user for confirmation.
     *
     * @param text  The question.
     * @param title The title.
     * @return true if the user has accepted else false.
     */
    boolean askUserForConfirmation(String text, String title);

    /**
     * Ask the user for confirmation with internationalized message.
     *
     * @param textKey  The question key.
     * @param titleKey The title key.
     * @return true if the user has accepted else false.
     */
    boolean askI18nUserForConfirmation(String textKey, String titleKey);

    /**
     * Ask the user for a text.
     *
     * @param text The text prompt.
     * @return The text of the user.
     */
    String askUserForText(String text);

    /**
     * Display a text.
     *
     * @param text The text to display.
     */
    void displayText(String text);

    /**
     * Display an error.
     *
     * @param error The error to display.
     */
    void displayError(JThequeError error);

    /**
     * Display a internationalized.
     *
     * @param key The internationalization key.
     */
    void displayI18nText(String key);

    /**
     * Choose a file.
     *
     * @param filter A file filter.
     * @return The path to the selected file.
     */
    String chooseFile(SimpleFilter filter);

    /**
     * Choose a directory.
     *
     * @return The path to the selected directory.
     */
    String chooseDirectory();

    /**
     * Add config tab component.
     *
     * @param component The config tab component to add.
     */
    void addConfigTabComponent(ConfigTabComponent component);

    /**
     * Remove the specified config tab component.
     *
     * @param component The config tab component to remove.
     */
    void removeConfigTabComponent(ConfigTabComponent component);

    /**
     * Add a ConfigTabListener.
     *
     * @param listener The listener to add.
     */
    void addConfigTabListener(ConfigTabListener listener);

    /**
     * Remove the specified ConfigTabListener.
     *
     * @param listener The listener to remove.
     */
    void removeConfigTabListener(ConfigTabListener listener);

    /**
     * Add a TabListener.
     *
     * @param listener The listener to add.
     */
    void addTabListener(TabListener listener);

    /**
     * Remove the specified TabListener.
     *
     * @param listener The listener to remove.
     */
    void removeTabListener(TabListener listener);

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
     * Return all window configuration.
     *
     * @return A configuration who encapsulate the configuration of different views.
     */
    WindowsConfiguration getConfigurations();

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
     * Refresh the component.
     *
     * @param c The component to refresh.
     */
    void refresh(Object c);

    /**
     * Set the main component of the main view.
     *
     * @param component The main component of the main view.
     */
    void setMainComponent(ViewComponent component);

    /**
     * Remove the main component of the main view.
     *
     * @param component The main component to remove.
     */
    void removeMainComponent(ViewComponent component);

    /**
     * Indicate if the tab is the main component or not.
     *
     * @return true if the tab is the main component else false.
     */
    boolean isTabMainComponent();

    /**
     * Return the main component.
     *
     * @return The main component.
     */
    ViewComponent getMainComponent();

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
    ViewDelegate getViewDelegate();
}