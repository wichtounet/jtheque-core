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
import org.jtheque.modules.able.Resources;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.resources.able.IResourceService;
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
    private final List<Module> modules = new ArrayList<Module>(10);
    private final Collection<Module> modulesToLoad = new ArrayList<Module>(10);

    private final IModuleLoader moduleLoader;

    /**
     * The application repository.
     */
    private IRepository repository;

    /**
     * The configuration of the module manager. It seems the informations about the modules who're
     * installed or disabled.
     */
    private ModuleConfiguration configuration;

	@Resource
	private ICore core;

	@Resource
	private IStateService stateService;

	@Resource
	private ILanguageService languageService;

	@Resource
	private IResourceService resourceService;

	@Resource
	private IUpdateService updateService;

	@Resource
	private IUIUtils uiUtils;

    /**
     * Indicate if there is a collection module.
     */
    private boolean collectionModule;

    /**
     * Construct a new ModuleService.
     *
     * @param moduleLoader The module loader.
     */
    public ModuleService(IModuleLoader moduleLoader) {
        super();

        this.moduleLoader = moduleLoader;
    }

    @Override
    public void load() {
        modules.addAll(moduleLoader.loadModules());

        configureModules();

        addLoadableModules();

        for (Module module : modules) {
	        setState(module, ModuleState.INSTALLED);

            if(module.isCollection()){
                collectionModule = true;
            }

            fireModuleInstalled(module);
        }
    }

	/**
	 * Configure the modules.
	 */
	private void configureModules() {
		configuration = stateService.getState(new ModuleConfiguration());

		CollectionUtils.filter(modules, new ConfigurationFilter(configuration, core.getApplication()));
		CollectionUtils.filter(modules, new CoreVersionFilter(core, uiUtils));
		
		CollectionUtils.sort(modules, new ModuleComparator());
	}

	/**
	 * Add the loadable modules to the modules to load list.
	 */
	private void addLoadableModules() {
		for (Module module : modules) {
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
		return !(module.getState() == ModuleState.DISABLED);
	}

    /**
     * Plug the modules.
     */
    @Override
    public void startModules() {
        for (Module module : modulesToLoad) {
            LoggerFactory.getLogger(getClass()).debug("Start module {}", module.getBundle().getSymbolicName());
            startModule(module);
            LoggerFactory.getLogger(getClass()).debug("Module {} started", module.getBundle().getSymbolicName());
        }
    }

    /**
     * Unplug the modules.
     */
    @Override
    public void stopModules() {
        List<Module> modulesToUnplug = CollectionUtils.copyOf(modulesToLoad);

        CollectionUtils.reverse(modulesToUnplug);

        for (Module module : modulesToUnplug) {
            if (module.getState() == ModuleState.STARTED) {
                try {
                    module.getBundle().stop();
                } catch (BundleException e) {
                    LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
                }

                setState(module, ModuleState.INSTALLED);

	            fireModuleStopped(module);

                Resources resources = module.getResources();

                if(resources != null){
                    for (String name : resources.getI18NResources()) {
                        languageService.releaseResource(name);
                    }

                    for (String name : resources.getResources()) {
                        resourceService.releaseResource(name);
                    }
                }

                ModuleResourceCache.removeModule(module.getId());
            }
        }
    }

    @Override
    public Collection<Module> getModules() {
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
     * Start a module. The module cannot be started if it's already started.
     *
     * @param module The module to Start.
     *
     * @throws IllegalStateException If the module is already started.
     */
    @Override
    public void startModule(Module module) {
	    if (module.getState() == ModuleState.STARTED) {
		    throw new IllegalStateException("The module is already started. ");
	    }

        setState(module, ModuleState.STARTED);

	    fireModuleStarted(module);

        try {
            module.getBundle().start();
        } catch (BundleException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }
    }

    @Override
    public void enableModule(Module module) {
        if(module.getState() == ModuleState.DISABLED){
            setState(module, ModuleState.INSTALLED);
        }
    }

    /**
     * Disable a module. The module must be stopped before disabling it.
     *
     * @param module The module to disable.
     *
     * @throws IllegalStateException If the module is started.
     */
    @Override
    public void disableModule(Module module) {
	    if(module.getState() == ModuleState.STARTED){
		    throw new IllegalStateException("The module cannot be disabled when started. ");
	    }

	    setState(module, ModuleState.DISABLED);
    }

    /**
     * Install a module.
     *
     * @param file The file of the module.
     * @return true if the module has been installed, else false.
     */
    @Override
    public boolean installModule(File file) {
        Module module = moduleLoader.installModule(file);

        if (module == null) {
            return false;
        }

        modules.add(module);

        fireModuleInstalled(module);

        configuration.add(module);

        return true;
    }

    @Override
    public void install(String versionsFileURL) {
        InstallationResult result = updateService.install(versionsFileURL);

        if (result.isInstalled()) {
            Module module = moduleLoader.installModule(new File(core.getFolders().getModulesFolder(), result.getJarFile()));
            
            modules.add(module);

            configuration.add(module);

	        fireModuleInstalled(module);

            uiUtils.displayI18nText("message.module.repository.installed");
        } else {
            uiUtils.displayI18nText("error.repository.module.not.installed");
        }
    }

    /**
     * Uninstall a module. The module must be stopped before uninstall it.
     *
     * @param module The module to uninstall.
     *
     * @throws IllegalStateException If the module is started.
     */
    @Override
    public void uninstallModule(Module module) {
	    if (module.getState() == ModuleState.STARTED) {
		    throw new IllegalStateException("The module cannot be disabled when started. ");
	    }

	    configuration.remove(module);
        modulesToLoad.remove(module);
        modules.remove(module);

        fireModuleUninstalled(module);

        for(ModuleListener listener : ModuleResourceCache.getResource(module.getId(), ModuleListener.class)){
            listeners.remove(ModuleListener.class, listener);
        }

        ModuleResourceCache.removeModule(module.getId());
    }

    @Override
    public void addModuleListener(String moduleId, ModuleListener listener) {
        listeners.add(ModuleListener.class, listener);

        ModuleResourceCache.addResource(moduleId, ModuleListener.class, listener);
    }

	@Override
	public String canModuleLaunched(Module module) {
		if (module.getCoreVersion() != null && module.getCoreVersion().isGreaterThan(ICore.VERSION)) {
			return getMessage("modules.message.versionproblem");
		} else if (!areAllDependenciesSatisfied(module)) {
			return getMessage("error.module.not.loaded.dependency", module.getDependencies());
		}

		return "";
	}

	@Override
	public Module getModuleById(String id) {
		Module module = null;

		for (Module m : modules) {
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

	/**
	 * Set the state of a module.
	 *
	 * @param module The module to set the state.
	 * @param state The state.
	 */
	private void setState(Module module, ModuleState state) {
		module.setState(state);

		configuration.setState(module.getId(), state);
	}

	/**
	 * Fire a module started event.
	 *
	 * @param module The started module.
	 */
	private void fireModuleStarted(Module module) {
		for (ModuleListener listener : listeners.getListeners(ModuleListener.class)) {
			listener.moduleStarted(module);
		}
	}

	/**
	 * Fire a module stopped event.
	 *
	 * @param module The stopped module.
	 */
	private void fireModuleStopped(Module module) {
		for (ModuleListener listener : listeners.getListeners(ModuleListener.class)) {
			listener.moduleStopped(module);
		}
	}

	/**
	 * Fire a module installed event.
	 *
	 * @param module The installed module.
	 */
    private void fireModuleInstalled(Module module) {
	    for (ModuleListener listener : listeners.getListeners(ModuleListener.class)) {
	        listener.moduleInstalled(module);
        }
    }

	/**
	 * Fire a module uninstalled event.
	 *
	 * @param module The uninstalled module. 
	 */
    private void fireModuleUninstalled(Module module){
	    for (ModuleListener listener : listeners.getListeners(ModuleListener.class)) {
            listener.moduleUninstalled(module);
        }
    }

    /**
     * Return the internationalized message with the given key.
     *
     * @param key The i18n key.
     * @param replaces The i18n replaces.
     *
     * @return The internationalized message. 
     */
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
}