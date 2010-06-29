package org.jtheque.update.impl.versions;

import org.jtheque.core.able.Versionable;
import org.jtheque.errors.able.IErrorService;
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

    @Resource
    private IErrorService errorService;

    @Override
    public Collection<Version> getVersions(Versionable object) {
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
    public Collection<ModuleVersion> getOnlineVersions(Versionable object) {
        return getModuleDescriptor(object.getDescriptorURL()).getVersions();
    }

    @Override
    public ModuleVersion getModuleVersion(Version version, Versionable object) {
        for (ModuleVersion m : getOnlineVersions(object)) {
            if (m.getVersion().equals(version)) {
                return m;
            }
        }

        return null;
    }

    @Override
    public Version getMostRecentVersion(Versionable object) {
        ModuleDescriptor versionsFile = getModuleDescriptor(object.getDescriptorURL());

        return versionsFile != null ? versionsFile.getMostRecentVersion().getVersion() : null;
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
                errorService.addInternationalizedError(
                        "error.update.internet.title", ArrayUtils.EMPTY_ARRAY,
                        "error.update.internet");
            }
        }

        return cache.get(url);
    }
}
