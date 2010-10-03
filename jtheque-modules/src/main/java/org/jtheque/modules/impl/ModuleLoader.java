package org.jtheque.modules.impl;

import org.jtheque.core.Core;
import org.jtheque.core.impl.Folders;
import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.errors.ErrorService;
import org.jtheque.errors.Errors;
import org.jtheque.i18n.I18NResourceFactory;
import org.jtheque.i18n.LanguageService;
import org.jtheque.modules.Module;
import org.jtheque.modules.ModuleException;
import org.jtheque.modules.ModuleException.ModuleOperation;
import org.jtheque.modules.ModuleState;
import org.jtheque.resources.Resource;
import org.jtheque.resources.ResourceService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.ThreadUtils;
import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.annotations.NotThreadSafe;
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
import org.springframework.osgi.context.BundleContextAware;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Dictionary;
import java.util.List;
import java.util.Locale;
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
@NotThreadSafe
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

    /**
     * Construct a new ModuleLoader.
     *
     * @param moduleService The module service.
     */
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
    public Collection<Module> loadModules() {
        File[] files = Folders.getModulesFolder().listFiles(new ModuleFilter());

        return isLoadingConcurrent() ? loadInParallel(files) : loadSequentially(files);
    }

    /**
     * Indicate if we must make the loading concurrent.
     *
     * @return {@code true} if the loading is concurrent otherwise {@code false}.
     */
    private static boolean isLoadingConcurrent() {
        String property = System.getProperty("jtheque.concurrent.load");

        return StringUtils.isNotEmpty(property) && "true".equalsIgnoreCase(property);
    }

    /**
     * Load all the modules from the given files in parallel (using one thread per processor).
     *
     * @param files The files to load the modules from.
     *
     * @return A Collection containing all the loaded modules.
     */
    @SuppressWarnings({"ForLoopReplaceableByForEach"})
    private Collection<Module> loadInParallel(File[] files) {
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
     * Load all the modules from the given files sequentially.
     *
     * @param files The files to load the modules from.
     *
     * @return A Collection containing all the loaded modules.
     */
    private Collection<Module> loadSequentially(File[] files) {
        List<Module> modules = CollectionUtils.newList(files.length);

        for (File file : files) {
            try {
                modules.add(installModule(file));
            } catch (ModuleException e) {
                //Do not rethrow the exception to try to install the others module. 
                OSGiUtils.getService(bundleContext, ErrorService.class).addError(Errors.newError(e));
            }
        }

        return modules;
    }

    /**
     * Install the module.
     *
     * @param file The file to the module to installFromRepository.
     *
     * @return The installed module.
     *
     * @throws org.jtheque.modules.ModuleException
     *          If an error occurs during module installation.
     */
    public Module installModule(File file) throws ModuleException {
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
            throw new ModuleException(e, ModuleOperation.INSTALL);
        } catch (IOException e) {
            throw new ModuleException(e, ModuleOperation.INSTALL);
        }

        Module module = builder.build();

        loadI18NResources(module, resources);

        moduleService.setResources(module, resources);
        
        return module;
    }

    /**
     * Read the config of the module.
     *
     * @param file The file of the module.
     *
     * @return The module resources.
     *
     * @throws IOException If an error occurs during Jar File reading.
     * @throws org.jtheque.modules.ModuleException
     *                     If the config cannot be read.
     */
    private ModuleResources readConfig(File file) throws IOException, ModuleException {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);

            ZipEntry configEntry = jarFile.getEntry("module.xml");

            if (configEntry == null) {
                throw new ModuleException("error.module.config", ModuleOperation.LOAD);
            }

            ModuleResources resources = importConfig(jarFile.getInputStream(configEntry));

            //Install necessary resources before installing the bundle
            for (Resource resource : resources.getResources()) {
                if (resource != null) {
                    resourceService.installResource(resource);
                }
            }

            return resources;
        } finally {
            if (jarFile != null) {
                jarFile.close();
            }
        }
    }

    /**
     * Import the configuration of the module from the module config XML file.
     *
     * @param stream The stream to the file.
     *
     * @return The ModuleResources of the module.
     *
     * @throws org.jtheque.modules.ModuleException
     *          If the config cannot be read properly.
     */
    private ModuleResources importConfig(InputStream stream) throws ModuleException {
        XMLOverReader reader = XML.newJavaFactory().newOverReader();

        try {
            reader.openStream(stream);

            return new ModuleResources(
                    importImageResources(reader),
                    importI18NResources(reader),
                    importResources(reader));
        } catch (XMLException e) {
            throw new ModuleException(e, ModuleOperation.LOAD);
        } finally {
            FileUtils.close(reader);
        }
    }

    /**
     * Read the manifest informations of the given module.
     *
     * @param container The module.
     * @param bundle    The bundle.
     */
    private static void readManifestInformations(Builder container, Bundle bundle) {
        @SuppressWarnings("unchecked") //We know that the bundle headers are a String<->String Map
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
     * @param module    The module to load i18n resources for.
     * @param resources The resources of the module.
     */
    private void loadI18NResources(Module module, ModuleResources resources) {
        for (I18NResource i18NResource : resources.getI18NResources()) {
            List<org.jtheque.i18n.I18NResource> i18NResources = CollectionUtils.newList(i18NResource.getResources().size());

            for (String resource : i18NResource.getResources()) {
                if (resource.startsWith("classpath:")) {
                    i18NResources.add(I18NResourceFactory.fromURL(resource.substring(resource.lastIndexOf('/') + 1),
                            module.getBundle().getResource(resource.substring(10))));
                }
            }

            languageService.registerResource(i18NResource.getName(), i18NResource.getVersion(),
                    i18NResources.toArray(new org.jtheque.i18n.I18NResource[i18NResources.size()]));
        }
    }

    /**
     * Import the i18n resources.
     *
     * @param reader The XML reader.
     *
     * @return A List containing all the I18NResource of the module.
     *
     * @throws XMLException If an error occurs during XML parsing.
     */
    private static List<I18NResource> importI18NResources(XMLOverReader reader) throws XMLException {
        List<I18NResource> i18NResources = CollectionUtils.newList();

        while (reader.next("/config/i18n/resource")) {
            List<String> resources = CollectionUtils.newList(5);

            String name = reader.readString("@name");
            Version version = Version.get(reader.readString("@version"));

            while (reader.next("classpath")) {
                resources.add("classpath:" + reader.readString("text()"));
            }

            i18NResources.add(new I18NResource(name, version, resources));
        }

        return i18NResources;
    }

    /**
     * Import the image resources.
     *
     * @param reader The XML reader.
     *
     * @return A List containing all the ImageResource of the module.
     *
     * @throws XMLException If an exception occurs during XML parsing.
     */
    private static List<ImageResource> importImageResources(XMLOverReader reader) throws XMLException {
        List<ImageResource> imageResources = CollectionUtils.newList(5);

        while (reader.next("/config/images/resource")) {
            String name = reader.readString("@name");
            String classpath = reader.readString("classpath");

            imageResources.add(new ImageResource(name, "classpath:" + classpath));
        }

        return imageResources;
    }

    /**
     * Import the resources.
     *
     * @param reader The XML reader.
     *
     * @return A List containing all the Resource of the module.
     *
     * @throws XMLException If an exception occurs during XML parsing.
     */
    private List<Resource> importResources(XMLOverReader reader) throws XMLException {
        List<Resource> resources = CollectionUtils.newList(5);

        while (reader.next("/config/resources/resource")) {
            String id = reader.readString("@id");
            Version version = Version.get(reader.readString("@version"));
            String url = reader.readString("@url");

            resources.add(resourceService.getOrDownloadResource(id, version, url));
        }

        return resources;
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
         * @param file The file to installFromRepository.
         */
        private ModuleLoaderTask(File file) {
            this.file = file;
        }

        @Override
        public Module call() {
            try {
                return installModule(file);
            } catch (ModuleException e) {
                OSGiUtils.getService(bundleContext, ErrorService.class).addError(Errors.newError(e));
            }

            return null;
        }
    }

    /**
     * A Builder for the SimpleModule instance.
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
            return new SimpleModule(this);
        }
    }

    /**
     * A module implementation.
     *
     * @author Baptiste Wicht
     */
    @Immutable
    private final class SimpleModule implements Module {
        private final String id;
        private final Bundle bundle;
        private final Version version;
        private final Version coreVersion;
        private final String[] dependencies;
        private final String url;
        private final String updateUrl;
        private final String messagesUrl;
        private final boolean collection;

        private volatile ModuleState state;

        /**
         * Create a module container using the given builder informations.
         *
         * @param builder The builder to get the informations from.
         */
        private SimpleModule(Builder builder) {
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

            state = ModuleState.INSTALLED;
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

    /**
     * A module file filter. This filter accept only the JAR files.
     *
     * @author Baptiste Wicht
     */
    private static final class ModuleFilter implements FileFilter {
        @Override
        public boolean accept(File file) {
            return file.isFile() && file.getName().toLowerCase(Locale.getDefault()).endsWith(".jar");
        }
    }
}