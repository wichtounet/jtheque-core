package org.jtheque.modules.impl;

import org.jtheque.core.able.ICore;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.errors.utils.JThequeError;
import org.jtheque.modules.able.IModuleLoader;
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
 * A loader for the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleLoader implements IModuleLoader, BundleContextAware {
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
            Bundle bundle = bundleContext.installBundle("file:" + file.getAbsolutePath());

            ModuleContainer container = new ModuleContainer(bundle);

            Dictionary<String, String> headers = bundle.getHeaders();

            String id = StringUtils.isNotEmpty(headers.get("Module-Id")) ? headers.get("Module-Id") : headers.get("Bundle-SymbolicName");

            container.setId(id);

            String version = StringUtils.isNotEmpty(headers.get("Module-Version")) ? headers.get("Module-Version") : headers.get("Bundle-Version");

            container.setVersion(new Version(version));

            if(StringUtils.isNotEmpty(headers.get("Module-Core"))){
                container.setCoreVersion(new Version(headers.get("Module-Core")));
            }

            container.setUrl(headers.get("Module-Url"));
            container.setUpdateUrl(headers.get("Module-UpdateUrl"));
            container.setMessagesUrl(headers.get("Module-MessagesUrl"));

            if(StringUtils.isNotEmpty(headers.get("Module-Collection"))){
                container.setCollection(Boolean.parseBoolean(headers.get("Module-Collection")));
            }

            if(StringUtils.isNotEmpty(headers.get("Module-Bundles"))){
                container.setBundles(COMMA_DELIMITER_PATTERN.split(headers.get("Module-Bundles")));
            }

            if(StringUtils.isNotEmpty(headers.get("Module-Dependencies"))){
                container.setDependencies(COMMA_DELIMITER_PATTERN.split(headers.get("Module-Dependencies")));
            }

	        if(StringUtils.isNotEmpty(headers.get("Module-Config"))){
		        String path = headers.get("Module-Config");

		        
	        }

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