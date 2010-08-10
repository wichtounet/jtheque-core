package org.jtheque.modules.able;

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

import org.jtheque.update.able.InstallationResult;

import java.io.File;
import java.util.Collection;

/**
 * A module manager. It seems a manager who's responsible for loading and managing the different modules of the
 * application.
 *
 * @author Baptiste Wicht
 */
public interface ModuleService {
    /**
     * Return all the modules.
     *
     * @return All the modules.
     */
    Collection<? extends Module> getModules();

    /**
     * Add a module listener.
     *
     * @param moduleId The id of the module who want to add a listener. If this id is given, the listener will be
     *                 automatically removed after it stopped.
     * @param listener The listener to add.
     */
    void addModuleListener(String moduleId, ModuleListener listener);

    /**
     * Return all the modules of the application repository.
     *
     * @return all the modules of the application repository.
     */
    Collection<ModuleDescription> getModulesFromRepository();

    /**
     * Return the repository of the application.
     *
     * @return The Repository.
     */
    Repository getRepository();

    /**
     * Test if the module is installed.
     *
     * @param module The module name.
     *
     * @return true if the module is installed.
     */
    boolean isInstalled(String module);

    /**
     * Return the module with the name.
     *
     * @param name The name of the module.
     *
     * @return The module.
     */
    Module getModuleById(String name);

    void install(InstallationResult result);

    /**
     * Unplug the modules.
     */
    void stopModules();

    /**
     * Enable a module.
     *
     * @param module The module to enable.
     */
    void enableModule(Module module);

    /**
     * Plug the modules.
     */
    void startModules();

    /**
     * Disable the module.
     *
     * @param module The module to disable.
     */
    void disableModule(Module module);

    /**
     * Install the module of the file.
     *
     * @param file The file of the module.
     */
    void installModule(File file);

    /**
     * Uninstall the module.
     *
     * @param module The module to uninstall.
     */
    void uninstallModule(Module module);

    /**
     * Test if a module can ben started.
     *
     * @param module The module to be started.
     *
     * @return The error message. If the error is empty, the module can be launched.
     */
    String canBeStarted(Module module);

    /**
     * Test if a module can ben stopped.
     *
     * @param module The module to be stopped.
     *
     * @return The error message. If the error is empty, the module can be launched.
     */
    String canBeStopped(Module module);

    /**
     * Test if a module can ben uninstalled.
     *
     * @param module The module to be uninstalled.
     *
     * @return The error message. If the error is empty, the module can be uninstalled.
     */
    String canBeUninstalled(Module module);

    /**
     * Test if a module can ben disabled.
     *
     * @param module The module to be disabled.
     *
     * @return The error message. If the error is empty, the module can be disabled.
     */
    String canBeDisabled(Module module);

    /**
     * Start a module. The module cannot be started if it's already started.
     *
     * @param module The module to Start.
     *
     * @throws IllegalStateException If the module is already started.
     */
    void startModule(Module module);

    /**
     * Stop the module.
     *
     * @param module The module to stop.
     *
     * @throws IllegalStateException If the module is already stopped.
     */
    void stopModule(Module module);

    /**
     * Indicate if the primary module is collection based.
     *
     * @return true if the primary module is collection based else false.
     */
    boolean hasCollectionModule();

    /**
     * Load the modules.
     */
    void load();

    /**
     * Register a swing loader. This loader will be called after the module loading.
     *
     * @param moduleId    The id of the module.
     * @param swingLoader The swing loader.
     */
    void registerSwingLoader(String moduleId, SwingLoader swingLoader);

    /**
     * Indicate if the given module need two phases loading or not. A two phases loading indicate that we must
     * previously set the collection and then load the module.
     *
     * @param module The module.
     *
     * @return true if the module need two phases loading else false.
     */
    boolean needTwoPhasesLoading(Module module);
}