package org.jtheque.schemas.impl;

import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.schemas.able.ISchemaService;
import org.jtheque.schemas.able.Schema;
import org.jtheque.states.able.IStateService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;

import java.util.ArrayList;
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
 * @see ISchemaService
 */
public final class SchemaService implements ISchemaService, ModuleListener {
    private final List<Schema> schemas = new ArrayList<Schema>(10);

    private final SchemaConfiguration configuration;

    /**
     * Construct a new SchemaService.
     *
     * @param stateService The state stateService.
     */
    public SchemaService(IStateService stateService) {
        super();

        configuration = stateService.getState(new SchemaConfiguration());

        Collections.sort(schemas);

        checkForUpdates();
    }

    /**
     * Check for updates of the schemas.
     */
    private void checkForUpdates() {
        for (Schema schema : schemas) {
            Version installedVersion = configuration.getVersion(schema.getId());

            if (installedVersion == null) {
                schema.install();

                configuration.setVersion(schema.getId(), schema.getVersion());
            } else if (schema.getVersion().isGreaterThan(installedVersion)) {
                schema.update(installedVersion);
            }
        }
    }

    @Override
    public void registerSchema(String moduleId, Schema schema) {
        schemas.add(schema);

        if (StringUtils.isNotEmpty(moduleId)) {
            ModuleResourceCache.addResource(moduleId, Schema.class, schema);
        }
    }

    @Override
    public void moduleStarted(Module module) {
        //Nothing to do here
    }

    @Override
    public void moduleStopped(Module module) {
        Set<Schema> resources = ModuleResourceCache.getResource(module.getId(), Schema.class);

        for (Schema schema : resources) {
            schemas.remove(schema);
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