package org.jtheque.schemas.impl;

import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.able.ModuleResourceCache;
import org.jtheque.schemas.able.Schema;
import org.jtheque.states.able.StateService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.bean.Version;
import org.jtheque.utils.collections.CollectionUtils;

import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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
 * A Schema manager implementation.
 *
 * @author Baptiste Wicht
 * @see org.jtheque.schemas.able.SchemaService
 */
@ThreadSafe
public final class SchemaService implements org.jtheque.schemas.able.SchemaService, ModuleListener {
    private final List<Schema> schemas = CollectionUtils.newList();

    private final SchemaConfiguration configuration;

    /**
     * Construct a new SchemaService.
     *
     * @param stateService The state stateService.
     */
    public SchemaService(StateService stateService) {
        super();

        configuration = stateService.getState(new SchemaConfiguration());
    }

    @Override
    public void registerSchema(String moduleId, Schema schema) {
        synchronized (schemas) {
            schemas.add(schema);
        }

        if (StringUtils.isNotEmpty(moduleId)) {
            ModuleResourceCache.addResource(moduleId, Schema.class, schema);
        }

        checkForUpdates();
    }

    /**
     * Check for updates of the schemas.
     */
    private void checkForUpdates() {
        synchronized (schemas) {
            Collections.sort(schemas);

            for (Schema schema : schemas) {
                if (canBeInstalled(schema)) {
                    Version installedVersion = configuration.getVersion(schema.getId());

                    if (installedVersion == null) {
                        LoggerFactory.getLogger(getClass()).info("Install schema {}:{}", schema.getId(), schema.getVersion());

                        schema.install();

                        configuration.setVersion(schema.getId(), schema.getVersion());
                    } else if (schema.getVersion().isGreaterThan(installedVersion)) {
                        schema.update(installedVersion);
                    }
                }
            }
        }
    }

    /**
     * Test if the schema can be installed.
     *
     * @param schema The schema to test.
     *
     * @return {@code true} if the schema can be installed else {@code false}.
     */
    private boolean canBeInstalled(Schema schema) {
        for (String dependency : schema.getDependencies()) {
            Version installedVersion = configuration.getVersion(dependency);

            if (installedVersion == null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void moduleStarted(Module module) {
        //Nothing to do here
    }

    @Override
    public void moduleStopped(Module module) {
        Set<Schema> resources = ModuleResourceCache.getResources(module.getId(), Schema.class);

        synchronized (schemas) {
            for (Schema schema : resources) {
                schemas.remove(schema);
            }
        }

        ModuleResourceCache.removeResourceOfType(module.getId(), Schema.class);
    }

    @Override
    public void moduleInstalled(Module module) {
        //Nothing to do here
    }

    @Override
    public void moduleUninstalled(Module module) {
        //Nothing to do here
    }
}