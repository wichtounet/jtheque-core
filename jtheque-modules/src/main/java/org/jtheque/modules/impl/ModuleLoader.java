package org.jtheque.modules.impl;

import org.jtheque.core.ICore;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.errors.IErrorService;
import org.jtheque.errors.JThequeError;
import org.jtheque.modules.able.IModuleLoader;
import org.jtheque.modules.able.Module;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.List;
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
public final class ModuleLoader implements IModuleLoader, BundleContextAware {
    private static final Collection<JThequeError> ERRORS = new ArrayList<JThequeError>(10);
    private static final Collection<Module> MODULES = new ArrayList<Module>(10);
    private static final Pattern COMMA_DELIMITER_PATTERN = Pattern.compile(";");

    private BundleContext bundleContext;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public List<ModuleContainer> loadModules() {
        List<ModuleContainer> modules = new ArrayList<ModuleContainer>(10);

        File moduleDir = OSGiUtils.getService(bundleContext, ICore.class).getFolders().getModulesFolder();

        File[] files = moduleDir.listFiles(new ModuleFilter());

        if (files != null) {
            for (File file : files) {
                loadModule(file, modules);
            }
        }

        return modules;
    }

    /**
     * Load a module from the file.
     *
     * @param file The Jar File of a module.
     * @param modules The modules to add the new modules to.
     */
    private void loadModule(File file, Collection<ModuleContainer> modules) {
        try {
            Bundle bundle = bundleContext.installBundle(file.getAbsolutePath());

            ModuleContainer container = new ModuleContainer(bundle);

            Dictionary<String, String> headers = bundle.getHeaders();

            String id = StringUtils.isNotEmpty(headers.get("Module-Id")) ? headers.get("Module-Id") : headers.get("Bundle-SymbolicName");

            container.setId(id);

            String version = StringUtils.isNotEmpty(headers.get("Module-Version")) ? headers.get("Module-Version") : headers.get("Bundle-Version");

            container.setVersion(new Version(version));

            container.setI18n(headers.get("Module-I18n"));

            if(StringUtils.isNotEmpty(headers.get("Module-Core"))){
                container.setCoreVersion(new Version(headers.get("Module-Core")));
            }

            container.setUrl(headers.get("Module-Url"));
            container.setUpdateUrl(headers.get("Module-UpdateUrl"));
            container.setMessagesUrl(headers.get("Module-MessagesUrl"));

            if(StringUtils.isNotEmpty(headers.get("Module-Collection"))){
                container.setCollection(Boolean.parseBoolean(headers.get("Module-Collection")));
            }

            container.setBundles(COMMA_DELIMITER_PATTERN.split(headers.get("Module-Bundles")));
            container.setDependencies(COMMA_DELIMITER_PATTERN.split(headers.get("Module-Dependencies")));

            modules.add(container);
        } catch (BundleException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            OSGiUtils.getService(bundleContext, IErrorService.class).addError(new JThequeError(e));
        }
    }

    @Override
    public ModuleContainer installModule(File file) {
        return null;  //Todo install a file
    }
}