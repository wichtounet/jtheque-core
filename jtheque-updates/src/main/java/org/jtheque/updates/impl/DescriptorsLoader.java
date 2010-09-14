package org.jtheque.updates.impl;

import org.jtheque.core.Versionable;
import org.jtheque.errors.ErrorService;
import org.jtheque.errors.Errors;
import org.jtheque.modules.Module;
import org.jtheque.utils.annotations.NotThreadSafe;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.collections.CollectionUtils;

import javax.annotation.Resource;

import java.util.Collection;
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
@NotThreadSafe
final class DescriptorsLoader {
    private static final int CACHE_INITIAL_SIZE = 12;

    private final Map<String, ModuleDescriptor> cache = CollectionUtils.newHashMap(CACHE_INITIAL_SIZE);

    private CoreDescriptor coreDescriptor;

    @Resource
    private ErrorService errorService;

    /**
     * Return all the versions of the object.
     *
     * @param object The object to get the versions from.
     *
     * @return A List containing all the versions from.
     */
    Collection<Version> getVersions(Versionable object) {
        ModuleDescriptor descriptor = getModuleDescriptor(object.getDescriptorURL());

        if (descriptor != null) {
            Collection<Version> versions = CollectionUtils.newList(descriptor.getVersions().size());

            for (ModuleVersion v : descriptor.getVersions()) {
                versions.add(v.getVersion());
            }

            return versions;
        }

        return CollectionUtils.emptyList();
    }

    /**
     * Return all the core versions.
     *
     * @return A Collection containing all the core versions.
     */
    Collection<Version> getCoreVersions() {
        CoreDescriptor descriptor = getCoreDescriptor();

        if (descriptor != null) {
            Collection<Version> versions = CollectionUtils.newList(descriptor.getVersions().size());

            for (CoreVersion v : descriptor.getVersions()) {
                versions.add(v.getVersion());
            }

            return versions;
        }

        return CollectionUtils.emptyList();
    }

    /**
     * Return the online versions of the object.
     *
     * @param object The object to get the version from.
     *
     * @return A List containing all the online versions of the object.
     */
    Iterable<ModuleVersion> getOnlineVersions(Versionable object) {
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

    /**
     * Return the online version corresponding to the specified version.
     *
     * @param version The version to search for.
     * @param object  The object to search in.
     *
     * @return The corresponding online version.
     */
    ModuleVersion getModuleVersion(Version version, Module object) {
        for (ModuleVersion m : getOnlineVersions(object)) {
            if (m.getVersion().equals(version)) {
                return m;
            }
        }

        return null;
    }

    /**
     * Return the core version corresponding to the given version.
     *
     * @param version The version.
     *
     * @return The core version of this version if there is one otherwise {@code null}.
     */
    CoreVersion getCoreVersion(Version version) {
        for (CoreVersion m : getOnlineCoreVersions()) {
            if (m.getVersion().equals(version)) {
                return m;
            }
        }

        return null;
    }

    /**
     * Return the most recent core version.
     *
     * @return The most recent core version.
     */
    Version getMostRecentCoreVersion() {
        CoreDescriptor descriptor = getCoreDescriptor();

        return descriptor != null ? descriptor.getMostRecentVersion().getVersion() : null;
    }

    /**
     * Return the most recent version of the object.
     *
     * @param object The object. It can be the Core, a module or an updatable.
     *
     * @return The most recent version of the object.
     */
    Version getMostRecentVersion(Versionable object) {
        ModuleDescriptor descriptor = getModuleDescriptor(object.getDescriptorURL());

        if (descriptor == null || descriptor.getMostRecentVersion() == null) {
            return null;
        } else {
            return descriptor.getMostRecentVersion().getVersion();
        }
    }

    /**
     * Return the most recent version of the module at the given url.
     *
     * @param url The url of the descriptor.
     *
     * @return The most recent module version of the descriptor.
     */

    ModuleVersion getMostRecentModuleVersion(String url) {
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
            cache.put(url, ModuleDescriptorReader.readModuleDescriptor(url));

            if (!cache.containsKey(url)) {
                errorService.addError(Errors.newI18nError(
                        "error.update.internet.title", ArrayUtils.EMPTY_ARRAY,
                        "error.update.internet"));
            }
        }

        return cache.get(url);
    }

    /**
     * Return the descriptor of the core. If the descriptor of the core has not been previously loaded, this method will
     * load it.
     *
     * @return The core descriptor or null if it cannot be read (not reachable or not valid file).
     */
    CoreDescriptor getCoreDescriptor() {
        if (coreDescriptor == null) {
            coreDescriptor = CoreDescriptorReader.readCoreDescriptor();

            if (coreDescriptor == null) {
                errorService.addError(Errors.newI18nError(
                        "error.update.internet.title", ArrayUtils.EMPTY_ARRAY,
                        "error.update.internet"));
            }
        }

        return coreDescriptor;
    }
}
