package org.jtheque.update.versions;

import org.jtheque.errors.IErrorService;
import org.jtheque.modules.able.Module;
import org.jtheque.update.Updatable;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
        return getVersionsFile(object).getMostRecentVersion().getVersion();
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
