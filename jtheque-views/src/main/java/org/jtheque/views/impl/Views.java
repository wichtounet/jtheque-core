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
import org.jtheque.spring.utils.SwingSpringProxy;
import org.jtheque.spring.utils.injection.Injector;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.able.IViews;
import org.jtheque.views.able.components.ConfigTabComponent;
import org.jtheque.views.able.components.MainComponent;
import org.jtheque.views.able.components.StateBarComponent;
import org.jtheque.views.able.panel.IModuleView;
import org.jtheque.views.able.panel.IRepositoryView;
import org.jtheque.views.able.windows.IConfigView;
import org.jtheque.views.able.windows.ILicenceView;
import org.jtheque.views.able.windows.ILogView;
import org.jtheque.views.able.windows.IMainView;
import org.jtheque.views.able.windows.IMessageView;
import org.jtheque.views.able.windows.IUpdateView;
import org.jtheque.views.impl.components.config.JPanelConfigAppearance;
import org.jtheque.views.impl.components.config.JPanelConfigNetwork;
import org.jtheque.views.impl.components.config.JPanelConfigOthers;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.swing.JComponent;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A window manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class Views implements IViews, ApplicationContextAware, ModuleListener {
    private SwingSpringProxy<ILicenceView> licenceView;
    private SwingSpringProxy<IConfigView> configView;
    private SwingSpringProxy<IModuleView> moduleView;
    private SwingSpringProxy<IMessageView> messageView;
    private SwingSpringProxy<ILogView> logView;
    private SwingSpringProxy<IRepositoryView> repositoryView;
    private SwingSpringProxy<IUpdateView> updateView;
    private SwingSpringProxy<IMainView> mainView;

    private final Collection<MainComponent> mainComponents = new ArrayList<MainComponent>(5);
    private final Collection<StateBarComponent> stateBarComponents = new ArrayList<StateBarComponent>(5);
    private final Collection<ConfigTabComponent> configPanels = new ArrayList<ConfigTabComponent>(5);
    
    private ApplicationContext applicationContext;

    @Override
    public void init(){
        SwingUtils.inEdt(new Runnable(){
            @Override
            public void run() {
                addConfigTabComponent("", new JPanelConfigAppearance());
                addConfigTabComponent("", new JPanelConfigOthers());
                addConfigTabComponent("", new JPanelConfigNetwork());
            }
        });
    }

    @Override
    public void setSelectedView(MainComponent component) {
        mainView.get().setSelectedComponent(component.getComponent());
    }

    @Override
    public MainComponent getSelectedView() {
        MainComponent selected = null;

        JComponent component = mainView.get().getSelectedComponent();
        
        for (MainComponent tab : mainComponents) {
            if (tab.getComponent().equals(component)) {
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
    public ILicenceView getLicenceView() {
        return licenceView.get();
    }

    @Override
    public IConfigView getConfigView() {
        return configView.get();
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
    public ILogView getLogView() {
        return logView.get();
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
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

        mainView = new SwingSpringProxy<IMainView>(IMainView.class, applicationContext);
        licenceView = new SwingSpringProxy<ILicenceView>(ILicenceView.class, applicationContext);
        configView = new SwingSpringProxy<IConfigView>(IConfigView.class, applicationContext);
        moduleView = new SwingSpringProxy<IModuleView>(IModuleView.class, applicationContext);
        messageView = new SwingSpringProxy<IMessageView>(IMessageView.class, applicationContext);
        logView = new SwingSpringProxy<ILogView>(ILogView.class, applicationContext);
        repositoryView = new SwingSpringProxy<IRepositoryView>(IRepositoryView.class, applicationContext);
        updateView = new SwingSpringProxy<IUpdateView>(IUpdateView.class, applicationContext);
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
        if(mainComponents.size() > 1){
            getMainView().getTabbedPane().setSelectedComponent(component.getComponent());
        }
    }

    @Override
    public void addStateBarComponent(String moduleId, StateBarComponent component) {
        if (component != null && component.getComponent() != null) {
            stateBarComponents.add(component);

            if("true".equals(SimplePropertiesCache.get("statebar-loaded"))){
                getMainView().getStateBar().addComponent(component);
            }

            ModuleResourceCache.addResource(moduleId, StateBarComponent.class, component);
        }
    }

    @Override
    public Collection<StateBarComponent> getStateBarComponents() {
        return CollectionUtils.copyOf(stateBarComponents);
    }

    @Override
    public void addConfigTabComponent(String moduleId, ConfigTabComponent component) {
        Injector.inject(applicationContext, component);

        configPanels.add(component);

        if("true".equals(SimplePropertiesCache.get("config-view-loaded"))){
            getConfigView().sendMessage("add", component);
        }

        ModuleResourceCache.addResource(moduleId, ConfigTabComponent.class, component);
    }

    @Override
    public Collection<ConfigTabComponent> getConfigTabComponents() {
        return CollectionUtils.copyOf(configPanels);
    }

    @Override
    public void moduleStateChanged(Module module, ModuleState newState, ModuleState oldState) {
        if(oldState == ModuleState.LOADED && (newState == ModuleState.INSTALLED ||
                newState == ModuleState.DISABLED || newState == ModuleState.UNINSTALLED)){

            removeMainComponents(ModuleResourceCache.getResource(module.getId(), MainComponent.class));
            removeStateBarComponents(ModuleResourceCache.getResource(module.getId(), StateBarComponent.class));
            removeConfigTabComponents(ModuleResourceCache.getResource(module.getId(), ConfigTabComponent.class));

            ModuleResourceCache.removeResourceOfType(module.getId(), MainComponent.class);
            ModuleResourceCache.removeResourceOfType(module.getId(), StateBarComponent.class);
            ModuleResourceCache.removeResourceOfType(module.getId(), ConfigTabComponent.class);
        }
    }

    private void removeMainComponents(Iterable<MainComponent> components) {
        for (MainComponent component : components) {
            mainComponents.remove(component);

            getMainView().sendMessage("remove", component);
        }
    }

    private void removeStateBarComponents(Iterable<StateBarComponent> components) {
        for (StateBarComponent component : components) {
            stateBarComponents.remove(component);

            if (component != null && component.getComponent() != null) {
                getMainView().getStateBar().removeComponent(component);
            }
        }
    }

    private void removeConfigTabComponents(Iterable<ConfigTabComponent> components) {
        for (ConfigTabComponent component : components) {
            configPanels.remove(component);

            getConfigView().sendMessage("remove", component);
        }
    }
}