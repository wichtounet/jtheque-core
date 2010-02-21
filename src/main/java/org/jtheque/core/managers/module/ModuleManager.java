package org.jtheque.core.managers.module;

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
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.module.annotations.Module;
import org.jtheque.core.managers.module.beans.CollectionBasedModule;
import org.jtheque.core.managers.module.beans.EmptyBeanMethod;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.module.beans.ModuleState;
import org.jtheque.core.managers.module.loaders.IModuleLoader;
import org.jtheque.core.managers.module.loaders.ModuleLoader;
import org.jtheque.core.managers.update.IUpdateManager;
import org.jtheque.core.managers.update.InstallationResult;
import org.jtheque.core.managers.update.repository.ModuleDescription;
import org.jtheque.core.managers.update.repository.Repository;
import org.jtheque.core.managers.update.repository.RepositoryReader;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;

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
public final class ModuleManager extends AbstractManager implements IModuleManager {
    /**
     * The application repository.
     */
    private Repository repository;

    /**
     * The configuration of the module manager. It seems the informations about the modules who're
     * installed or disabled.
     */
    private ModuleConfiguration configuration;

    @Resource
    private IModuleLoader moduleLoader;

    private final List<ModuleContainer> moduleContainers = new ArrayList<ModuleContainer>(10);
    private final Collection<ModuleContainer> modulesToLoad = new ArrayList<ModuleContainer>(10);

    /**
     * Indicate if we must refresh the list of the modules to load.
     */
    private boolean mustRefresh = true;

    /**
     * Indicate if there is a collection module.
     */
    private boolean collectionModule = false;

    @Override
    public void preInit() {
        moduleContainers.addAll(ModuleLoader.getModules());

        loadModuleBeans();

        for (JThequeError error : ModuleLoader.getErrors()) {
            Managers.getManager(IViewManager.class).displayError(error);
        }
    }

    /**
     * Load the module beans.
     */
    private void loadModuleBeans() {
        for (ModuleContainer module : moduleContainers) {
            module.setModule(Managers.getManager(IBeansManager.class).getBean(module.getBeanName()));

            Managers.getManager(ILanguageManager.class).addBaseName(module.getInfos().i18n());

            if (CollectionBasedModule.class.isAssignableFrom(module.getModule().getClass())) {
                collectionModule = true;
            }
        }
    }

    @Override
    public void close() {
        for (ModuleContainer module : moduleContainers) {
            if (module.getState() == ModuleState.UNINSTALLED) {
                configuration.remove(module);

                //TODO : Delete the file. 
            }
        }
    }
    
    @Override
    public void prePlugModules() {
        configureModules();

        for (ModuleContainer module : getModulesToLoad()) {
            module.getPrePlugMethod().run();
            setState(module, ModuleState.LOADED);
        }
    }

    /**
     * Plug the modules.
     */
    @Override
    public void plugModules() {
        for (ModuleContainer module : getModulesToLoad()) {
            module.getPlugMethod().run();
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
        for (ModuleContainer module : moduleContainers) {
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
    private static boolean canBeLoaded(ModuleContainer module) {
        return !(module.getState() == ModuleState.DISABLED || module.getState() == ModuleState.UNINSTALLED);
    }

    /**
     * Return the dependency of the module.
     *
     * @param module The module to get the dependency from.
     * @return The dependency of the module or null if the module has no dependency.
     */
    private static String[] getDependencies(ModuleContainer module) {
        return module.getInfos().dependencies();
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
                module.getUnPlugMethod().run();

                setState(module, ModuleState.INSTALLED);
            }

            Managers.getManager(ILanguageManager.class).removeBaseName(module.getInfos().i18n());
        }
    }

    /**
     * Configure the modules.
     */
    private void configureModules() {
        configuration = getStates().getState(ModuleConfiguration.class);

        if (configuration == null) {
            initConfiguration();
        }

        CollectionUtils.filter(moduleContainers, new ConfigurationFilter(configuration));
        CollectionUtils.filter(moduleContainers, new CoreVersionFilter());

        CollectionUtils.sort(moduleContainers, new ModuleComparator());
    }

    /**
     * Init the configuration of the modules.
     */
    private void initConfiguration() {
        try {
            configuration = getStates().createState(ModuleConfiguration.class);
        } catch (Exception e) {
            configuration = new ModuleConfiguration();
            getLogger().error(e);
            getErrorManager().addInternationalizedError("error.loading.configuration");
        }

        for (ModuleContainer module : moduleContainers) {
            configuration.add(module, ModuleState.INSTALLED);
        }
    }

    @Override
    public Collection<ModuleContainer> getModules() {
        return moduleContainers;
    }

    @Override
    public Collection<ModuleDescription> getModulesFromRepository() {
        return getRepository().getModules();
    }

    @Override
    public Repository getRepository() {
        if (repository == null) {
            repository = new RepositoryReader().read(Managers.getCore().getApplication().getRepository());
        }

        return repository;
    }

    /**
     * Set the state of a module.
     *
     * @param module The module to set the state.
     * @param state  The state.
     */
    void setState(ModuleContainer module, ModuleState state) {
        module.setState(state);
        configuration.setState(module.getId(), state);
    }

    /**
     * Load a module.
     *
     * @param module The module to load.
     */
    @Override
    public void loadModule(ModuleContainer module) {
        setState(module, ModuleState.LOADED);

        module.getPrePlugMethod().run();
        module.getPlugMethod().run();
    }

    @Override
    public void enableModule(ModuleContainer module) {
        setState(module, ModuleState.INSTALLED);
    }

    /**
     * Disable a module.
     *
     * @param module The module to disable.
     */
    @Override
    public void disableModule(ModuleContainer module) {
        setState(module, ModuleState.DISABLED);

        module.getUnPlugMethod().run();

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
        Object module = moduleLoader.installModule(file);

        if (module == null) {
            return false;
        }

        ModuleContainer container = createSimpleContainer(module);

        moduleContainers.add(container);

        configuration.add(container);

        fireModuleAdded();

        return true;
    }

    @Override
    public void install(String versionsFileURL) {
        InstallationResult result = Managers.getManager(IUpdateManager.class).install(versionsFileURL);

        if (result.isInstalled() && result.isMustRestart()) {
            configuration.add(result);

            Managers.getManager(IViewManager.class).displayI18nText("message.module.repository.installed.restart");
        } else if (result.isInstalled()) {
            Object module = moduleLoader.loadModule(new File(
                    Managers.getCore().getFolders().getModulesFolder(), result.getJarFile()));

            ModuleContainer container = createSimpleContainer(module);

            moduleContainers.add(container);

            configuration.add(result);

            fireModuleAdded();

            getManager(IViewManager.class).displayI18nText("message.module.repository.installed");
        } else {
            getManager(IViewManager.class).displayI18nText("error.repository.module.not.installed");
        }
    }

    /**
     * Create a simple module container for the module.
     *
     * @param module The module.
     * @return The module container for the specified module.
     */
    private static ModuleContainer createSimpleContainer(Object module) {
        ModuleContainer container = new ModuleContainer("notABean", module.getClass().getAnnotation(Module.class));

        container.setModule(module);
        container.setState(ModuleState.JUST_INSTALLED);
        container.setPlugMethod(new EmptyBeanMethod());
        container.setPrePlugMethod(new EmptyBeanMethod());
        container.setUnPlugMethod(new EmptyBeanMethod());

        return container;
    }

    /**
     * Uninstall a module.
     *
     * @param module The module to uninstall.
     */
    @Override
    public void uninstallModule(ModuleContainer module) {
        setState(module, ModuleState.UNINSTALLED);

        module.getUnPlugMethod().run();

        fireModuleRemoved(module);
    }

    @Override
    public void addModuleListener(ModuleListener listener) {
        getListeners().add(ModuleListener.class, listener);
    }

    @Override
    public void removeModuleListener(ModuleListener listener) {
        getListeners().remove(ModuleListener.class, listener);
    }

    /**
     * Fire a module added event.
     */
    private static void fireModuleAdded() {
        ModuleListener[] l = getListeners().getListeners(ModuleListener.class);

        for (ModuleListener listener : l) {
            listener.moduleAdded();
        }
    }

    /**
     * Fire a module removed event.
     *
     * @param module The removed module.
     */
    private static void fireModuleRemoved(ModuleContainer module) {
        ModuleListener[] l = getListeners().getListeners(ModuleListener.class);

        for (ModuleListener listener : l) {
            listener.moduleRemoved(module);
        }
    }

    @Override
    public String canModuleLaunched(ModuleContainer module) {
        if (new Version(module.getInfos().core()).isGreaterThan(Managers.getCore().getCoreCurrentVersion())) {
            return getMessage("modules.message.versionproblem");
        } else if (!areAllDependenciesSatisfied(module)) {
            return getMessage("error.module.not.loaded.dependency", module.getInfos().dependencies());
        } else if (module.getState() == ModuleState.JUST_INSTALLED) {
            return getMessage("error.module.just.installed");
        }

        return "";
    }

    /**
     * Indicate if all the dependencies of the module are satisfied.
     *
     * @param module The module to test.
     * @return <code>true</code> if all the dependencies are satisfied else <code>false</code>.
     */
    private boolean areAllDependenciesSatisfied(ModuleContainer module) {
        if (StringUtils.isEmpty(getDependencies(module))) {
            return true;
        }

        for (String dependency : getDependencies(module)) {
            ModuleContainer resolvedDependency = getModuleById(dependency);

            if (resolvedDependency == null || !canBeLoaded(resolvedDependency)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ModuleContainer getModuleById(String id) {
        ModuleContainer module = null;

        for (ModuleContainer m : moduleContainers) {
            if (id.equals(m.getInfos().id())) {
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