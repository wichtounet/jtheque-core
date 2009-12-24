package org.jtheque.core.managers.module.loaders;

import org.apache.commons.logging.LogFactory;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.ioc.Ioc;
import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.utils.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Pattern;

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

/**
 * A loader for the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleLoader {
    private static final Collection<JThequeError> ERRORS = new ArrayList<JThequeError>(10);
    private static final Collection<ModuleContainer> MODULES = new ArrayList<ModuleContainer>(10);
    private static final Pattern COMMA_DELIMITER_PATTERN = Pattern.compile(";");

    /**
     * This is an utility class, not instanciable.
     */
    private ModuleLoader() {
        super();
    }

    /**
     * Load the modules.
     */
    public static void loadModules() {
        FileUtils.clearFolder(Managers.getCore().getFolders().getModulesFolder());

        File moduleDir = Managers.getCore().getFolders().getModulesFolder();

        File[] files = moduleDir.listFiles(new ModuleFilter());

        if (files != null) {
            for (File file : files) {
                loadModule(file);
            }
        }
    }

    /**
     * Load a module from the file.
     *
     * @param file The Jar File of a module.
     */
    private static void loadModule(File file) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);

            Manifest manifest = jarFile.getManifest();

            if (isValidManifest(manifest, file)) {
                loadModule(file, manifest);
            }
        } catch (IOException e) {
            ERRORS.add(new InternationalizedError("error.module.ioexception", new Object[]{file.getName()}));
        } finally {
            FileUtils.close(jarFile);
        }
    }

    /**
     * Test if the manifest of the file is valid.
     *
     * @param manifest The
     * @param file     The file of the manifest.
     * @return true if the manifest is valid else false.
     */
    private static boolean isValidManifest(Manifest manifest, File file) {
        if (manifest == null) {
            ERRORS.add(new InternationalizedError("error.module.nomanifest", new Object[]{file.getName()}));

            return false;
        }

        return true;
    }

    /**
     * Load the module from the file.
     *
     * @param file     The module jar file.
     * @param manifest The module manifest.
     * @throws MalformedURLException Throw if the URLs to the resources are malformed.
     */
    private static void loadModule(File file, Manifest manifest) throws MalformedURLException {
        String moduleContext = manifest.getMainAttributes().getValue("Module-Context");

        if (moduleContext == null) {
            ERRORS.add(new InternationalizedError("error.module.nomodulecontext", new Object[]{file.getName()}));
        } else {
            Ioc.getContainer().addBeansFile(moduleContext);

            addResource(file.toURI().toURL());

            loadResources(file, manifest);
        }
    }

    /**
     * Load all the resources of a module file.
     *
     * @param file     The file.
     * @param manifest The manifest of the file.
     * @throws MalformedURLException Throws if an error occurs during the loading of the URL.
     */
    private static void loadResources(File file, Manifest manifest) throws MalformedURLException {
        String resources = manifest.getMainAttributes().getValue("Resources");

        if (resources != null) {
            String[] libs = COMMA_DELIMITER_PATTERN.split(resources.trim());

            for (String lib : libs) {
                File libFile = new File(Managers.getCore().getFolders().getLibrariesFolder(), lib);

                if (libFile.exists()) {
                    addResource(libFile.toURI().toURL());
                } else {
                    ERRORS.add(new InternationalizedError("error.module.libnotexisting", file.getName(), lib));
                }
            }
        }
    }

    /**
     * Add a resource.
     *
     * @param url The URL of the resource.
     */
    static void addResource(URL url) {
        try {
            Class<URLClassLoader> classLoaderClass = URLClassLoader.class;
            final Method method = classLoaderClass.getDeclaredMethod("addURL", new Class[]{URL.class});

            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    method.setAccessible(true);

                    return null;
                }
            });

            method.invoke(ClassLoader.getSystemClassLoader(), url);
        } catch (Exception t) {
            LogFactory.getLog(ModuleLoader.class).error(t);
        }

        LogFactory.getLog(ModuleLoader.class).debug("Add resource to ClassLoader. URL : " + url);
    }

    /**
     * Return the errors of the loading.
     *
     * @return A collection containing all the errors occurred during the loading process.
     */
    public static Iterable<JThequeError> getErrors() {
        return ERRORS;
    }

    /**
     * Return all the modules.
     *
     * @return A collection containing all the loaded modules.
     */
    public static Collection<ModuleContainer> getModules() {
        return MODULES;
    }
}