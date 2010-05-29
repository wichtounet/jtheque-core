package org.jtheque.modules.impl;

import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.i18n.able.ILanguageService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.modules.able.Resources;
import org.jtheque.utils.bean.Version;

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
 * @author Baptiste Wicht
 */
public final class ModuleContainer implements Module {
    private final Bundle bundle;

    private ModuleState state;

    private String id;
    private Version version;
    private Version coreVersion;
    private String[] libs;
    private String[] dependencies;
    private String url;
    private String updateUrl;
    private String messagesUrl;
    private boolean collection;
    private ModuleResources resources;

    /**
     * Construct a new ModuleContainer from the specified bundle.
     *
     * @param bundle The bundle of the module.
     */
    public ModuleContainer(Bundle bundle) {
        super();

        this.bundle = bundle;
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
    public String getDisplayState() {
        return OSGiUtils.getService(bundle.getBundleContext(), ILanguageService.class).getMessage(state.getKey());
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Set the id of the module.
     *
     * @param id The id of the module.
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return OSGiUtils.getService(bundle.getBundleContext(), ILanguageService.class).getMessage(id + ".name");
    }

    @Override
    public String getAuthor() {
        return OSGiUtils.getService(bundle.getBundleContext(), ILanguageService.class).getMessage(id + ".author");
    }

    @Override
    public String getDescription() {
        return OSGiUtils.getService(bundle.getBundleContext(), ILanguageService.class).getMessage(id + ".description");
    }

    @Override
    public Version getVersion() {
        return version;
    }

    /**
     * Set the version of the module.
     *
     * @param version The version of the module.
     */
    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public Version getCoreVersion() {
        return coreVersion;
    }

    /**
     * Set the core version needed by the module.
     *
     * @param coreVersion The core version needed by the module.
     */
    public void setCoreVersion(Version coreVersion) {
        this.coreVersion = coreVersion;
    }

    @Override
    public String getUrl() {
        return url;
    }

    /**
     * Set the URL of the site of the module.
     *
     * @param url THe URL of the site of the module.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUpdateUrl() {
        return updateUrl;
    }

    /**
     * Set the the URL to the update file of the module.
     *
     * @param updateUrl The URL to the update file of the module.
     */
    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    @Override
    public String[] getLibs() {
        return libs;
    }

    /**
     * Set the libs of the module.
     *
     * @param libs The libs of the module.
     * @see #getLibs()
     */
    public void setLibs(String[] libs) {
        this.libs = libs;
    }

    @Override
    public String[] getDependencies() {
        return dependencies;
    }

    /**
     * Set the dependencies of the module.
     *
     * @param dependencies The dependencies of the module.
     * @see #getDependencies()
     */
    public void setDependencies(String[] dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String getMessagesUrl() {
        return messagesUrl;
    }

    /**
     * Set the messages URL.
     *
     * @param messagesUrl The messages URL of the mdule.
     */
    public void setMessagesUrl(String messagesUrl) {
        this.messagesUrl = messagesUrl;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Set the boolean tag indicating if the module is collection-based or not.
     *
     * @param collection boolean tag indicating if the module is collection-based (true) or not (false).
     */
    public void setCollection(boolean collection) {
        this.collection = collection;
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
     * Set the resources of of the module.
     *
     * @param resources The resources of the module.
     */
    public void setResources(ModuleResources resources) {
        this.resources = resources;
	}
}