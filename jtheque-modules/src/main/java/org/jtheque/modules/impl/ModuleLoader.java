package org.jtheque.modules.impl;

import org.jtheque.core.able.ICore;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.errors.utils.Errors;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.i18n.utils.I18NResourceFactory;
import org.jtheque.modules.able.IModuleLoader;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.Resources;
import org.jtheque.modules.impl.ModuleContainer.Builder;
import org.jtheque.modules.utils.I18NResource;
import org.jtheque.modules.utils.ImageResource;
import org.jtheque.resources.able.IResource;
import org.jtheque.resources.able.IResourceService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ThreadUtils;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.io.FileUtils;
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
import java.util.Collection;
import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

    @Resource
    private ICore core;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    public List<Module> loadModules() {
        File moduleDir = core.getFolders().getModulesFolder();

        File[] files = moduleDir.listFiles();

        Collection<Future<Module>> futures = new ArrayList<Future<Module>>(files.length);

        ExecutorService loadersPool = Executors.newFixedThreadPool(2 * ThreadUtils.processors());

        for (File file : files) {
            futures.add(loadersPool.submit(new ModuleLoaderTask(file)));
        }

        List<Module> modules = new ArrayList<Module>(files.length);

        try {
            for (Future<Module> future : futures) {
                modules.add(future.get());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        loadersPool.shutdown();

        return modules;
    }

    @Override
    public Module installModule(File file) {
        Builder builder = new Builder();

        try {
            //Read the config file of the module
            readConfig(file, builder);

            //Install the bundle
            Bundle bundle = bundleContext.installBundle("file:" + file.getAbsolutePath());
            builder.setBundle(bundle);

            builder.setLanguageService(languageService);

            //Get informations from manifest
            readManifestInformations(builder, bundle);
        } catch (BundleException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            OSGiUtils.getService(bundleContext, IErrorService.class).addError(Errors.newError(e));
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            OSGiUtils.getService(bundleContext, IErrorService.class).addError(Errors.newError(e));
        }

        Module module = builder.build();

        //Add i18n resources
        loadI18NResources(module);

        return module;
    }

    /**
     * Read the config of the module.
     *
     * @param file   The file of the module.
     * @param module The module
     *
     * @throws IOException If an error occurs during Jar File reading.
     */
    private void readConfig(File file, Builder module) throws IOException {
        JarFile jarFile = new JarFile(file);
        ZipEntry configEntry = jarFile.getEntry("module.xml");

        if (configEntry != null) {
            ModuleResources resources = importConfig(jarFile.getInputStream(configEntry));

            //Install necessary resources before installing the bundle
            for (IResource resource : resources.getResources()) {
                if (resource != null) {
                    resourceService.installResource(resource);
                }
            }

            module.setResources(resources);
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
            OSGiUtils.getService(bundleContext, IErrorService.class).addError(Errors.newError(e));
        } finally {
            FileUtils.close(reader);
        }

        return resources;
    }

    /**
     * Read the manifest informations of the given module.
     *
     * @param container The module.
     * @param bundle    The bundle.
     */
    private static void readManifestInformations(Builder container, Bundle bundle) {
        @SuppressWarnings("unchecked") //We kwnow that the bundle headers are a String<->String Map
                Dictionary<String, String> headers = bundle.getHeaders();

        container.setId(headers.get("Bundle-SymbolicName"));
        container.setVersion(new Version(headers.get("Bundle-Version")));

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

    /**
     * Load the i18n resources of the given module.
     *
     * @param module The module to load i18n resources for.
     */
    private void loadI18NResources(Module module) {
        for (I18NResource i18NResource : module.getResources().getI18NResources()) {
            List<org.jtheque.i18n.able.I18NResource> i18NResources = new ArrayList<org.jtheque.i18n.able.I18NResource>(i18NResource.getResources().size());

            for (String resource : i18NResource.getResources()) {
                if (resource.startsWith("classpath:")) {
                    i18NResources.add(I18NResourceFactory.fromURL(resource.substring(resource.lastIndexOf('/') + 1),
                            module.getBundle().getResource(resource.substring(10))));
                }
            }

            languageService.registerResource(i18NResource.getName(), i18NResource.getVersion(),
                    i18NResources.toArray(new org.jtheque.i18n.able.I18NResource[i18NResources.size()]));
        }
    }

    /**
     * Import the i18n resources.
     *
     * @param resources The resources of the module.
     * @param reader    The XML reader.
     *
     * @throws XMLException If an error occurs during XML parsing.
     */
    private static void importI18NResources(ModuleResources resources, IXMLOverReader reader) throws XMLException {
        while (reader.next("/config/i18n/i18nResource")) {
            String name = reader.readString("@name");
            Version version = new Version(reader.readString("@version"));

            I18NResource i18NResource = new I18NResource(name, version);

            while (reader.next("classpath")) {
                String classpath = reader.readString("text()");

                i18NResource.addResource("classpath:" + classpath);
            }

            resources.addI18NResource(i18NResource);
        }
    }

    /**
     * Import the image resources.
     *
     * @param resources The resources to fill.
     * @param reader    The XML reader.
     *
     * @throws XMLException If an exception occurs during XML parsing.
     */
    private static void importImageResources(ModuleResources resources, IXMLOverReader reader) throws XMLException {
        while (reader.next("/config/images/resource")) {
            String name = reader.readString("@name");
            String classpath = reader.readString("classpath");

            resources.addImageResource(new ImageResource(name, "classpath:" + classpath));
        }
    }

    /**
     * Import the resources.
     *
     * @param resources The resources to fill.
     * @param reader    The XML reader.
     *
     * @throws XMLException If an exception occurs during XML parsing.
     */
    private void importResources(ModuleResources resources, IXMLOverReader reader) throws XMLException {
        while (reader.next("/config/resources/resource")) {
            String id = reader.readString("@id");
            Version version = new Version(reader.readString("@version"));
            String url = reader.readString("@url");

            resources.addResource(resourceService.getOrDownloadResource(id, version, url));
        }
    }

    @Override
    public void uninstallModule(Module module) {
        Resources resources = module.getResources();

        if (resources != null) {
            for (I18NResource i18NResource : resources.getI18NResources()) {
                languageService.releaseResource(i18NResource.getName());
            }
        }
    }

    private final class ModuleLoaderTask implements Callable<Module> {
        private final File file;

        /**
         * Construct a new ModuleLoader task for the given file.
         *
         * @param file The file to install.
         */
        private ModuleLoaderTask(File file) {
            this.file = file;
        }

        @Override
        public Module call() {
            return installModule(file);
        }
    }
}