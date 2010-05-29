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

import java.io.File;
import java.util.Collection;

/**
 * A module manager. It seems a manager who's responsible for loading and managing the different modules of the
 * application.
 *
 * @author Baptiste Wicht
 */
public interface IModuleService {
    /**
     * Return all the modules.
     *
     * @return All the modules.
     */
    Collection<Module> getModules();

    /**
     * Add a module listener.
     *
     * @param moduleId The id of the module who want to add a listener. If this id is given, the listener will
     *                 be automatically removed after it stopped.
     * @param listener The listener to add.
     */
    void addModuleListener(String moduleId, ModuleListener listener);

    /**
     * Return all the modules of the application repository.
     *
     * @return all the modules of the application repository.
     */
    Collection<IModuleDescription> getModulesFromRepository();

    /**
     * Return the repository of the application.
     *
     * @return The Repository.
     */
    IRepository getRepository();

    /**
     * Test if the module is installed.
     *
     * @param module The module name.
     * @return true if the module is installed.
     */
    boolean isInstalled(String module);

    /**
     * Return the module with the name.
     *
     * @param name The name of the module.
     * @return The module.
     */
    Module getModuleById(String name);

    /**
     * Install the module from the versions file.
     *
     * @param versionsFileURL The URL of the versions file.
     */
    void install(String versionsFileURL);

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
     * @return true if the module has been installed else false.
     */
    boolean installModule(File file);

    /**
     * Uninstall the module.
     *
     * @param module The module to uninstall.
     */
    void uninstallModule(Module module);

    /**
     * Test if a module can ben launched.
     *
     * @param module The module to be launched.
     * @return The error message. If the error is empty, the module can be launched.
     */
    String canModuleLaunched(Module module);

    /**
     * Load the module.
     *
     * @param module The module to load.
     */
    void startModule(Module module);

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
}