package org.jtheque.views.impl;

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

import org.jtheque.core.utils.SimplePropertiesCache;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.spring.utils.SwingSpringProxy;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.views.able.IViews;
import org.jtheque.views.able.components.ConfigTabComponent;
import org.jtheque.views.able.components.IStateBarComponent;
import org.jtheque.views.able.components.MainComponent;
import org.jtheque.views.able.panel.IModuleView;
import org.jtheque.views.able.panel.IRepositoryView;
import org.jtheque.views.able.windows.IConfigView;
import org.jtheque.views.able.windows.IErrorView;
import org.jtheque.views.able.windows.IEventView;
import org.jtheque.views.able.windows.ILicenseView;
import org.jtheque.views.able.windows.IMainView;
import org.jtheque.views.able.windows.IMessageView;
import org.jtheque.views.able.windows.IUpdateView;
import org.jtheque.views.impl.components.config.JPanelConfigAppearance;
import org.jtheque.views.impl.components.config.JPanelConfigNetwork;
import org.jtheque.views.impl.components.config.JPanelConfigOthers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.JComponent;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A window manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class Views implements IViews, ApplicationContextAware, ModuleListener {
    private SwingSpringProxy<ILicenseView> licenseView;
    private SwingSpringProxy<IConfigView> configView;
    private SwingSpringProxy<IModuleView> moduleView;
    private SwingSpringProxy<IMessageView> messageView;
    private SwingSpringProxy<IEventView> eventView;
    private SwingSpringProxy<IRepositoryView> repositoryView;
    private SwingSpringProxy<IUpdateView> updateView;
    private SwingSpringProxy<IErrorView> errorView;
    private SwingSpringProxy<IMainView> mainView;

    private final Collection<MainComponent> mainComponents = new ArrayList<MainComponent>(5);
    private final Collection<IStateBarComponent> stateBarComponents = new ArrayList<IStateBarComponent>(5);
    private final Collection<ConfigTabComponent> configPanels = new ArrayList<ConfigTabComponent>(5);

    private ApplicationContext applicationContext;

    @Resource
    private IModuleService moduleService;

    /**
     * Register a module listener to the module service.
     */
    @PostConstruct
    public void register() {
        moduleService.addModuleListener("", this);
    }

    @Override
    public void setSelectedView(MainComponent component) {
        mainView.get().setSelectedComponent(component.getImpl());
    }

    @Override
    public MainComponent getSelectedView() {
        MainComponent selected = null;

        JComponent component = mainView.get().getSelectedComponent();

        for (MainComponent tab : mainComponents) {
            if (tab.getImpl().equals(component)) {
                selected = tab;
                break;
            }
        }

        return selected;
    }

    @Override
    public IMainView getMainView() {
        return mainView.get();
    }

    @Override
    public ILicenseView getLicenseView() {
        return licenseView.get();
    }

    @Override
    public IConfigView getConfigView() {
        return configView.get();
    }

    @Override
    public IErrorView getErrorView() {
        return errorView.get();
    }

    @Override
    public IModuleView getModuleView() {
        return moduleView.get();
    }

    @Override
    public IMessageView getMessagesView() {
        return messageView.get();
    }

    @Override
    public IEventView getEventView() {
        return eventView.get();
    }

    @Override
    public IRepositoryView getRepositoryView() {
        return repositoryView.get();
    }

    @Override
    public IUpdateView getUpdateView() {
        return updateView.get();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

        mainView = new SwingSpringProxy<IMainView>(IMainView.class, applicationContext);
        licenseView = new SwingSpringProxy<ILicenseView>(ILicenseView.class, applicationContext);
        configView = new SwingSpringProxy<IConfigView>(IConfigView.class, applicationContext);
        moduleView = new SwingSpringProxy<IModuleView>(IModuleView.class, applicationContext);
        messageView = new SwingSpringProxy<IMessageView>(IMessageView.class, applicationContext);
        eventView = new SwingSpringProxy<IEventView>(IEventView.class, applicationContext);
        repositoryView = new SwingSpringProxy<IRepositoryView>(IRepositoryView.class, applicationContext);
        updateView = new SwingSpringProxy<IUpdateView>(IUpdateView.class, applicationContext);
        errorView = new SwingSpringProxy<IErrorView>(IErrorView.class, applicationContext);

        //Pre init the view
        errorView.get();
    }

    @Override
    public void addMainComponent(String moduleId, MainComponent component) {
        mainComponents.add(component);

        getMainView().sendMessage("add", component);

        ModuleResourceCache.addResource(moduleId, MainComponent.class, component);
    }

    @Override
    public Collection<MainComponent> getMainComponents() {
        return CollectionUtils.copyOf(mainComponents);
    }

    @Override
    public void setSelectedMainComponent(MainComponent component) {
        if (mainComponents.size() > 1) {
            getMainView().getTabbedPane().setSelectedComponent(component.getImpl());
        }
    }

    @Override
    public void addStateBarComponent(String moduleId, IStateBarComponent component) {
        if (component != null && component.getComponent() != null) {
            stateBarComponents.add(component);

            if ("true".equals(SimplePropertiesCache.get("statebar-loaded"))) {
                getMainView().getStateBar().addComponent(component);
            }

            ModuleResourceCache.addResource(moduleId, IStateBarComponent.class, component);
        }
    }

    @Override
    public Collection<IStateBarComponent> getStateBarComponents() {
        return CollectionUtils.copyOf(stateBarComponents);
    }

    @Override
    public void addConfigTabComponent(String moduleId, ConfigTabComponent component) {
        configPanels.add(component);

        if ("true".equals(SimplePropertiesCache.get("config-view-loaded"))) {
            getConfigView().sendMessage("add", component);
        }

        ModuleResourceCache.addResource(moduleId, ConfigTabComponent.class, component);
    }

    @Override
    public Collection<ConfigTabComponent> getConfigTabComponents() {
        return CollectionUtils.copyOf(configPanels);
    }

    @Override
    public void init() {
        addConfigTabComponent("", new SwingSpringProxy<JPanelConfigAppearance>(JPanelConfigAppearance.class, applicationContext).get());
        addConfigTabComponent("", new SwingSpringProxy<JPanelConfigOthers>(JPanelConfigOthers.class, applicationContext).get());
        addConfigTabComponent("", new SwingSpringProxy<JPanelConfigNetwork>(JPanelConfigNetwork.class, applicationContext).get());
    }

    @Override
    public void moduleStarted(Module module) {
        //Nothing to change here
    }

    @Override
    public void moduleStopped(Module module) {
        removeMainComponents(ModuleResourceCache.getResource(module.getId(), MainComponent.class));
        removeStateBarComponents(ModuleResourceCache.getResource(module.getId(), IStateBarComponent.class));
        removeConfigTabComponents(ModuleResourceCache.getResource(module.getId(), ConfigTabComponent.class));

        ModuleResourceCache.removeResourceOfType(module.getId(), MainComponent.class);
        ModuleResourceCache.removeResourceOfType(module.getId(), IStateBarComponent.class);
        ModuleResourceCache.removeResourceOfType(module.getId(), ConfigTabComponent.class);
    }

    @Override
    public void moduleInstalled(Module module) {
        //Nothing to change here
    }

    @Override
    public void moduleUninstalled(Module module) {
        //Nothing to change here
    }

    /**
     * Remove the given main components.
     *
     * @param components The main components to remove.
     */
    private void removeMainComponents(Iterable<MainComponent> components) {
        for (MainComponent component : components) {
            mainComponents.remove(component);

            getMainView().sendMessage("remove", component);
        }
    }

    /**
     * Remove the given state bar components.
     *
     * @param components The state bar components to remove.
     */
    private void removeStateBarComponents(Iterable<IStateBarComponent> components) {
        for (IStateBarComponent component : components) {
            stateBarComponents.remove(component);

            if (component != null && component.getComponent() != null) {
                getMainView().getStateBar().removeComponent(component);
            }
        }
    }

    /**
     * Remove the given config tab components.
     *
     * @param components The config tab components to remove.
     */
    private void removeConfigTabComponents(Iterable<ConfigTabComponent> components) {
        for (ConfigTabComponent component : components) {
            configPanels.remove(component);

            if ("true".equals(SimplePropertiesCache.get("config-view-loaded"))) {
                getConfigView().sendMessage("remove", component);
            }
        }
    }
}