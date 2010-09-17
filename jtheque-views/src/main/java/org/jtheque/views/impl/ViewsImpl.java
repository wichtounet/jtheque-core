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

import org.jtheque.core.Core;
import org.jtheque.messages.MessageService;
import org.jtheque.modules.Module;
import org.jtheque.modules.ModuleListener;
import org.jtheque.modules.ModuleResourceCache;
import org.jtheque.modules.ModuleService;
import org.jtheque.ui.Controller;
import org.jtheque.ui.UIUtils;
import org.jtheque.updates.UpdateService;
import org.jtheque.utils.SimplePropertiesCache;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.ui.SwingUtils;
import org.jtheque.views.Views;
import org.jtheque.views.components.ConfigTabComponent;
import org.jtheque.views.components.MainComponent;
import org.jtheque.views.components.StateBarComponent;
import org.jtheque.views.impl.components.config.JPanelConfigAppearance;
import org.jtheque.views.impl.components.config.JPanelConfigNetwork;
import org.jtheque.views.impl.components.config.JPanelConfigOthers;
import org.jtheque.views.panel.ModuleView;
import org.jtheque.views.windows.ConfigView;
import org.jtheque.views.windows.MainView;
import org.jtheque.views.windows.MessageView;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.JComponent;

import java.util.Collection;
import java.util.List;

/**
 * A window manager implementation.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class ViewsImpl implements Views, ApplicationContextAware, ModuleListener {
    @GuardedInternally
    private final Collection<MainComponent> mainComponents = CollectionUtils.newConcurrentSet();

    @GuardedInternally
    private final Collection<StateBarComponent> stateBarComponents = CollectionUtils.newConcurrentSet();

    @GuardedInternally
    private final Collection<ConfigTabComponent> configPanels = CollectionUtils.newConcurrentSet();

    private ApplicationContext applicationContext;

    @Resource
    private ModuleService moduleService;

    @Resource
    private Controller<ConfigView> configController;

    @Resource
    private Controller<MessageView> messageController;

    @Resource
    private Controller<ModuleView> moduleController;

    @Resource
    private Controller<MainView> generalController;

    @Resource
    private MessageService messageService;

    @Resource
    private UIUtils uiUtils;

    @Resource
    private Core core;

    @Resource
    private UpdateService updateService;

    /**
     * Register a module listener to the module service.
     */
    @PostConstruct
    public void register() {
        moduleService.addModuleListener("", this);
    }

    @Override
    public void setSelectedView(final MainComponent component) {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                generalController.getView().setSelectedComponent(component.getImpl());
            }
        });
    }

    @Override
    public MainComponent getSelectedView() {
        MainComponent selected = null;

        JComponent component = generalController.getView().getSelectedComponent();

        for (MainComponent tab : mainComponents) {
            if (tab.getImpl().equals(component)) {
                selected = tab;
                break;
            }
        }

        return selected;
    }

    @Override
    public MainView getMainView() {
        SwingUtils.assertEDT("Views.getMainView()");

        return generalController.getView();
    }

    @Override
    public void addMainComponent(String moduleId, final MainComponent component) {
        mainComponents.add(component);

        ModuleResourceCache.addResource(moduleId, MainComponent.class, component);

        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                generalController.getView().sendMessage("add", component);
            }
        });
    }

    @Override
    public Collection<MainComponent> getMainComponents() {
        return CollectionUtils.copyOf(mainComponents);
    }

    @Override
    public void setSelectedMainComponent(final MainComponent component) {
        if (mainComponents.size() > 1) {
            SwingUtils.inEdt(new Runnable() {
                @Override
                public void run() {
                    generalController.getView().getTabbedPane().setSelectedComponent(component.getImpl());
                }
            });
        }
    }

    @Override
    public void addStateBarComponent(String moduleId, final StateBarComponent component) {
        if (component != null && component.getComponent() != null) {
            stateBarComponents.add(component);

            ModuleResourceCache.addResource(moduleId, StateBarComponent.class, component);

            if (SimplePropertiesCache.get("statebar-loaded", Boolean.class)) {
                SwingUtils.inEdt(new Runnable() {
                    @Override
                    public void run() {
                        generalController.getView().getStateBar().addComponent(component);
                    }
                });
            }
        }
    }

    @Override
    public Collection<StateBarComponent> getStateBarComponents() {
        return CollectionUtils.copyOf(stateBarComponents);
    }

    @Override
    public void addConfigTabComponent(String moduleId, final ConfigTabComponent component) {
        configPanels.add(component);

        ModuleResourceCache.addResource(moduleId, ConfigTabComponent.class, component);

        if (SimplePropertiesCache.get("config-view-loaded", Boolean.class)) {
            SwingUtils.inEdt(new Runnable() {
                @Override
                public void run() {
                    configController.getView().sendMessage("add", component);
                }
            });
        }
    }

    @Override
    public Collection<ConfigTabComponent> getConfigTabComponents() {
        return CollectionUtils.copyOf(configPanels);
    }

    @Override
    public void init() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                addConfigTabComponent("", applicationContext.getBean(JPanelConfigAppearance.class));
                addConfigTabComponent("", applicationContext.getBean(JPanelConfigOthers.class));
                addConfigTabComponent("", applicationContext.getBean(JPanelConfigNetwork.class));
            }
        });
    }

    @Override
    public void displayConditionalViews() {
        if (messageService.isDisplayNeeded()) {
            messageController.getView().display();
        }

        if (core.getConfiguration().verifyUpdateOnStartup()) {
            List<String> messages = updateService.getPossibleUpdates(moduleService.getModules());

            for (String message : messages) {
                if (uiUtils.askI18nUserForConfirmation(message, message + ".title")) {
                    moduleController.getView().display();
                    break;
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void moduleStarted(Module module) {
        //Nothing to change here
    }

    @Override
    public void moduleStopped(Module module) {
        removeMainComponents(ModuleResourceCache.getResources(module.getId(), MainComponent.class));
        removeStateBarComponents(ModuleResourceCache.getResources(module.getId(), StateBarComponent.class));
        removeConfigTabComponents(ModuleResourceCache.getResources(module.getId(), ConfigTabComponent.class));

        ModuleResourceCache.removeResourceOfType(module.getId(), MainComponent.class);
        ModuleResourceCache.removeResourceOfType(module.getId(), StateBarComponent.class);
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
        for (final MainComponent component : components) {
            mainComponents.remove(component);

            SwingUtils.inEdt(new Runnable() {
                @Override
                public void run() {
                    generalController.getView().sendMessage("remove", component);
                }
            });
        }
    }

    /**
     * Remove the given state bar components.
     *
     * @param components The state bar components to remove.
     */
    private void removeStateBarComponents(Iterable<StateBarComponent> components) {
        for (final StateBarComponent component : components) {
            stateBarComponents.remove(component);

            if (component != null && component.getComponent() != null) {
                SwingUtils.inEdt(new Runnable() {
                    @Override
                    public void run() {
                        generalController.getView().getStateBar().removeComponent(component);
                    }
                });
            }
        }
    }

    /**
     * Remove the given config tab components.
     *
     * @param components The config tab components to remove.
     */
    private void removeConfigTabComponents(Iterable<ConfigTabComponent> components) {
        for (final ConfigTabComponent component : components) {
            configPanels.remove(component);

            if (SimplePropertiesCache.get("config-view-loaded", Boolean.class)) {
                SwingUtils.inEdt(new Runnable() {
                    @Override
                    public void run() {
                        configController.getView().sendMessage("remove", component);
                    }
                });
            }
        }
    }
}