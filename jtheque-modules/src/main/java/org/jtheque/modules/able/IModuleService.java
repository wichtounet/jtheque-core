package org.jtheque.modules.able;

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

import org.jtheque.modules.impl.ModuleDescription;
import org.jtheque.modules.impl.Repository;

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
    Collection<? extends Module> getModules();

    /**
     * Add a module listener.
     *
     * @param listener The listener to add.
     */
    void addModuleListener(ModuleListener listener);

    /**
     * Remove a module listener.
     *
     * @param listener The listener to remove.
     */
    void removeModuleListener(ModuleListener listener);

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
    void unplugModules();

    /**
     * Enable a module.
     *
     * @param module The module to enable.
     */
    void enableModule(Module module);

    /**
     * Plug the modules.
     */
    void plugModules();

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
    void loadModule(Module module);

    /**
     * Indicate if the primary module is collection based.
     *
     * @return true if the primary module is collection based else false.
     */
    boolean hasCollectionModule();

    void load();

    void close();
}