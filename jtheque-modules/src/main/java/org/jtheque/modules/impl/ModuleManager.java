package org.jtheque.modules.impl;

import org.jtheque.core.Core;
import org.jtheque.core.impl.Folders;
import org.jtheque.errors.ErrorService;
import org.jtheque.errors.Errors;
import org.jtheque.modules.Module;
import org.jtheque.modules.ModuleException;
import org.jtheque.modules.ModuleException.ModuleOperation;
import org.jtheque.ui.UIUtils;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ThreadUtils;
import org.jtheque.utils.annotations.NotThreadSafe;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.CopyException;
import org.jtheque.utils.io.FileUtils;

import org.osgi.framework.BundleException;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static org.jtheque.modules.ModuleState.DISABLED;
import static org.jtheque.modules.ModuleState.STARTED;

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

/**
 * A manager for the modules. It is only used by the module service.
 *
 * @author Baptiste Wicht
 */
@NotThreadSafe
public final class ModuleManager {
    private final List<Module> modules = CollectionUtils.newConcurrentList();

    @Resource
    private Core core;

    @Resource
    private UIUtils uiUtils;

    @Resource
    private ErrorService errorService;

    @Resource
    private ModuleLoader moduleLoader;

    /**
     * Return all the modules.
     *
     * @return A Collection containing all the modules.
     */
    Collection<Module> getModules() {
        return modules;
    }

    /**
     * Return the module with the given ID.
     *
     * @param id The id of the module to search.
     *
     * @return The module with the specified id or {@code null} if there is no module with this id.
     */
    Module getModuleById(String id) {
        for (Module module : modules) {
            if (id.equals(module.getId())) {
                return module;
            }
        }

        return null;
    }

    /**
     * Indicate if a module with the given id exists or not.
     *
     * @param id The id to search for.
     *
     * @return true if a module exists with this id otherwise false.
     */
    boolean exists(String id) {
        return getModuleById(id) != null;
    }

    /**
     * Start the given module.
     *
     * @param module The module to start.
     *
     * @throws org.jtheque.modules.ModuleException
     *          If there is an OSGi error during start.
     */
    void startModule(Module module) throws ModuleException {
        try {
            module.getBundle().start();
        } catch (BundleException e) {
            throw new ModuleException(e, ModuleOperation.START);
        }
    }

    /**
     * Stop the given module.
     *
     * @param module The module to stop.
     *
     * @throws org.jtheque.modules.ModuleException
     *          If there is an OSGi error during start.
     */
    void stopModule(Module module) throws ModuleException {
        try {
            module.getBundle().stop();
        } catch (BundleException e) {
            throw new ModuleException(e, ModuleOperation.STOP);
        }
    }

    /**
     * Start all the modules using the given launcher.
     *
     * @param moduleLauncher The launcher to use.
     */
    void startAll(ModuleLauncher moduleLauncher) {
        if (isStartingConcurrent()) {
            ModuleStarter starter = new ModuleStarter(moduleLauncher);

            for (Module module : modules) {
                if (canBeLoaded(module) && areAllDependenciesSatisfied(module)) {
                    starter.addModule(module);
                }
            }

            starter.startAll();
        } else {
            for (Module module : modules) {
                if (canBeLoaded(module) && areAllDependenciesSatisfied(module)) {
                    try {
                        moduleLauncher.startModule(module);
                    } catch (ModuleException e) {
                        errorService.addError(Errors.newError(e));
                    }
                }
            }
        }
    }

    /**
     * Indicate if the starting must be made in concurrent.
     *
     * @return {@code true} if the starting is made in parallel or sequentially ({@code false}).
     */
    private static boolean isStartingConcurrent() {
        String property = System.getProperty("jtheque.concurrent.start");

        return StringUtils.isNotEmpty(property) && "true".equalsIgnoreCase(property);
    }

    /**
     * Test if the module can be loaded.
     *
     * @param module The module to test.
     *
     * @return true if the module can be loaded else false.
     */
    static boolean canBeLoaded(Module module) {
        return module.getState() != DISABLED;
    }

    /**
     * Test if there is a dependency on the given module.
     *
     * @param module The module to test for dependencies.
     *
     * @return true if there is a dependency on the given module.
     */
    boolean isThereIsActiveDependenciesOn(Module module) {
        for (Module other : modules) {
            if (other != module && other.getState() == STARTED &&
                    ArrayUtils.contains(other.getDependencies(), module.getId())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Load all the modules.
     */
    void loadModules() {
        //Load all modules
        modules.addAll(moduleLoader.loadModules());

        CollectionUtils.filter(modules, new CoreVersionFilter(core, uiUtils));

        //Must cast to perform the good sort
        CollectionUtils.sort((CopyOnWriteArrayList<Module>) modules, new ModuleComparator());
    }

    /**
     * Install the module from the given file.
     *
     * @param file The file to install.
     *
     * @return The installed module or {@code null} if the module file cannot be installed.
     *
     * @throws org.jtheque.modules.ModuleException
     *          If there is problem installing the file.
     */
    Module installModule(File file) throws ModuleException {
        File moduleFile = installModuleFile(file);

        try {
            Module module = moduleLoader.installModule(moduleFile);

            if (exists(module.getId())) {
                throw new ModuleException("error.module.install.already.exists", ModuleOperation.INSTALL);
            } else {
                modules.add(module);

                return module;
            }
        } catch (ModuleException e){
            FileUtils.delete(moduleFile);

            throw e;
        }
    }

    /**
     * Install the module file. It seems copy it into the application directory and make verifications for the existence
     * of the file.
     *
     * @param file The file of the module.
     *
     * @return The file were the module has been installed.
     *
     * @throws org.jtheque.modules.ModuleException
     *          If there is a problem to copy the file in the good folder.
     */
    private static File installModuleFile(File file) throws ModuleException {
        File target = file;

        if (!FileUtils.isFileInDirectory(file, Folders.getModulesFolder())) {
            target = new File(Folders.getModulesFolder(), file.getName());

            if (target.exists()) {
                throw new ModuleException("errors.module.installFromRepository.already.exists", ModuleOperation.INSTALL);
            } else {
                try {
                    FileUtils.copy(file, target);
                } catch (CopyException e) {
                    throw new ModuleException(e, ModuleOperation.INSTALL);
                }
            }
        }

        return target;
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

    /**
     * Indicate if all the dependencies of the given module are satisfied.
     *
     * @param module The module to test.
     *
     * @return true if the all the dependencies of the module are satisfied else false.
     */
    boolean areAllDependenciesSatisfiedAndActive(Module module) {
        if (StringUtils.isEmpty(module.getDependencies())) {
            return true;
        }

        for (String dependencyId : module.getDependencies()) {
            Module dependency = getModuleById(dependencyId);

            if (dependency == null || dependency.getState() != STARTED) {
                return false;
            }
        }

        return true;
    }

    /**
     * Install the module from the repository from the file.
     *
     * @param file The file of the module.
     *
     * @return The installed Module.
     *
     * @throws org.jtheque.modules.ModuleException
     *          If an exception occurs during install.
     */
    Module installModuleFromRepository(File file) throws ModuleException {
        Module module = moduleLoader.installModule(file);

        uiUtils.displayI18nText("message.module.repository.installed");

        return module;
    }

    /**
     * Uninstall the module.
     *
     * @param module The module to uninstall.
     *
     * @throws org.jtheque.modules.ModuleException
     *          If an error occurs during the uninstallation of the bundle.
     */
    void uninstallModule(Module module) throws ModuleException {
        try {
            module.getBundle().uninstall();
        } catch (BundleException e) {
            throw new ModuleException(e, ModuleOperation.UNINSTALL);
        }

        moduleLoader.uninstallModule(module);

        modules.remove(module);

        //Delete the bundle file
        FileUtils.delete(StringUtils.delete(module.getBundle().getLocation(), "file:"));
    }

    /**
     * A starter for the modules. This started load the modules with several threads.
     *
     * @author Baptiste Wicht
     */
    private final class ModuleStarter {
        private final Set<Module> startList = CollectionUtils.newSet(5);
        private final ModuleLauncher moduleLauncher;

        private final ExecutorService startersPool = Executors.newFixedThreadPool(ThreadUtils.processors());

        private final Semaphore semaphore = new Semaphore(0, true);
        private CountDownLatch countDown;

        /**
         * Construct a new ModuleStarter.
         *
         * @param moduleLauncher The module launcher to use.
         */
        private ModuleStarter(ModuleLauncher moduleLauncher) {
            super();

            this.moduleLauncher = moduleLauncher;
        }

        /**
         * Add a module to start.
         *
         * @param module The module to start.
         */
        public void addModule(Module module) {
            startList.add(module);
        }

        /**
         * Start all the modules of the starter.
         */
        public void startAll() {
            if (startList.isEmpty()) {
                return;
            }

            countDown = new CountDownLatch(startList.size());

            //startReadyModules();

            while (true) {
                try {
                    semaphore.acquire();

                    startReadyModules();

                    if (startList.isEmpty()) {
                        break;
                    }
                } catch (InterruptedException e) {
                    LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);

                    Thread.currentThread().interrupt();

                    break;
                }
            }

            try {
                countDown.await();
            } catch (InterruptedException e) {
                LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);

                Thread.currentThread().interrupt();
            }

            startersPool.shutdown();
        }

        /**
         * Start the currently ready modules.
         */
        private void startReadyModules() {
            for (Iterator<Module> iterator = startList.iterator(); iterator.hasNext();) {
                Module module = iterator.next();

                if (canBeStarted(module)) {
                    startersPool.submit(new ModuleStarterRunnable(this, module, moduleLauncher));
                    iterator.remove();
                } else if (!(canBeLoaded(module) && areAllDependenciesSatisfied(module))) {
                    //Perhaps the start of a module has not been successful and this module cannot be launched anymore

                    countDown.countDown();
                    iterator.remove();
                }
            }
        }

        /**
         * Indicate if the module can be started.
         *
         * @param module The module to test.
         *
         * @return {@code true} if the module can be started else {@code false}.
         */
        public boolean canBeStarted(Module module) {
            if (module.getCoreVersion() != null && module.getCoreVersion().isGreaterThan(Core.VERSION)) {
                return false;
            }

            return areAllDependenciesSatisfiedAndActive(module);

        }
    }

    /**
     * A simple runnable to start a module.
     *
     * @author Baptiste Wicht
     */
    private final class ModuleStarterRunnable implements Runnable {
        private final ModuleStarter starter;
        private final Module module;
        private final ModuleLauncher moduleLauncher;

        /**
         * Construct a ModuleStarterRunnable for the given module.
         *
         * @param starter        The starter.
         * @param module         The module to start.
         * @param moduleLauncher The module launcher to start the module.
         */
        private ModuleStarterRunnable(ModuleStarter starter, Module module, ModuleLauncher moduleLauncher) {
            super();

            this.starter = starter;
            this.module = module;
            this.moduleLauncher = moduleLauncher;

        }

        @Override
        public void run() {
            try {
                moduleLauncher.startModule(module);
            } catch (ModuleException e) {
                errorService.addError(Errors.newError(e));
            }

            starter.semaphore.release();
            starter.countDown.countDown();
        }
    }
}