package org.jtheque.modules.impl;

import org.jtheque.core.able.ICore;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.errors.utils.JThequeError;
import org.jtheque.i18n.able.I18NResource;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.i18n.utils.I18NResourceFactory;
import org.jtheque.modules.able.IModuleLoader;
import org.jtheque.modules.able.Module;
import org.jtheque.resources.able.IResourceService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.xml.utils.XMLException;
import org.jtheque.xml.utils.XMLOverReader;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.osgi.context.BundleContextAware;

import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
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
    private static final String[] EMPTY_ARRAY = new String[0];

    private BundleContext bundleContext;

    @Resource
    private ILanguageService languageService;

    @Resource
    private IResourceService resourceService;


    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public List<Module> loadModules() {
        List<Module> modules = new ArrayList<Module>(10);

        File moduleDir = OSGiUtils.getService(bundleContext, ICore.class).getFolders().getModulesFolder();

        File[] files = moduleDir.listFiles(new ModuleFilter());

        if (files != null) {
            for (File file : files) {
                modules.add(installModule(file));
            }
        }

        return modules;
    }

    @Override
    public Module installModule(File file) {
        ModuleContainer container = null;

        try {
            Bundle bundle = bundleContext.installBundle("file:" + file.getAbsolutePath());

            container = new ModuleContainer(bundle);

            Dictionary<String, String> headers = bundle.getHeaders();

            String id = StringUtils.isNotEmpty(headers.get("Module-Id")) ? headers.get("Module-Id") : headers.get("Bundle-SymbolicName");

            container.setId(id);

            String version = StringUtils.isNotEmpty(headers.get("Module-Version")) ? headers.get("Module-Version") : headers.get("Bundle-Version");

            container.setVersion(new Version(version));

            if (StringUtils.isNotEmpty(headers.get("Module-Core"))) {
                container.setCoreVersion(new Version(headers.get("Module-Core")));
            }

            container.setUrl(headers.get("Module-Url"));
            container.setUpdateUrl(headers.get("Module-UpdateUrl"));
            container.setMessagesUrl(headers.get("Module-MessagesUrl"));

            if (StringUtils.isNotEmpty(headers.get("Module-Collection"))) {
                container.setCollection(Boolean.parseBoolean(headers.get("Module-Collection")));
            }

            if (StringUtils.isNotEmpty(headers.get("Module-Libs"))) {
                container.setLibs(COMMA_DELIMITER_PATTERN.split(headers.get("Module-Libs")));

                File libsFolder = OSGiUtils.getService(bundleContext, ICore.class).getFolders().getLibrariesFolder();

                for (String lib : container.getLibs()) {
                    File libFile = new File(libsFolder, lib);

                    LoggerFactory.getLogger(getClass()).debug("Install bundle dependency {}", libFile.getAbsolutePath());
                    
                    bundleContext.installBundle("file:" + libFile.getAbsolutePath());
                }
            } else {
                container.setLibs(EMPTY_ARRAY);
            }

            if (StringUtils.isNotEmpty(headers.get("Module-Dependencies"))) {
                container.setDependencies(COMMA_DELIMITER_PATTERN.split(headers.get("Module-Dependencies")));
            } else {
                container.setDependencies(EMPTY_ARRAY);
            }

            String path = headers.get("Module-Config");

            if (StringUtils.isNotEmpty(path)) {
                container.setResources(importConfig(bundle, path));
            }
        } catch (BundleException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            OSGiUtils.getService(bundleContext, IErrorService.class).addError(new JThequeError(e));
        }

        return container;
    }

    /**
     * Import the configuration of the module from the module config XML file.
     *
     * @param bundle The bundle.
     * @param path   The path to the file inside the bundle.
     * @return The ModuleResources of the module.
     */
    private ModuleResources importConfig(Bundle bundle, String path) {
        ModuleResources resources = new ModuleResources();

        XMLOverReader reader = new XMLOverReader();
        try {
            reader.openURL(bundle.getResource(path));

            while (reader.next("/config/i18n/i18nResource")) {
                String name = reader.readString("@name");
                Version version = new Version(reader.readString("@version"));

                List<I18NResource> i18NResources = new ArrayList<I18NResource>(3);

                while (reader.next("classpath")) {
                    String classpath = reader.readString("text()");

                    i18NResources.add(I18NResourceFactory.fromURL(classpath.substring(classpath.lastIndexOf('/') + 1), bundle.getResource(classpath)));

                    reader.switchToParent();
                }

                languageService.registerResource(name, version, i18NResources.toArray(new I18NResource[i18NResources.size()]));

                resources.addI18NResource(name);

                reader.switchToParent();
            }

            while (reader.next("/config/resources/resource")) {
                String name = reader.readString("@name");
                String classpath = reader.readString("classpath");

                resourceService.registerResource(name, new UrlResource(bundle.getResource(classpath)));

                resources.addResource(name);
            }
        } catch (XMLException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            OSGiUtils.getService(bundleContext, IErrorService.class).addError(new JThequeError(e));
        }

        return resources;
	}
}