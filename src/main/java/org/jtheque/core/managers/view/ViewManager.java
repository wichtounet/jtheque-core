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
import org.jtheque.core.managers.module.ModuleListener;
import org.jtheque.core.managers.module.ModuleResourceCache;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.module.beans.ModuleState;
import org.jtheque.core.managers.view.able.IAboutView;
import org.jtheque.core.managers.view.able.ICollectionView;
import org.jtheque.core.managers.view.able.ISplashManager;
import org.jtheque.core.managers.view.able.IView;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.able.ViewDefaults;
import org.jtheque.core.managers.view.able.ViewDelegate;
import org.jtheque.core.managers.view.able.components.MainComponent;
import org.jtheque.core.managers.view.able.components.StateBarComponent;
import org.jtheque.core.managers.view.able.components.ViewComponent;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.edt.Task;
import org.jtheque.core.managers.view.impl.WindowsConfiguration;
import org.jtheque.core.managers.view.impl.components.config.ConfigTabComponent;
import org.jtheque.utils.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A view manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class ViewManager extends AbstractManager implements IViewManager, ModuleListener {
    private final Collection<StateBarComponent> stateBarComponents = new ArrayList<StateBarComponent>(5);
    private final Collection<MainComponent> mainComponents = new ArrayList<MainComponent>(5);
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
    public void close() {
        if (windowManager != null && windowManager.getMainView() != null) {
            windowManager.getMainView().closeDown();
        }
    }

    @Override
    public void init() {
        MacOSXConfiguration.configureForMac();

        configuration = getStates().getOrCreateState(WindowsConfiguration.class);
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
    public void addStateBarComponent(String moduleId, StateBarComponent component) {
        if (component != null && component.getComponent() != null) {
            stateBarComponents.add(component);

            windowManager.getMainView().getStateBar().addComponent(component);

            ModuleResourceCache.addResource(moduleId, StateBarComponent.class, component);
        }
    }

    @Override
    public Collection<StateBarComponent> getStateBarComponents() {
        return CollectionUtils.copyOf(stateBarComponents);
    }

    @Override
    public void addMainComponent(String moduleId, MainComponent component) {
        mainComponents.add(component);

        windowManager.getMainView().sendMessage("add", component);

        ModuleResourceCache.addResource(moduleId, MainComponent.class, component);
    }

    @Override
    public Collection<MainComponent> getMainComponents() {
        return CollectionUtils.copyOf(mainComponents);
    }

    @Override
    public void addConfigTabComponent(String moduleId, ConfigTabComponent component) {
        configPanels.add(component);

        windowManager.getConfigView().sendMessage("add", component);

        ModuleResourceCache.addResource(moduleId, ConfigTabComponent.class, component);
    }

    @Override
    public Collection<ConfigTabComponent> getConfigTabComponents() {
        return CollectionUtils.copyOf(configPanels);
    }

    @Override
    public boolean askI18nUserForConfirmation(String textKey, String titleKey) {
        return viewDelegate.askUserForConfirmation(getMessage(textKey), getMessage(titleKey));
    }

    @Override
    public void displayI18nText(String key) {
        viewDelegate.displayText(getMessage(key));
    }

    @Override
    public ViewDelegate getDelegate() {
        return viewDelegate;
    }

    @Override
    public void setSelectedMainComponent(MainComponent component) {
        if(getMainComponents().size() > 1){
            windowManager.getMainView().getTabbedPane().setSelectedComponent(component.getComponent());
        }
    }

    @Override
    public void moduleStateChanged(ModuleContainer module, ModuleState newState, ModuleState oldState) {
        if(oldState == ModuleState.LOADED && (newState == ModuleState.INSTALLED ||
                newState == ModuleState.DISABLED || newState == ModuleState.UNINSTALLED)){

            for(StateBarComponent component : ModuleResourceCache.getResource(module.getId(), StateBarComponent.class)){
                removeStateBarComponent(component);
            }

            for(MainComponent component : ModuleResourceCache.getResource(module.getId(), MainComponent.class)){
                removeTabComponent(component);
            }

            for(ConfigTabComponent component : ModuleResourceCache.getResource(module.getId(), ConfigTabComponent.class)){
                removeConfigTabComponent(component);
            }

            for(ViewComponent component : ModuleResourceCache.getResource(module.getId(), ViewComponent.class)){
                removeMainComponent(component);
            }

            ModuleResourceCache.removeResourceOfType(module.getId(), StateBarComponent.class);
            ModuleResourceCache.removeResourceOfType(module.getId(), MainComponent.class);
            ModuleResourceCache.removeResourceOfType(module.getId(), ConfigTabComponent.class);
            ModuleResourceCache.removeResourceOfType(module.getId(), ViewComponent.class);
        }
    }

    private void removeStateBarComponent(StateBarComponent component) {
        stateBarComponents.remove(component);

        if (component != null && component.getComponent() != null) {
            windowManager.getMainView().getStateBar().removeComponent(component);
        }
    }

    private void removeTabComponent(MainComponent component) {
        mainComponents.remove(component);

        windowManager.getMainView().sendMessage("remove", component);
    }

    private void removeConfigTabComponent(ConfigTabComponent component) {
        configPanels.remove(component);

        windowManager.getConfigView().sendMessage("remove", component);
    }

    private void removeMainComponent(ViewComponent component) {
        if (mainComponent == component) {
            mainComponent = null;
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