package org.jtheque.views.impl;

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

import org.jtheque.core.utils.SimplePropertiesCache;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.states.IStateManager;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.ui.able.IView;
import org.jtheque.ui.able.ViewComponent;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.ViewsServices;
import org.jtheque.views.able.IViewManager;
import org.jtheque.views.able.ViewDefaults;
import org.jtheque.views.able.Views;
import org.jtheque.views.able.components.ConfigTabComponent;
import org.jtheque.views.able.components.MainComponent;
import org.jtheque.views.able.components.StateBarComponent;
import org.jtheque.views.able.windows.IAboutView;
import org.jtheque.views.able.panel.ICollectionView;

import javax.annotation.Resource;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A view manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class ViewManager implements IViewManager, ModuleListener {
    private final Collection<StateBarComponent> stateBarComponents = new ArrayList<StateBarComponent>(5);
    private final Collection<MainComponent> mainComponents = new ArrayList<MainComponent>(5);
    private final Collection<ConfigTabComponent> configPanels;

    private final ViewDefaults viewDefaults;

    private final WindowsConfiguration configuration;
    private final Views windowManager;
    private final IUIUtils utils;

    private ViewComponent mainComponent;

    @Resource
    private ICollectionView collectionPane;

    @Resource
    private IAboutView aboutPane;

    public ViewManager(Collection<ConfigTabComponent> configPanels, ViewDefaults viewDefaults, Views windowManager, IUIUtils utils) {
        super();

        this.configPanels = configPanels;
        this.viewDefaults = viewDefaults;
        this.windowManager = windowManager;
        this.utils = utils;

        configuration = ViewsServices.get(IStateManager.class).getOrCreateState(WindowsConfiguration.class);
    }

    @Override
    public void displayAboutView() {
        applyGlassPane(aboutPane.getImpl());
        aboutPane.appear();
    }

    @Override
    public void displayCollectionView() {
        applyGlassPane(collectionPane.getImpl());
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
    public Views getViews() {
        return windowManager;
    }

    @Override
    public void addStateBarComponent(String moduleId, StateBarComponent component) {
        if (component != null && component.getComponent() != null) {
            stateBarComponents.add(component);

            if("true".equals(SimplePropertiesCache.get("statebar-loaded"))){
                windowManager.getMainView().getStateBar().addComponent(component);
            }
            
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

        if("true".equals(SimplePropertiesCache.get("config-view-loaded"))){
            windowManager.getConfigView().sendMessage("add", component);
        }

        ModuleResourceCache.addResource(moduleId, ConfigTabComponent.class, component);
    }

    @Override
    public Collection<ConfigTabComponent> getConfigTabComponents() {
        return CollectionUtils.copyOf(configPanels);
    }
    
    @Override
    public void setSelectedMainComponent(MainComponent component) {
        if(getMainComponents().size() > 1){
            windowManager.getMainView().getTabbedPane().setSelectedComponent(component.getComponent());
        }
    }

    @Override
    public void setSize(IView view, int defaultWidth, int defaultHeight) {
        ((Component) view).setSize(defaultWidth, defaultHeight);
        SwingUtils.centerFrame((Window) view);
    }

    @Override
    public void fill(WindowConfiguration configuration, IView view) {
        Component window = (Component) view;

        configuration.setWidth(window.getWidth());
        configuration.setHeight(window.getHeight());
        configuration.setPositionX(window.getLocation().x);
        configuration.setPositionY(window.getLocation().y);
    }

    @Override
    public void configure(WindowConfiguration configuration, IView view) {
        Window window = (Window) view;

        window.setSize(configuration.getWidth(), configuration.getHeight());

        if (configuration.getPositionX() == -1 || configuration.getPositionY() == -1) {
            SwingUtils.centerFrame(window);
        } else {
            if (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().contains(
                    configuration.getPositionX(), configuration.getPositionY())) {
                window.setLocation(configuration.getPositionX(), configuration.getPositionY());
            } else {
                SwingUtils.centerFrame(window);
            }
        }
    }

    @Override
    public void moduleStateChanged(Module module, ModuleState newState, ModuleState oldState) {
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

    @Override
    public void applyGlassPane(Object component) {
        final Component glass = (Component) component;

        Runnable display = new Runnable() {
            @Override
            public void run() {
                windowManager.getMainView().setGlassPane(glass);

                glass.setVisible(true);

                glass.repaint();

                utils.getDelegate().refresh(glass);
                windowManager.getMainView().refresh();
            }
        };

        utils.getDelegate().run(display);
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
    public ICollectionView getCollectionView() {
        return collectionPane;
    }

    @Override
    public ViewDefaults getViewDefaults() {
        return viewDefaults;
    }
}