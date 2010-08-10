package org.jtheque.modules.impl;

import org.jtheque.core.able.Core;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.errors.able.ErrorService;
import org.jtheque.errors.able.Errors;
import org.jtheque.i18n.able.I18NResourceFactory;
import org.jtheque.i18n.able.LanguageService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.resources.able.Resource;
import org.jtheque.resources.able.ResourceService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ThreadUtils;
import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.XML;
import org.jtheque.xml.utils.XMLException;
import org.jtheque.xml.utils.XMLOverReader;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.slf4j.LoggerFactory;
import org.springframework.osgi.context.BundleContextAware;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
public final class ModuleLoader implements BundleContextAware {
    private static final Pattern COMMA_DELIMITER_PATTERN = Pattern.compile(";");
    private static final String[] EMPTY_ARRAY = new String[0];

    private BundleContext bundleContext;

    @javax.annotation.Resource
    private ResourceService resourceService;

    @javax.annotation.Resource
    private LanguageService languageService;

    @javax.annotation.Resource
    private Core core;

    private final ModuleServiceImpl moduleService;

    public ModuleLoader(ModuleServiceImpl moduleService) {
        super();

        this.moduleService = moduleService;
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    /**
     * Load the modules.
     *
     * @return All the loaded modules.
     */
    public List<Module> loadModules() {
        File moduleDir = core.getFolders().getModulesFolder();

        File[] files = moduleDir.listFiles();

        ExecutorService loadersPool = Executors.newFixedThreadPool(2 * ThreadUtils.processors());

        CompletionService<Module> completionService = new ExecutorCompletionService<Module>(loadersPool);

        for (File file : files) {
            completionService.submit(new ModuleLoaderTask(file));
        }

        List<Module> modules = CollectionUtils.newList(files.length);

        try {
            for (int i = 0; i < files.length; i++) {
                modules.add(completionService.take().get());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        loadersPool.shutdown();

        return modules;
    }

    /**
     * Install the module.
     *
     * @param file The file to the module to install.
     *
     * @return The installed module.
     */
    public Module installModule(File file) {
        Builder builder = new Builder();

        ModuleResources resources = null;

        try {
            //Read the config file of the module
            resources = readConfig(file);

            //Install the bundle
            Bundle bundle = bundleContext.installBundle("file:" + file.getAbsolutePath());
            builder.setBundle(bundle);

            //Get informations from manifest
            readManifestInformations(builder, bundle);
        } catch (BundleException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            OSGiUtils.getService(bundleContext, ErrorService.class).addError(Errors.newError(e));
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            OSGiUtils.getService(bundleContext, ErrorService.class).addError(Errors.newError(e));
        }

        Module module = builder.build();

        //Load i18n resources
        loadI18NResources(module);

        if(resources == null){
            moduleService.setResources(module, new ModuleResources());
        } else {
            moduleService.setResources(module, resources);
        }
        
        return module;
    }

    /**
     * Read the config of the module.
     *
     * @param file   The file of the module.
     * @throws IOException If an error occurs during Jar File reading.
     */
    private ModuleResources readConfig(File file) throws IOException {
        JarFile jarFile = new JarFile(file);
        ZipEntry configEntry = jarFile.getEntry("module.xml");

        ModuleResources resources = null;

        if (configEntry != null) {
            resources = importConfig(jarFile.getInputStream(configEntry));

            //Install necessary resources before installing the bundle
            for (Resource resource : resources.getResources()) {
                if (resource != null) {
                    resourceService.installResource(resource);
                }
            }
        }

        jarFile.close();

        return resources;
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

        XMLOverReader reader = XML.newJavaFactory().newOverReader();
        try {
            reader.openStream(stream);

            importI18NResources(resources, reader);
            importImageResources(resources, reader);
            importResources(resources, reader);
        } catch (XMLException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            OSGiUtils.getService(bundleContext, ErrorService.class).addError(Errors.newError(e));
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
        container.setVersion(Version.get(headers.get("Bundle-Version")));

        if (StringUtils.isNotEmpty(headers.get("Module-Core"))) {
            container.setCoreVersion(Version.get(headers.get("Module-Core")));
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
        for (I18NResource i18NResource : moduleService.getResources(module).getI18NResources()) {
            List<org.jtheque.i18n.able.I18NResource> i18NResources = CollectionUtils.newList(i18NResource.getResources().size());

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
    private static void importI18NResources(ModuleResources resources, XMLOverReader reader) throws XMLException {
        while (reader.next("/config/i18n/i18nResource")) {
            String name = reader.readString("@name");
            Version version = Version.get(reader.readString("@version"));

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
    private static void importImageResources(ModuleResources resources, XMLOverReader reader) throws XMLException {
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
    private void importResources(ModuleResources resources, XMLOverReader reader) throws XMLException {
        while (reader.next("/config/resources/resource")) {
            String id = reader.readString("@id");
            Version version = Version.get(reader.readString("@version"));
            String url = reader.readString("@url");

            resources.addResource(resourceService.getOrDownloadResource(id, version, url));
        }
    }

    /**
     * Uninstall the given module.
     *
     * @param module The module to uninstall.
     */
    public void uninstallModule(Module module) {
        ModuleResources resources = moduleService.getResources(module);

        if (resources != null) {
            for (I18NResource i18NResource : resources.getI18NResources()) {
                languageService.releaseResource(i18NResource.getName());
            }
        }
    }

    /**
     * A simple task to load a module from a file.
     *
     * @author Baptiste Wicht
     */
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

    /**
     * A Builder for the ModuleContainer instance.
     *
     * @author Baptiste Wicht
     */
    private final class Builder {
        private String id;
        private Bundle bundle;
        private Version version;
        private Version coreVersion;
        private String[] dependencies;
        private String url;
        private String updateUrl;
        private String messagesUrl;
        private boolean collection;

        /**
         * Set the id of the module.
         *
         * @param id The id of the module.
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Set the version of the module.
         *
         * @param version The version of the module.
         */
        public void setVersion(Version version) {
            this.version = version;
        }

        /**
         * Set the core version needed by the module.
         *
         * @param coreVersion The core version needed by the module.
         */
        public void setCoreVersion(Version coreVersion) {
            this.coreVersion = coreVersion;
        }

        /**
         * Set the bundle of the module.
         *
         * @param bundle The bundle.
         */
        public void setBundle(Bundle bundle) {
            this.bundle = bundle;
        }

        /**
         * Set the URL of the site of the module.
         *
         * @param url THe URL of the site of the module.
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * Set the the URL to the update file of the module.
         *
         * @param updateUrl The URL to the update file of the module.
         */
        public void setUpdateUrl(String updateUrl) {
            this.updateUrl = updateUrl;
        }

        /**
         * Set the dependencies of the module.
         *
         * @param dependencies The dependencies of the module.
         */
        public void setDependencies(String[] dependencies) {
            this.dependencies = ArrayUtils.copyOf(dependencies);
        }

        /**
         * Set the messages URL.
         *
         * @param messagesUrl The messages URL of the module.
         */
        public void setMessagesUrl(String messagesUrl) {
            this.messagesUrl = messagesUrl;
        }

        /**
         * Set the boolean tag indicating if the module is collection-based or not.
         *
         * @param collection boolean tag indicating if the module is collection-based (true) or not (false).
         */
        public void setCollection(boolean collection) {
            this.collection = collection;
        }

        /**
         * Build the module.
         *
         * @return The module to build.
         */
        public Module build() {
            return new ModuleContainer(this);
        }
    }

    /**
     * A module implementation.
     *
     * @author Baptiste Wicht
     */
    @Immutable
    private final class ModuleContainer implements Module {
        private final String id;
        private final Bundle bundle;
        private final Version version;
        private final Version coreVersion;
        private final String[] dependencies;
        private final String url;
        private final String updateUrl;
        private final String messagesUrl;
        private final boolean collection;

        private ModuleState state;

        /**
         * Create a module container using the given builder informations.
         *
         * @param builder The builder to get the informations from.
         */
        private ModuleContainer(Builder builder) {
            super();

            id = builder.id;
            version = builder.version;
            coreVersion = builder.coreVersion;
            bundle = builder.bundle;
            dependencies = builder.dependencies;
            url = builder.url;
            updateUrl = builder.updateUrl;
            messagesUrl = builder.messagesUrl;
            collection = builder.collection;
        }

        @Override
        public Bundle getBundle() {
            return bundle;
        }

        @Override
        public ModuleState getState() {
            return state;
        }

        @Override
        public void setState(ModuleState state) {
            this.state = state;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return internationalize(id + ".name");
        }

        @Override
        public String getAuthor() {
            return internationalize(id + ".author");
        }

        @Override
        public String getDescription() {
            return internationalize(id + ".description");
        }

        @Override
        public String getDisplayState() {
            return internationalize(state.getKey());
        }

        /**
         * Internationalize the given key.
         *
         * @param key The i18n key.
         *
         * @return The internationalized message.
         */
        private String internationalize(String key) {
            return languageService.getMessage(key);
        }

        @Override
        public Version getVersion() {
            return version;
        }

        @Override
        public Version getCoreVersion() {
            return coreVersion;
        }

        @Override
        public String getUrl() {
            return url;
        }

        @Override
        public String getDescriptorURL() {
            return updateUrl;
        }

        @Override
        public String[] getDependencies() {
            return ArrayUtils.copyOf(dependencies);
        }

        @Override
        public String getMessagesUrl() {
            return messagesUrl;
        }

        @Override
        public String toString() {
            return getName();
        }

        @Override
        public boolean isCollection() {
            return collection;
        }
    }
}