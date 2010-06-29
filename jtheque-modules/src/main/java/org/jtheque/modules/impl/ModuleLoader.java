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
import org.jtheque.modules.able.Resources;
import org.jtheque.modules.utils.I18NDescription;
import org.jtheque.modules.utils.ImageDescription;
import org.jtheque.resources.able.IResource;
import org.jtheque.resources.able.IResourceService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.xml.utils.IXMLOverReader;
import org.jtheque.xml.utils.XML;
import org.jtheque.xml.utils.XMLException;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

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
    private IResourceService resourceService;

    @Resource
    private ILanguageService languageService;

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
        ModuleContainer container = new ModuleContainer();

        try {
            //Read the config file of the module
            readConfig(file, container);

            //Install necessary resources before installing the bundle
            for (IResource resource : container.getResources().getResources()) {
                if (resource != null) {
                    resourceService.installResource(resource);
                }
            }

            //Install the bundle
            Bundle bundle = bundleContext.installBundle("file:" + file.getAbsolutePath());
            container.setBundle(bundle);

            container.setLanguageService(OSGiUtils.getService(bundleContext, ILanguageService.class));

            //Get informations from manifest
            readManifestInformations(container, bundle);

            //Add i18n resources
            loadI18NResources(container);
        } catch (BundleException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            OSGiUtils.getService(bundleContext, IErrorService.class).addError(new JThequeError(e));
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            OSGiUtils.getService(bundleContext, IErrorService.class).addError(new JThequeError(e));
        }

        return container;
    }

    @Override
    public void uninstallModule(Module module) {
        Resources resources = module.getResources();

        if (resources != null) {
            for (I18NDescription i18NDescription : resources.getI18NResources()) {
                languageService.releaseResource(i18NDescription.getName());
            }
        }
    }

    private static void readManifestInformations(ModuleContainer container, Bundle bundle) {
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

        if (StringUtils.isNotEmpty(headers.get("Module-Dependencies"))) {
            container.setDependencies(COMMA_DELIMITER_PATTERN.split(headers.get("Module-Dependencies")));
        } else {
            container.setDependencies(EMPTY_ARRAY);
        }
    }

    private void loadI18NResources(Module container) {
        for (I18NDescription i18NDescription : container.getResources().getI18NResources()) {
            List<I18NResource> i18NResources = new ArrayList<I18NResource>(i18NDescription.getResources().size());

            for (String resource : i18NDescription.getResources()) {
                if (resource.startsWith("classpath:")) {
                    i18NResources.add(I18NResourceFactory.fromURL(resource.substring(resource.lastIndexOf('/') + 1),
                            container.getBundle().getResource(resource.substring(10))));
                }
            }

            languageService.registerResource(i18NDescription.getName(), i18NDescription.getVersion(),
                    i18NResources.toArray(new I18NResource[i18NResources.size()]));
        }
    }

    private void readConfig(File file, ModuleContainer container) throws IOException {
        JarFile jarFile = new JarFile(file);
        ZipEntry configEntry = jarFile.getEntry("module.xml");

        if (configEntry != null) {
            container.setResources(importConfig(jarFile.getInputStream(configEntry)));
        } else {
            container.setResources(new ModuleResources());
        }

        jarFile.close();
    }

    /**
     * Import the configuration of the module from the module config XML file.
     *
     * @param stream The stream to the file.
     *
     * @return The ModuleResources of the module.
     */
    private ModuleResources importConfig(InputStream stream) {
        ModuleResources resources = new ModuleResources();

        IXMLOverReader reader = XML.newJavaFactory().newOverReader();
        try {
            reader.openStream(stream);

            importI18NResources(resources, reader);
            importImageResources(resources, reader);
            importResources(resources, reader);
        } catch (XMLException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            OSGiUtils.getService(bundleContext, IErrorService.class).addError(new JThequeError(e));
        }

        return resources;
    }

    private static void importI18NResources(ModuleResources resources, IXMLOverReader reader) throws XMLException {
        while (reader.next("/config/i18n/i18nResource")) {
            String name = reader.readString("@name");
            Version version = new Version(reader.readString("@version"));

            I18NDescription description = new I18NDescription(name, version);

            while (reader.next("classpath")) {
                String classpath = reader.readString("text()");

                description.getResources().add("classpath:" + classpath);
            }

            resources.addI18NResource(description);
        }
    }

    private static void importImageResources(ModuleResources resources, IXMLOverReader reader) throws XMLException {
        while (reader.next("/config/images/resource")) {
            String name = reader.readString("@name");
            String classpath = reader.readString("classpath");

            resources.addImageResource(new ImageDescription(name, "classpath:" + classpath));
        }
    }

    private void importResources(ModuleResources resources, IXMLOverReader reader) throws XMLException {
        while (reader.next("/config/resources/resource")) {
            String id = reader.readString("@id");
            Version version = new Version(reader.readString("@version"));
            String url = reader.readString("@url");

            IResource resource = resourceService.getResource(id, version);

            if (resource != null) {
                resources.addResource(resource);
            } else {
                resource = resourceService.downloadResource(url, version);

                resources.addResource(resource);
            }
        }
    }
}