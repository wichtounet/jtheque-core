package org.jtheque.modules.impl;

import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.i18n.ILanguageService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.utils.bean.Version;
import org.osgi.framework.Bundle;

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
 * @author Baptiste Wicht
 */
public final class ModuleContainer implements Module {
    private final Bundle module;

    private ModuleState state;

    private String id;
    private String i18n;
    private Version version;
    private Version coreVersion;
    private String[] bundles;
    private String[] dependencies;
    private String url;
    private String updateUrl;
    private String messagesUrl;
    private boolean collection;

    public ModuleContainer(Bundle module) {
        super();

        this.module = module;
    }

    @Override
    public Bundle getModule() {
        return module;
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

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return OSGiUtils.getService(module.getBundleContext(), ILanguageService.class).getMessage(id + ".name");
    }

    @Override
    public String getAuthor() {
        return OSGiUtils.getService(module.getBundleContext(), ILanguageService.class).getMessage(id + ".author");
    }

    @Override
    public String getDescription() {
        return OSGiUtils.getService(module.getBundleContext(), ILanguageService.class).getMessage(id + ".description");
    }

    @Override
    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }

    @Override
    public Version getCoreVersion() {
        return coreVersion;
    }

    public void setCoreVersion(Version coreVersion) {
        this.coreVersion = coreVersion;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    @Override
    public String[] getBundles() {
        return bundles;
    }

    public void setBundles(String[] bundles) {
        this.bundles = bundles;
    }

    @Override
    public String[] getDependencies() {
        return dependencies;
    }

    public void setDependencies(String[] dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String getMessagesUrl() {
        return messagesUrl;
    }

    public void setMessagesUrl(String messagesUrl) {
        this.messagesUrl = messagesUrl;
    }

    @Override
    public Version getMostRecentVersion() {
        //TODO : return Managers.getManager(IUpdateManager.class).getMostRecentVersion(this);

        return null;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    @Override
    public boolean isCollection() {
        return collection;
    }
}