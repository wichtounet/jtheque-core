package org.jtheque.core.managers.module.loaders;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.utils.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * A module Loader. This class is responsible for the loading of all the modules of the application.
 *
 * @author Baptiste Wicht
 */
public final class SystemModuleLoader implements IModuleLoader {
    private static final IModuleLoader INSTANCE = new SystemModuleLoader();

    /**
     * Construct a new SystemModuleLoader. This constructor is private, all methods are static.
     */
    private SystemModuleLoader() {
        super();
    }

    /**
     * Return the loader.
     *
     * @return The loader.
     */
    public static IModuleLoader get() {
        return INSTANCE;
    }

    @Override
    public Object installModule(File file) {
        if (isValidModuleFile(file)) {
            File modulesFolder = Managers.getCore().getFolders().getModulesFolder();

            boolean inDirectory = FileUtils.putFileInDirectoryIfNot(file, modulesFolder);

            if (inDirectory) {
                return loadModule(file);
            }
        }

        return null;
    }

    /**
     * Indicate if the file contain a valid module.
     *
     * @param file The file to validate.
     * @return true if the file is valid else false.
     */
    private boolean isValidModuleFile(File file) {
        boolean valid = true;

        if (file.isFile()) {
            JarFile jarFile = null;
            try {
                jarFile = new JarFile(file);

                Manifest manifest = jarFile.getManifest();

                if (manifest == null) {
                    valid = false;
                } else {
                    String moduleClassName = manifest.getMainAttributes().getValue("ModuleContainer-Class");

                    if (moduleClassName == null) {
                        valid = false;
                    }
                }
            } catch (IOException e) {
                valid = false;
            } finally {
                if (jarFile != null) {
                    try {
                        jarFile.close();
                    } catch (IOException e) {
                        Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
                    }
                }
            }
        } else {
            valid = false;
        }

        return valid;
    }

    @Override
    public Object loadModule(File file) {
        Object module = null;

        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);

            Manifest manifest = jarFile.getManifest();

            if (manifest != null) {
                String moduleClassName = manifest.getMainAttributes().getValue("Module-Context");

                if (moduleClassName != null) {
                    ModuleLoader.addResource(file.toURI().toURL());

                    module = loadModuleClass(moduleClassName);
                }
            }
        } catch (IOException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
                }
            }
        }

        return module;
    }

    /**
     * Load a module class.
     *
     * @param moduleClassName The class name of the module.
     * @return The loaded module.
     */
    private Object loadModuleClass(String moduleClassName) {
        Object module = null;

        try {
            Class<?> moduleClass = Class.forName(moduleClassName, true, ClassLoader.getSystemClassLoader());

            module = moduleClass.newInstance();
        } catch (ClassNotFoundException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        } catch (InstantiationException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        } catch (IllegalAccessException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }

        return module;
    }
}