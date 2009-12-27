package org.jtheque.core.managers.view;

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

import org.jtheque.core.managers.AbstractManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.able.IAboutView;
import org.jtheque.core.managers.view.able.ICollectionView;
import org.jtheque.core.managers.view.able.ISplashManager;
import org.jtheque.core.managers.view.able.IView;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.ViewDefaults;
import org.jtheque.core.managers.view.able.ViewDelegate;
import org.jtheque.core.managers.view.able.components.StateBarComponent;
import org.jtheque.core.managers.view.able.components.TabComponent;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.edt.Task;
import org.jtheque.core.managers.view.impl.WindowsConfiguration;
import org.jtheque.core.managers.view.impl.components.config.ConfigTabComponent;
import org.jtheque.core.managers.view.listeners.ConfigTabEvent;
import org.jtheque.core.managers.view.listeners.ConfigTabListener;
import org.jtheque.core.managers.view.listeners.StateBarEvent;
import org.jtheque.core.managers.view.listeners.StateBarListener;
import org.jtheque.core.managers.view.listeners.TabEvent;
import org.jtheque.core.managers.view.listeners.TabListener;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.SimpleFilter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A view manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class ViewManager extends AbstractManager implements IViewManager {
    /* Components */
    private final Collection<StateBarComponent> stateBarComponents = new ArrayList<StateBarComponent>(5);
    private final Collection<TabComponent> tabComponents = new ArrayList<TabComponent>(5);
    private Collection<ConfigTabComponent> configPanels;

    private ViewComponent mainComponent;

    private WindowsConfiguration configuration;

    private ViewDelegate viewDelegate;

    private ViewDefaults viewDefaults;

    @Resource
    private Views windowManager;

    @Resource
    private ICollectionView collectionPane;

    @Resource
    private IAboutView aboutPane;

    @Override
    public void displayAboutView() {
        viewDelegate.applyGlassPane(aboutPane.getImpl());
        aboutPane.appear();
    }

    @Override
    public void displayCollectionView() {
        viewDelegate.applyGlassPane(collectionPane.getImpl());
        collectionPane.appear();
    }

    @Override
    public void saveState(IView window, String name) {
        if (configuration != null) {
            configuration.update(name, window);
        }
    }

    @Override
    public void configureView(IView view, String name, int defaultWidth, int defaultHeight) {
        configuration.configure(name, view, defaultWidth, defaultHeight);
    }

    @Override
    public void setMainComponent(ViewComponent component) {
        mainComponent = component;
    }

    @Override
    public void removeMainComponent(ViewComponent component) {
        if (mainComponent == component) {
            mainComponent = null;
        }
    }

    @Override
    public boolean isTabMainComponent() {
        return mainComponent == null;
    }

    @Override
    public ViewComponent getMainComponent() {
        if (isTabMainComponent()) {
            return windowManager.getMainView().getTabbedPane();
        }

        return mainComponent;
    }

    @Override
    public void close() {
        if (windowManager != null && windowManager.getMainView() != null) {
            windowManager.getMainView().closeDown();
        }
    }

    @Override
    public void init() {
        MacOSXConfiguration.configureForMac();

        configuration = getStates().getState(WindowsConfiguration.class);

        if (configuration == null) {
            try {
                configuration = getStates().createState(WindowsConfiguration.class);
            } catch (Exception e) {
                getLogger().error(e, "Unable to retrieve configuration");
                configuration = new WindowsConfiguration();
                getErrorManager().addInternationalizedError("error.loading.configuration");
            }
        }
    }

    @Override
    public WindowsConfiguration getConfigurations() {
        return configuration;
    }

    @Override
    public Views getViews() {
        return windowManager;
    }

    @Override
    public ISplashManager getSplashManager() {
        return SplashManager.getInstance();
    }

    @Override
    public void addStateBarComponent(StateBarComponent component) {
        stateBarComponents.add(component);

        if (component != null && component.getComponent() != null) {
            fireStateBarComponentAdded();
        }
    }

    @Override
    public void removeStateBarComponent(StateBarComponent component) {
        stateBarComponents.remove(component);

        if (component != null && component.getComponent() != null) {
            fireStateBarComponentRemoved(component);
        }
    }

    @Override
    public Collection<StateBarComponent> getStateBarComponents() {
        return CollectionUtils.copyOf(stateBarComponents);
    }

    @Override
    public void addTabComponent(TabComponent component) {
        if (isTabMainComponent()) {
            tabComponents.add(component);

            fireTabComponentAdded();
        }
    }

    @Override
    public void removeTabComponent(TabComponent component) {
        if (isTabMainComponent()) {
            tabComponents.remove(component);

            fireTabComponentRemoved(component);
        }
    }

    @Override
    public Collection<TabComponent> getTabComponents() {
        return CollectionUtils.copyOf(tabComponents);
    }

    @Override
    public void addConfigTabComponent(ConfigTabComponent component) {
        configPanels.add(component);

        fireConfigTabComponentAdded(component);
    }

    @Override
    public void removeConfigTabComponent(ConfigTabComponent component) {
        configPanels.remove(component);

        fireConfigTabComponentRemoved(component);
    }

    @Override
    public Collection<ConfigTabComponent> getConfigTabComponents() {
        return CollectionUtils.copyOf(configPanels);
    }

    @Override
    public boolean askUserForConfirmation(String text, String title) {
        return viewDelegate.askYesOrNo(text, title);
    }

    @Override
    public boolean askI18nUserForConfirmation(String textKey, String titleKey) {
        return viewDelegate.askYesOrNo(getMessage(textKey), getMessage(titleKey));
    }

    @Override
    public String askUserForText(String text) {
        return viewDelegate.askText(text);
    }

    @Override
    public void displayText(String text) {
        viewDelegate.displayText(text);
    }

    @Override
    public String chooseFile(SimpleFilter filter) {
        return viewDelegate.chooseFile(filter);
    }

    @Override
    public String chooseDirectory() {
        return viewDelegate.chooseDirectory();
    }

    @Override
    public void displayI18nText(String key) {
        displayText(getMessage(key));
    }

    @Override
    public void addStateBarListener(StateBarListener listener) {
        getListeners().add(StateBarListener.class, listener);
    }

    @Override
    public void removeStateBarListener(StateBarListener listener) {
        getListeners().remove(StateBarListener.class, listener);
    }

    @Override
    public void addConfigTabListener(ConfigTabListener listener) {
        getListeners().add(ConfigTabListener.class, listener);
    }

    @Override
    public void removeConfigTabListener(ConfigTabListener listener) {
        getListeners().remove(ConfigTabListener.class, listener);
    }

    @Override
    public void addTabListener(TabListener listener) {
        getListeners().add(TabListener.class, listener);
    }

    @Override
    public void removeTabListener(TabListener listener) {
        getListeners().remove(TabListener.class, listener);
    }

    @Override
    public void displayError(JThequeError error) {
        viewDelegate.displayError(error);
    }

    @Override
    public ViewDelegate getViewDelegate() {
        return viewDelegate;
    }

    /**
     * Fire a state bar component added event.
     */
    private static void fireStateBarComponentAdded() {
        StateBarListener[] l = getListeners().getListeners(StateBarListener.class);

        for (StateBarListener listener : l) {
            listener.componentAdded();
        }
    }

    /**
     * Fire a state bar component removed event.
     *
     * @param component The state bar component who's been removed.
     */
    private void fireStateBarComponentRemoved(StateBarComponent component) {
        StateBarListener[] l = getListeners().getListeners(StateBarListener.class);

        StateBarEvent event = new StateBarEvent(this, component);

        for (StateBarListener listener : l) {
            listener.componentRemoved(event);
        }
    }

    /**
     * Fire a config tab component added event.
     *
     * @param component The config tab component who's been added.
     */
    private void fireConfigTabComponentAdded(ConfigTabComponent component) {
        ConfigTabListener[] l = getListeners().getListeners(ConfigTabListener.class);

        ConfigTabEvent event = new ConfigTabEvent(this, component);

        for (ConfigTabListener listener : l) {
            listener.tabAdded(event);
        }
    }

    /**
     * Fire a config tab component removed event.
     *
     * @param component The config tab component who's been removed.
     */
    private void fireConfigTabComponentRemoved(ConfigTabComponent component) {
        ConfigTabListener[] l = getListeners().getListeners(ConfigTabListener.class);

        ConfigTabEvent event = new ConfigTabEvent(this, component);

        for (ConfigTabListener listener : l) {
            listener.tabRemoved(event);
        }
    }

    /**
     * Fire a tab component added event.
     */
    private static void fireTabComponentAdded() {
        TabListener[] l = getListeners().getListeners(TabListener.class);

        for (TabListener listener : l) {
            listener.tabAdded();
        }
    }

    /**
     * Fire a tab component removed event.
     *
     * @param component The tab component who's been removed.
     */
    private void fireTabComponentRemoved(TabComponent component) {
        TabListener[] l = getListeners().getListeners(TabListener.class);

        TabEvent event = new TabEvent(this, component);

        for (TabListener listener : l) {
            listener.tabRemoved(event);
        }
    }

    @Override
    public void execute(SimpleTask task) {
        viewDelegate.run(task.asRunnable());
    }

    @Override
    public <T> T execute(Task<T> task) {
        viewDelegate.run(task.asRunnable());

        return task.getResult();
    }

    @Override
    public void refresh(Object c) {
        viewDelegate.refresh(c);
    }

    @Override
    public ICollectionView getCollectionView() {
        return collectionPane;
    }

    @Override
    public ViewDefaults getViewDefaults() {
        return viewDefaults;
    }

    /**
     * Set the config panels. This is not for use, this is only for Spring Injection.
     *
     * @param configPanels A collection containing all the config tab components.
     */
    public void setConfigPanels(Collection<ConfigTabComponent> configPanels) {
        this.configPanels = CollectionUtils.copyOf(configPanels);
    }

    /**
     * Set the view delegate. This is not for use, this is only for Spring Injection.
     *
     * @param viewDelegate The view delegate implementation.
     */
    public void setViewDelegate(ViewDelegate viewDelegate) {
        this.viewDelegate = viewDelegate;
    }

    /**
     * Set the view defaults. This is not for use, this is only for Spring Injection.
     *
     * @param viewDefaults The view defaults.
     */
    public void setViewDefaults(ViewDefaults viewDefaults) {
        this.viewDefaults = viewDefaults;
    }
}