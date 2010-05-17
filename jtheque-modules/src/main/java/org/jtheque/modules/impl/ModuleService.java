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

import org.jtheque.core.able.ICore;
import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.modules.able.IModuleDescription;
import org.jtheque.modules.able.IModuleLoader;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.IRepository;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.states.able.IStateService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.osgi.framework.BundleException;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A module manager implementation. It manage the cycle life of the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleService implements IModuleService {
    private final WeakEventListenerList listeners = new WeakEventListenerList();

    /**
     * The application repository.
     */
    private IRepository repository;

    /**
     * The configuration of the module manager. It seems the informations about the modules who're
     * installed or disabled.
     */
    private ModuleConfiguration configuration;

    private final IModuleLoader moduleLoader;

    private final List<ModuleContainer> modules = new ArrayList<ModuleContainer>(10);
    private final Collection<ModuleContainer> modulesToLoad = new ArrayList<ModuleContainer>(10);

	@Resource
	private ICore core;

	@Resource
	private IStateService stateService;

	@Resource
	private ILanguageService languageService;

	@Resource
	private IUpdateService updateService;

	@Resource
	private IUIUtils uiUtils;

    /**
     * Indicate if we must refresh the list of the modules to load.
     */
    private boolean mustRefresh = true;

    /**
     * Indicate if there is a collection module.
     */
    private boolean collectionModule;

    public ModuleService(IModuleLoader moduleLoader) {
        super();

        this.moduleLoader = moduleLoader;
    }

    @Override
    public void load() {
        modules.addAll(moduleLoader.loadModules());

        configureModules();

        for (ModuleContainer module : modules) {
            if(module.isCollection()){
                collectionModule = true;
            }

            fireModuleStateChanged(module, module.getState(), null);
        }
    }

    @Override
    public void close() {
        for (Module module : modules) {
            if (module.getState() == ModuleState.UNINSTALLED) {
                configuration.remove(module);

                //TODO : Delete the file. 
            }
        }
    }

    /**
     * Plug the modules.
     */
    @Override
    public void plugModules() {
        for (ModuleContainer module : getModulesToLoad()) {
            setState(module, ModuleState.LOADED);
            try {
                module.getModule().start();
            } catch (BundleException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            }
        }
    }

    /**
     * Return the modules to load.
     *
     * @return A List containing all the modules to load.
     */
    private Collection<ModuleContainer> getModulesToLoad() {
        if (mustRefresh) {
            modulesToLoad.clear();

            addLoadableModules();

            mustRefresh = false;
        }

        return modulesToLoad;
    }

    /**
     * Add the loadable modules to the modules to load list.
     */
    private void addLoadableModules() {
        for (ModuleContainer module : modules) {
            if (canBeLoaded(module) && areAllDependenciesSatisfied(module)) {
                modulesToLoad.add(module);
            }
        }
    }

    /**
     * Test if the module can be loaded.
     *
     * @param module The module to test.
     * @return true if the module can be loaded else false.
     */
    private static boolean canBeLoaded(Module module) {
        return !(module.getState() == ModuleState.DISABLED || module.getState() == ModuleState.UNINSTALLED);
    }

    /**
     * Unplug the modules.
     */
    @Override
    public void unplugModules() {
        List<ModuleContainer> modulesToUnplug = CollectionUtils.copyOf(getModulesToLoad());

        CollectionUtils.reverse(modulesToUnplug);

        for (ModuleContainer module : modulesToUnplug) {
            if (module.getState() == ModuleState.LOADED) {
                try {
                    module.getModule().start();
                } catch (BundleException e) {
                    LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
                }

                setState(module, ModuleState.INSTALLED);

                ModuleResourceCache.removeModule(module.getId());
            }
        }
    }

    /**
     * Configure the modules.
     */
    private void configureModules() {
        configuration = stateService.getState(new ModuleConfiguration());

        //TODO : CollectionUtils.filter(modules, new ConfigurationFilter(configuration, getService(ICore.class).getApplication()));
        CollectionUtils.filter(modules, new CoreVersionFilter(core, uiUtils));

        CollectionUtils.sort(modules, new ModuleComparator());
    }

    @Override
    public Collection<? extends Module> getModules() {
        return modules;
    }

    @Override
    public Collection<IModuleDescription> getModulesFromRepository() {
        return getRepository().getModules();
    }

    @Override
    public IRepository getRepository() {
        if (repository == null) {
            repository = new RepositoryReader().read(core.getApplication().getRepository());
        }

        return repository;
    }

    /**
     * Set the state of a module.
     *
     * @param module The module to set the state.
     * @param state  The state.
     */
    void setState(Module module, ModuleState state) {
        ModuleState oldState = module.getState();

        module.setState(state);

        fireModuleStateChanged(module, state, oldState);

        configuration.setState(module.getId(), state);
    }

    /**
     * Load a module.
     *
     * @param module The module to load.
     */
    @Override
    public void loadModule(Module module) {
        setState(module, ModuleState.LOADED);

        try {
            module.getModule().start();
        } catch (BundleException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    @Override
    public void enableModule(Module module) {
        setState(module, ModuleState.INSTALLED);
    }

    /**
     * Disable a module.
     *
     * @param module The module to disable.
     */
    @Override
    public void disableModule(Module module) {
        setState(module, ModuleState.DISABLED);

        try {
            module.getModule().stop();
        } catch (BundleException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }

        ModuleResourceCache.removeModule(module.getId());

        mustRefresh = true;
    }

    /**
     * Install a module.
     *
     * @param file The file of the module.
     * @return true if the module has been installed, else false.
     */
    @Override
    public boolean installModule(File file) {
        ModuleContainer module = moduleLoader.installModule(file);

        if (module == null) {
            return false;
        }

        modules.add(module);

        fireModuleStateChanged(module, module.getState(), null);

        configuration.add(module);

        return true;
    }

    @Override
    public void install(String versionsFileURL) {
        InstallationResult result = updateService.install(versionsFileURL);

        if (result.isInstalled()) {
            /* TODO install 

             Object module = moduleLoader.loadModule(new File(
                    ModulesServices.get(ICore.class).getFolders().getModulesFolder(), result.getJarFile()));

            ModuleContainer container = createSimpleContainer(module);

            modules.add(container);*/

            configuration.add(result);

            uiUtils.displayI18nText("message.module.repository.installed");
        } else {
            uiUtils.displayI18nText("error.repository.module.not.installed");
        }
    }

    /**
     * Uninstall a module.
     *
     * @param module The module to uninstall.
     */
    @Override
    public void uninstallModule(Module module) {
        setState(module, ModuleState.UNINSTALLED);

        try {
            module.getModule().stop();
        } catch (BundleException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }

        ModuleResourceCache.removeModule(module.getId());
    }

    @Override
    public void addModuleListener(String moduleId, ModuleListener listener) {
        listeners.add(ModuleListener.class, listener);

        ModuleResourceCache.addResource(moduleId, ModuleListener.class, listener);
    }

    private void fireModuleStateChanged(Module container, ModuleState newState, ModuleState oldState) {
        ModuleListener[] l = listeners.getListeners(ModuleListener.class);

        for (ModuleListener listener : l) {
            listener.moduleStateChanged(container, newState, oldState);
        }

	    //Remove listeners from this module
        if(oldState == ModuleState.LOADED && (newState == ModuleState.INSTALLED ||
                newState == ModuleState.DISABLED || newState == ModuleState.UNINSTALLED)){

	        for(ModuleListener listener : ModuleResourceCache.getResource(container.getId(), ModuleListener.class)){
		        listeners.remove(ModuleListener.class, listener);
	        }
	        
            ModuleResourceCache.removeResourceOfType(container.getId(), ModuleListener.class);
        }
    }

    @Override
    public String canModuleLaunched(Module module) {
        if (module.getCoreVersion() != null && module.getCoreVersion().isGreaterThan(ICore.VERSION)) {
            return getMessage("modules.message.versionproblem");
        } else if (!areAllDependenciesSatisfied(module)) {
            return getMessage("error.module.not.loaded.dependency", module.getDependencies());
        } else if (module.getState() == ModuleState.JUST_INSTALLED) {
            return getMessage("error.module.just.installed");
        }

        return "";
    }

    private String getMessage(String key, String... replaces) {
        return languageService.getMessage(key, replaces);
    }

    /**
     * Indicate if all the dependencies of the module are satisfied.
     *
     * @param module The module to test.
     * @return <code>true</code> if all the dependencies are satisfied else <code>false</code>.
     */
    private boolean areAllDependenciesSatisfied(Module module) {
        if (StringUtils.isEmpty(module.getDependencies())) {
            return true;
        }

        for (String dependency : module.getDependencies()) {
            Module resolvedDependency = getModuleById(dependency);

            if (resolvedDependency == null || !canBeLoaded(resolvedDependency)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Module getModuleById(String id) {
        Module module = null;

        for (ModuleContainer m : modules) {
            if (id.equals(m.getId())) {
                module = m;
                break;
            }
        }

        return module;
    }

    @Override
    public boolean isInstalled(String module) {
        return getModuleById(module) != null;
    }

    @Override
    public boolean hasCollectionModule() {
        return collectionModule;
    }
}