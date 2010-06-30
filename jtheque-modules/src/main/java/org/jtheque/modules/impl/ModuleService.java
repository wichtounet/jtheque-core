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
import org.jtheque.images.able.IImageService;
import org.jtheque.modules.able.IModuleDescription;
import org.jtheque.modules.able.IModuleLoader;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.IRepository;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.modules.able.Resources;
import org.jtheque.modules.able.SwingLoader;
import org.jtheque.modules.utils.ImageResource;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.states.able.IStateService;
import org.jtheque.ui.able.IUIUtils;
import org.jtheque.update.able.IUpdateService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.CopyException;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.utils.ui.SwingUtils;

import org.osgi.framework.BundleException;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;

import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A module manager implementation. It manage the cycle life of the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleService implements IModuleService {
    private final WeakEventListenerList listeners = new WeakEventListenerList();
    private final List<Module> modules = new ArrayList<Module>(10);
    private final Map<String, SwingLoader> loaders = new HashMap<String, SwingLoader>(10);

    private final IModuleLoader moduleLoader;

    /**
     * The application repository.
     */
    private IRepository repository;

    /**
     * The configuration of the module manager. It seems the informations about the modules who're installed or
     * disabled.
     */
    private ModuleConfiguration configuration;

    @Resource
    private ICore core;

    @Resource
    private IStateService stateService;

    @Resource
    private IImageService imageService;

    @Resource
    private IUpdateService updateService;

    @Resource
    private ILanguageService languageService;

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

        Runtime.getRuntime().addShutdownHook(new ModuleStopHook());
    }

    @Override
    public void load() {
        SwingUtils.assertNotEDT("load()");

        modules.addAll(moduleLoader.loadModules());

        configuration = stateService.getState(new ModuleConfiguration());

        CollectionUtils.filter(modules, new CoreVersionFilter(core, uiUtils));
        CollectionUtils.sort(modules, new ModuleComparator());

        for (Module module : modules) {
            //Configuration
            if (configuration.containsModule(module)) {
                module.setState(configuration.getState(module.getId()));
            } else {
                module.setState(ModuleState.INSTALLED);
                configuration.add(module);
            }

            //Colllection modules
            if (module.isCollection()) {
                collectionModule = true;
            }

            //Indicate the module as installed
            fireModuleInstalled(module);
        }
    }

    /**
     * Test if the module can be loaded.
     *
     * @param module The module to test.
     *
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
        SwingUtils.assertNotEDT("startModules()");

        for (Module module : modules) {
            if (canBeLoaded(module) && areAllDependenciesSatisfied(module)) {
                startModule(module);
            }
        }
    }

    /**
     * Unplug the modules.
     */
    @Override
    public void stopModules() {
        List<Module> modulesToUnplug = CollectionUtils.copyOf(modules);

        CollectionUtils.reverse(modulesToUnplug);

        for (Module module : modulesToUnplug) {
            if (module.getState() == ModuleState.STARTED) {
                stopModule(module);
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

    @Override
    public void registerSwingLoader(String moduleId, SwingLoader swingLoader) {
        loaders.put(moduleId, swingLoader);
    }

    @Override
    public void startModule(Module module) {
        SwingUtils.assertNotEDT("startModule(Module)");

        if (module.getState() == ModuleState.STARTED) {
            throw new IllegalStateException("The module is already started. ");
        }

        LoggerFactory.getLogger(getClass()).debug("Start module {}", module.getBundle().getSymbolicName());

        //Add images resources
        loadImageResources(module);

        try {
            module.getBundle().start();
        } catch (BundleException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }

        if (loaders.containsKey(module.getId())) {
            loaders.get(module.getId()).afterAll();
            loaders.remove(module.getId());
        }

        setState(module, ModuleState.STARTED);

        fireModuleStarted(module);

        LoggerFactory.getLogger(getClass()).debug("Module {} started", module.getBundle().getSymbolicName());
    }


    private void loadImageResources(Module container) {
        for (ImageResource imageResource : container.getResources().getImageResources()) {
            String resource = imageResource.getResource();

            if (resource.startsWith("classpath:")) {
                imageService.registerResource(imageResource.getName(),
                        new UrlResource(container.getBundle().getResource(resource.substring(10))));
            }
        }
    }

    @Override
    public void stopModule(Module module) {
        SwingUtils.assertNotEDT("stopModule(Module)");

        if (module.getState() != ModuleState.STARTED) {
            throw new IllegalStateException("The module is already started. ");
        }

        LoggerFactory.getLogger(getClass()).debug("Stop module {}", module.getBundle().getSymbolicName());

        setState(module, ModuleState.INSTALLED);

        fireModuleStopped(module);

        Resources resources = module.getResources();

        if (resources != null) {
            for (ImageResource imageResource : resources.getImageResources()) {
                imageService.releaseResource(imageResource.getName());
            }
        }

        ModuleResourceCache.removeModule(module.getId());

        try {
            module.getBundle().stop();
        } catch (BundleException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }

        LoggerFactory.getLogger(getClass()).debug("Module {} stopped", module.getBundle().getSymbolicName());
    }

    @Override
    public void enableModule(Module module) {
        if (module.getState() == ModuleState.DISABLED) {
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
        if (module.getState() == ModuleState.STARTED) {
            throw new IllegalStateException("The module cannot be disabled when started. ");
        }

        setState(module, ModuleState.DISABLED);
    }

    @Override
    public void installModule(File file) {
        File moduleFile = installModuleFile(file);

        if(moduleFile != null){
            Module module = moduleLoader.installModule(moduleFile);

            if (module == null) {
                FileUtils.delete(moduleFile);

                uiUtils.displayI18nText("error.module.not.installed");
            } else if (exists(module.getId())) {
                uiUtils.displayI18nText("errors.module.install.already.exists");
            } else {
                module.setState(ModuleState.INSTALLED);

                modules.add(module);
                configuration.add(module);

                fireModuleInstalled(module);

                uiUtils.displayI18nText("message.module.installed");
            }
        }
    }

    private File installModuleFile(File file) {
        File target = file;

        if (!FileUtils.isFileInDirectory(file, core.getFolders().getModulesFolder())) {
            target = new File(core.getFolders().getModulesFolder(), file.getName());

            if (target.exists()) {
                uiUtils.displayI18nText("errors.module.install.already.exists");

                return null;
            } else {
                try {
                    FileUtils.copy(file, target);
                } catch (CopyException e) {
                    LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);

                    uiUtils.displayI18nText("errors.module.install.copy");

                    return null;
                }
            }
        }

        return target;
    }

    private boolean exists(String id) {
        for(Module module : modules){
            if(id.equals(module.getId())){
                return true;
            }
        }

        return false;
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

    @Override
    public void uninstallModule(Module module) {
        if (module.getState() == ModuleState.STARTED) {
            stopModule(module);
        }

        moduleLoader.uninstallModule(module);

        configuration.remove(module);
        modules.remove(module);

        try {
            module.getBundle().uninstall();
        } catch (BundleException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
        }

        //Delete the bundle file
        FileUtils.delete(StringUtils.delete(module.getBundle().getLocation(), "file:"));

        fireModuleUninstalled(module);

        for (ModuleListener listener : ModuleResourceCache.getResource(module.getId(), ModuleListener.class)) {
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
     * @param state  The state.
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
    private void fireModuleUninstalled(Module module) {
        for (ModuleListener listener : listeners.getListeners(ModuleListener.class)) {
            listener.moduleUninstalled(module);
        }
    }

    /**
     * Return the internationalized message with the given key.
     *
     * @param key      The i18n key.
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
     *
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

    private final class ModuleStopHook extends Thread {
        @Override
        public void run() {
            stopModules();
        }
    }
}