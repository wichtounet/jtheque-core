package org.jtheque.core.managers.schema;

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

import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;

public abstract class DefaultSchema extends AbstractSchema {
    private final Version version;
    private final String id;
    private final String[] dependencies;

    private static final String[] EMPTY_DEPENDENCIES = {};

    protected DefaultSchema(Version version, String id, String... dependencies) {
        super();

        this.version = version;
        this.id = id;
        this.dependencies = dependencies != null ? ArrayUtils.copyOf(dependencies) : EMPTY_DEPENDENCIES;
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String[] getDependencies() {
        return dependencies;
    }

    @Override
    public void importDataFromHSQL(Iterable<Insert> inserts) {
        //Not all the schema must implement that method
    }
}
