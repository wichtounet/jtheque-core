package org.jtheque.modules.impl;

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

import org.jtheque.core.ICore;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.core.utils.WeakEventListenerList;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.modules.able.IModuleLoader;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.states.IStateService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.update.IUpdateService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A module manager implementation. It manage the cycle life of the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleService implements IModuleService, BundleContextAware {
    private final WeakEventListenerList listeners = new WeakEventListenerList();

    /**
     * The application repository.
     */
    private Repository repository;

    /**
     * The configuration of the module manager. It seems the informations about the modules who're
     * installed or disabled.
     */
    private ModuleConfiguration configuration;

    private final IModuleLoader moduleLoader;

    private final List<ModuleContainer> modules = new ArrayList<ModuleContainer>(10);
    private final Collection<ModuleContainer> modulesToLoad = new ArrayList<ModuleContainer>(10);

    /**
     * Indicate if we must refresh the list of the modules to load.
     */
    private boolean mustRefresh = true;

    /**
     * Indicate if there is a collection module.
     */
    private boolean collectionModule;

    private BundleContext bundleContext;

    public ModuleService(IModuleLoader moduleLoader) {
        super();

        this.moduleLoader = moduleLoader;
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;

        ModulesServices.setContext(bundleContext);
    }

    @Override
    public void load() {
        modules.addAll(moduleLoader.loadModules());

        configureModules();

        for (ModuleContainer module : modules) {
            if(module.isCollection()){
                collectionModule = true;
            }
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

            getService(ILanguageService.class).removeBaseName(module.getI18n());
        }
    }

    /**
     * Configure the modules.
     */
    private void configureModules() {
        configuration = getService(IStateService.class).getOrCreateState(ModuleConfiguration.class);

        CollectionUtils.filter(modules, new ConfigurationFilter(configuration, getService(ICore.class).getApplication()));
        CollectionUtils.filter(modules, new CoreVersionFilter());

        CollectionUtils.sort(modules, new ModuleComparator());
    }

    private <T> T getService(Class<T> classz) {
        return OSGiUtils.getService(bundleContext, classz);
    }

    @Override
    public Collection<? extends Module> getModules() {
        return modules;
    }

    @Override
    public Collection<ModuleDescription> getModulesFromRepository() {
        return getRepository().getModules();
    }

    @Override
    public Repository getRepository() {
        if (repository == null) {
            repository = new RepositoryReader().read(ModulesServices.get(ICore.class).getApplication().getRepository());
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
        InstallationResult result = ModulesServices.get(IUpdateService.class).install(versionsFileURL);

        if (result.isInstalled()) {
            /* TODO install 

             Object module = moduleLoader.loadModule(new File(
                    ModulesServices.get(ICore.class).getFolders().getModulesFolder(), result.getJarFile()));

            ModuleContainer container = createSimpleContainer(module);

            modules.add(container);*/

            configuration.add(result);

            ModulesServices.get(IUIUtils.class).displayI18nText("message.module.repository.installed");
        } else {
            ModulesServices.get(IUIUtils.class).displayI18nText("error.repository.module.not.installed");
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
    public void addModuleListener(ModuleListener listener) {
        listeners.add(ModuleListener.class, listener);
    }

    @Override
    public void removeModuleListener(ModuleListener listener) {
        listeners.remove(ModuleListener.class, listener);
    }

    private void fireModuleStateChanged(Module container, ModuleState newState, ModuleState oldState) {
        ModuleListener[] l = listeners.getListeners(ModuleListener.class);

        for (ModuleListener listener : l) {
            listener.moduleStateChanged(container, newState, oldState);
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
        return getService(ILanguageService.class).getMessage(key, replaces);
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