package org.jtheque.modules.impl;

import org.jtheque.i18n.able.LanguageService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.modules.able.Resources;
import org.jtheque.utils.annotations.Immutable;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;

import org.osgi.framework.Bundle;

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
 * A module implementation.
 *
 * @author Baptiste Wicht
 */
@Immutable
public final class ModuleContainer implements Module {
    private final String id;
    private final Bundle bundle;
    private final Version version;
    private final Version coreVersion;
    private final ModuleResources resources;
    private final String[] dependencies;
    private final String url;
    private final String updateUrl;
    private final String messagesUrl;
    private final boolean collection;

    private ModuleState state;

    /**
     * For internationalization purpose.
     */
    private final LanguageService languageService;

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
        resources = builder.resources;
        bundle = builder.bundle;
        dependencies = builder.dependencies;
        url = builder.url;
        updateUrl = builder.updateUrl;
        languageService = builder.languageService;
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
        if (languageService == null) {
            return key;
        }

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

    @Override
    public Resources getResources() {
        return resources;
    }

    /**
     * A Builder for the ModuleContainer instance.
     *
     * @author Baptiste Wicht
     */
    public static class Builder {
        private String id;
        private Bundle bundle;
        private Version version;
        private Version coreVersion;
        private String[] dependencies;
        private String url;
        private String updateUrl;
        private String messagesUrl;
        private boolean collection;
        private LanguageService languageService;
        private ModuleResources resources = new ModuleResources();

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
         * Set the resources of of the module.
         *
         * @param resources The resources of the module.
         */
        public void setResources(ModuleResources resources) {
            this.resources = resources;
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
         * Set the language service of the container. If this service is not setted, the informations will not be
         * internationalized.
         *
         * @param languageService The language service.
         */
        public void setLanguageService(LanguageService languageService) {
            this.languageService = languageService;
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
         *
         * @see #getDependencies()
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
}