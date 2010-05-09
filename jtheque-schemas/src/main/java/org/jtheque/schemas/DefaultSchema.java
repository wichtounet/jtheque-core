package org.jtheque.schemas;

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

import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.ArrayUtils;
import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;

public abstract class DefaultSchema extends AbstractSchema implements BundleContextAware {
    private final Version version;
    private final String id;
    private final String[] dependencies;

    private static final String[] EMPTY_DEPENDENCIES = {};

    protected DefaultSchema(Version version, String id, String... dependencies) {
        super();

        this.version = version;
        this.id = id;
        this.dependencies = dependencies == null ? EMPTY_DEPENDENCIES : ArrayUtils.copyOf(dependencies);
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
    public void setBundleContext(BundleContext bundleContext) {
        setJdbcTemplate(bundleContext);
    }
}
