package org.jtheque.update.impl.versions;

import org.jtheque.core.able.ICore;
import org.jtheque.core.able.Versionable;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.errors.utils.Errors;
import org.jtheque.modules.able.Module;
import org.jtheque.resources.impl.CoreDescriptor;
import org.jtheque.resources.impl.CoreVersion;
import org.jtheque.resources.impl.DescriptorReader;
import org.jtheque.resources.impl.ModuleDescriptor;
import org.jtheque.resources.impl.ModuleVersion;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.collections.CollectionUtils;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
 * A loader for the online version's file.
 *
 * @author Baptiste Wicht
 */
public final class DescriptorsLoader implements IVersionsLoader {
    private static final int CACHE_INITIAL_SIZE = 12;

    private final Map<String, ModuleDescriptor> cache = new HashMap<String, ModuleDescriptor>(CACHE_INITIAL_SIZE);

    private CoreDescriptor coreDescriptor;

    @Resource
    private IErrorService errorService;

    @Override
    public Collection<Version> getVersions(Module object) {
        ModuleDescriptor descriptor = getModuleDescriptor(object.getDescriptorURL());

        if (descriptor != null) {
            Collection<Version> versions = new ArrayList<Version>(descriptor.getVersions().size());

            for (ModuleVersion v : descriptor.getVersions()) {
                versions.add(v.getVersion());
            }

            return versions;
        }

        return CollectionUtils.emptyList();
    }

    @Override
    public Collection<Version> getCoreVersions() {
        CoreDescriptor descriptor = getCoreDescriptor();

        if(descriptor != null){
            Collection<Version> versions = new ArrayList<Version>(descriptor.getVersions().size());

            for (CoreVersion v : descriptor.getVersions()) {
                versions.add(v.getVersion());
            }

            return versions;
        }

        return CollectionUtils.emptyList();
    }

    @Override
    public Collection<ModuleVersion> getOnlineVersions(Module object) {
        return getModuleDescriptor(object.getDescriptorURL()).getVersions();
    }

    /**
     * Return all the online core versions.
     *
     * @return An Iterable on the online core versions.
     */
    private Iterable<CoreVersion> getOnlineCoreVersions() {
        return getCoreDescriptor().getVersions();
    }

    @Override
    public ModuleVersion getModuleVersion(Version version, Module object) {
        for (ModuleVersion m : getOnlineVersions(object)) {
            if (m.getVersion().equals(version)) {
                return m;
            }
        }

        return null;
    }

    @Override
    public CoreVersion getCoreVersion(Version version) {
        for (CoreVersion m : getOnlineCoreVersions()) {
            if (m.getVersion().equals(version)) {
                return m;
            }
        }

        return null;
    }

    @Override
    public Version getMostRecentCoreVersion() {
        CoreDescriptor descriptor = getCoreDescriptor();

        return descriptor != null ? descriptor.getMostRecentVersion().getVersion() : null;
    }

    @Override
    public Version getMostRecentVersion(Versionable object) {
        ModuleDescriptor descriptor = getModuleDescriptor(object.getDescriptorURL());

        return descriptor != null ? descriptor.getMostRecentVersion().getVersion() : null;
    }

    @Override
    public ModuleVersion getMostRecentModuleVersion(String url) {
        ModuleDescriptor versionsFile = getModuleDescriptor(url);

        return versionsFile != null ? versionsFile.getMostRecentVersion() : null;
    }


    /**
     * Return the version's file of the object.
     *
     * @param url The url of the descriptor.
     *
     * @return The version's file of the module.
     */
    private ModuleDescriptor getModuleDescriptor(String url) {
        if (!cache.containsKey(url)) {
            cache.put(url, DescriptorReader.readModuleDescriptor(url));

            if (!cache.containsKey(url)) {
                errorService.addError(Errors.newI18nError(
                        "error.update.internet.title", ArrayUtils.EMPTY_ARRAY,
                        "error.update.internet"));
            }
        }

        return cache.get(url);
    }

    /**
     * Return the descriptor of the core. If the descriptor of the core has not been previously loaded, this
     * method will load it.
     *
     * @return The core descriptor or null if it cannot be read (not reachable or not valid file). 
     */
    public CoreDescriptor getCoreDescriptor() {
        if (coreDescriptor == null) {
            coreDescriptor = DescriptorReader.readCoreDescriptor(ICore.DESCRIPTOR_FILE_URL);

            if (coreDescriptor == null) {
                errorService.addError(Errors.newI18nError(
                        "error.update.internet.title", ArrayUtils.EMPTY_ARRAY,
                        "error.update.internet"));
            }
        }

        return coreDescriptor;
    }
}
