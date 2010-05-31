package org.jtheque.update.impl.versions;

import org.jtheque.errors.able.IErrorService;
import org.jtheque.modules.able.Module;
import org.jtheque.update.able.Updatable;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;

import javax.annotation.Resource;

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
public final class VersionsLoader implements IVersionsLoader {
    private final Map<Object, VersionsFile> cache;

    private static final int CACHE_INITIAL_SIZE = 20;

    @Resource
    private IErrorService errorService;

    /**
     * Construct a new VersionsLoader.
     */
    public VersionsLoader() {
        super();

        cache = new HashMap<Object, VersionsFile>(CACHE_INITIAL_SIZE);
    }

    @Override
    public Version getVersion(Object object) {
        if (isModule(object)) {
            return ((Module) object).getVersion();
        } else if (isUpdatable(object)) {
            return ((Updatable) object).getVersion();
        }

        return null;
    }

    /**
     * Test if the object is a module.
     *
     * @param object The object to test.
     * @return true if the object is a module else false.
     */
    private static boolean isModule(Object object) {
        return object instanceof Module;
    }

    /**
     * Test if the object is an updatable.
     *
     * @param object The object to test.
     * @return true if the object is an updatable else false.
     */
    private static boolean isUpdatable(Object object) {
        return object instanceof Updatable;
    }

    @Override
    public Collection<Version> getVersions(Object object) {
        VersionsFile file = getVersionsFile(object);

        return file == null ? CollectionUtils.<Version>emptyList() : CollectionUtils.expand(file.getVersions(), new VersionExpander());
    }

    @Override
    public Collection<OnlineVersion> getOnlineVersions(Object object) {
        return getVersionsFile(object).getVersions();
    }

    @Override
    public OnlineVersion getOnlineVersion(Version version, Object object) {
        for (OnlineVersion v : getOnlineVersions(object)) {
            if (v.getVersion().equals(version)) {
                return v;
            }
        }

        return null;
    }

    @Override
    public InstallVersion getInstallVersion(String versionFileURL) {
        return new VersionsFileReader().read(versionFileURL).getInstallVersion();
    }

    @Override
    public Version getMostRecentVersion(Object object) {
        VersionsFile versionsFile = getVersionsFile(object);

        if(versionsFile != null){
            return versionsFile.getMostRecentVersion().getVersion();
        }

        return null;
    }

    /**
     * Return the version's file of the object.
     *
     * @param object The object to get the version's file.
     * @return The version's file of the module.
     */
    private VersionsFile getVersionsFile(Object object) {
        if (!cache.containsKey(object)) {
            cache.put(object, new VersionsFileReader().read(object));

            if (!cache.containsKey(object)) {
                errorService.addInternationalizedError("error.update.internet");
            }
        }

        return cache.get(object);
    }
}
