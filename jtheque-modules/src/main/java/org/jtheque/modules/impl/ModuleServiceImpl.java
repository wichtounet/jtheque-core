package org.jtheque.modules.impl;

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
import org.jtheque.images.ImageService;
import org.jtheque.modules.Module;
import org.jtheque.modules.ModuleDescription;
import org.jtheque.modules.ModuleListener;
import org.jtheque.modules.ModuleResourceCache;
import org.jtheque.modules.ModuleService;
import org.jtheque.modules.ModuleState;
import org.jtheque.modules.Repository;
import org.jtheque.modules.SwingLoader;
import org.jtheque.states.StateService;
import org.jtheque.ui.UIUtils;
import org.jtheque.update.InstallationResult;
import org.jtheque.utils.SimplePropertiesCache;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.collections.WeakEventListenerList;
import org.jtheque.utils.ui.SwingUtils;

import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.jtheque.modules.ModuleState.*;

/**
 * A module manager implementation. It manage the cycle life of the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleServiceImpl implements ModuleService {
    private final Map<String, SwingLoader> loaders = CollectionUtils.newConcurrentMap(5);
    private final Map<Module, ModuleResources> resources = CollectionUtils.newConcurrentMap(5);

    @GuardedInternally
    private final WeakEventListenerList<ModuleListener> listeners = WeakEventListenerList.create();

    /**
     * The configuration of the module manager. It seems the informations about the modules who're installed or
     * disabled.
     */
    @GuardedInternally
    private final ModuleConfiguration configuration;

    @Resource
    private Core core;

    @Resource
    private ImageService imageService;

    @Resource
    private UIUtils uiUtils;

    @Resource
    private ModuleManager moduleManager;

    private volatile boolean loaded;

    /**
     * Indicate if there is a collection module.
     */
    private boolean collectionModule;

    public ModuleServiceImpl(StateService stateService) {
        super();

        configuration = stateService.getState(new ModuleConfiguration());
    }

    @Override
    public void load() {
        SwingUtils.assertNotEDT("load()");

        if (loaded) {
            throw new IllegalStateException("Cannot be loaded twice");
        }

        loaded = true;

        moduleManager.loadModules();

        for (Module module : moduleManager.getModules()) {
            //Configuration
            if (configuration.containsModule(module)) {
                module.setState(configuration.getState(module.getId()));
            } else {
                module.setState(INSTALLED);
                configuration.add(module);
            }

            //If a collection module must be launched
            if (ModuleManager.canBeLoaded(module) && module.isCollection()) {
                collectionModule = true;
            }

            //Indicate the module as installed
            fireModuleInstalled(module);
        }
    }

    /**
     * Plug the modules.
     */
    @Override
    public void startModules() {
        SwingUtils.assertNotEDT("startModules()");

        synchronized (this) {
            moduleManager.startAll();
        }
    }

    /**
     * Stop and uninstall all the modules.
     */
    @PreDestroy
    private void shutdown() {
        stopModules();

        synchronized (this) {
            moduleManager.uninstallModules();
        }
    }

    /**
     * Stop all the modules
     */
    private void stopModules() {
        List<Module> modulesToUnplug = CollectionUtils.copyOf(moduleManager.getModules());

        CollectionUtils.reverse(modulesToUnplug);

        for (Module module : modulesToUnplug) {
            if (module.getState() == STARTED) {
                stopModule(module);
            }
        }
    }

    @Override
    public Collection<Module> getModules() {
        return CollectionUtils.protect(moduleManager.getModules());
    }

    @Override
    public Collection<ModuleDescription> getModulesFromRepository() {
        return getRepository().getModules();
    }

    @Override
    public Repository getRepository() {
        return RepositoryReader.getCachedRepository(core.getApplication().getRepository());
    }

    @Override
    public void registerSwingLoader(String moduleId, SwingLoader swingLoader) {
        loaders.put(moduleId, swingLoader);
    }

    @Override
    public boolean needTwoPhasesLoading(Module module) {
        return module.isCollection() && !SimplePropertiesCache.get("collectionChosen", Boolean.class);
    }

    @Override
    public void startModule(Module module) {
        SwingUtils.assertNotEDT("startModule(Module)");

        LoggerFactory.getLogger(getClass()).debug("Start module {}", module.getBundle().getSymbolicName());

        if (needTwoPhasesLoading(module)) {
            throw new IllegalStateException("The module needs a collection");
        }

        synchronized (this) {
            if (module.getState() == STARTED) {
                throw new IllegalStateException("The module is already started. ");
            }

            setState(module, STARTED);
        }

        //Add images resources
        loadImageResources(module);

        moduleManager.startModule(module);

        SwingLoader loader = loaders.remove(module.getId());
        if (loader != null) {
            loader.afterAll();
        }

        fireModuleStarted(module);

        LoggerFactory.getLogger(getClass()).debug("Module {} started", module.getBundle().getSymbolicName());
    }

    /**
     * Load the image resources of the module.
     *
     * @param module The module to load the image resources for.
     */
    private void loadImageResources(Module module) {
        for (ImageResource imageResource : resources.get(module).getImageResources()) {
            String resource = imageResource.getResource();

            if (resource.startsWith("classpath:")) {
                imageService.registerResource(imageResource.getName(),
                        new UrlResource(module.getBundle().getResource(resource.substring(10))));
            }
        }
    }

    @Override
    public void stopModule(Module module) {
        SwingUtils.assertNotEDT("stopModule(Module)");

        LoggerFactory.getLogger(getClass()).debug("Stop module {}", module.getBundle().getSymbolicName());

        synchronized (this) {
            if (module.getState() != STARTED) {
                throw new IllegalStateException("The module is already started. ");
            }

            setState(module, INSTALLED);
        }

        for (ImageResource imageResource : resources.get(module).getImageResources()) {
            imageService.releaseResource(imageResource.getName());
        }

        ModuleResourceCache.removeModule(module.getId());

        moduleManager.stopModule(module);

        fireModuleStopped(module);

        LoggerFactory.getLogger(getClass()).debug("Module {} stopped", module.getBundle().getSymbolicName());
    }

    @Override
    public void enableModule(Module module) {
        if (module.getState() == DISABLED) {
            setState(module, INSTALLED);
        }
    }

    @Override
    public void disableModule(Module module) {
        if (module.getState() == STARTED) {
            stopModule(module);

            setState(module, DISABLED);
        }
    }

    @Override
    public void installModule(File file) {
        Module module = moduleManager.installModule(file);

        if (module != null) {
            installModule(module);
        }
    }

    private void installModule(Module module) {
        configuration.add(module);

        fireModuleInstalled(module);
    }

    @Override
    public void install(InstallationResult result) {
        if (result.isInstalled()) {
            installModule(moduleManager.installModule(result));
        } else {
            uiUtils.displayI18nText("error.repository.module.not.installed");
        }
    }

    @Override
    public void uninstallModule(Module module) {
        if (module.getState() == STARTED) {
            stopModule(module);
        }

        configuration.remove(module);

        moduleManager.uninstallModule(module);

        fireModuleUninstalled(module);

        for (ModuleListener listener : ModuleResourceCache.getResources(module.getId(), ModuleListener.class)) {
            listeners.remove(listener);
        }

        ModuleResourceCache.removeModule(module.getId());
    }

    @Override
    public void addModuleListener(String moduleId, ModuleListener listener) {
        listeners.add(listener);

        ModuleResourceCache.addResource(moduleId, ModuleListener.class, listener);
    }

    @Override
    public String canBeStarted(Module module) {
        if (module.getCoreVersion() != null && module.getCoreVersion().isGreaterThan(Core.VERSION)) {
            return "modules.message.versionproblem";
        }

        if (!moduleManager.areAllDependenciesSatisfiedAndActive(module)) {
            return "error.module.not.loaded.dependency";
        }

        return "";
    }

    @Override
    public String canBeStopped(Module module) {
        if (module.getState() != STARTED) {
            return "error.module.not.started";
        }

        if (moduleManager.isThereIsActiveDependenciesOn(module)) {
            return "error.module.dependencies";
        }

        return "";
    }

    @Override
    public String canBeUninstalled(Module module) {
        if (module.getState() == STARTED && moduleManager.isThereIsActiveDependenciesOn(module)) {
            return "error.module.dependencies";
        }

        return "";
    }

    @Override
    public String canBeDisabled(Module module) {
        if (module.getState() == DISABLED) {
            return "error.module.not.enabled";
        }

        if (module.getState() == STARTED && moduleManager.isThereIsActiveDependenciesOn(module)) {
            return "error.module.dependencies";
        }

        return "";
    }

    @Override
    public Module getModuleById(String id) {
        return moduleManager.getModuleById(id);
    }

    @Override
    public boolean isInstalled(String id) {
        return moduleManager.exists(id);
    }

    @Override
    public boolean hasCollectionModule() {
        return collectionModule;
    }

    /**
     * Set the state of a module.
     *
     * @param module The module to set the state.
     * @param state  The state.
     */
    private void setState(Module module, ModuleState state) {
        synchronized (this){
            module.setState(state);

            configuration.setState(module.getId(), state);
        }
    }

    /**
     * Fire a module started event.
     *
     * @param module The started module.
     */
    private void fireModuleStarted(Module module) {
        for (ModuleListener listener : listeners) {
            listener.moduleStarted(module);
        }
    }

    /**
     * Fire a module stopped event.
     *
     * @param module The stopped module.
     */
    private void fireModuleStopped(Module module) {
        for (ModuleListener listener : listeners) {
            listener.moduleStopped(module);
        }
    }

    /**
     * Fire a module installed event.
     *
     * @param module The installed module.
     */
    private void fireModuleInstalled(Module module) {
        for (ModuleListener listener : listeners) {
            listener.moduleInstalled(module);
        }
    }

    /**
     * Fire a module uninstalled event.
     *
     * @param module The uninstalled module.
     */
    private void fireModuleUninstalled(Module module) {
        for (ModuleListener listener : listeners) {
            listener.moduleUninstalled(module);
        }
    }

    ModuleResources getResources(Module module) {
        return resources.get(module);
    }

    void setResources(Module module, ModuleResources resources) {
        this.resources.put(module, resources);
    }
}