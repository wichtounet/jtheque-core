package org.jtheque.schemas;

import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.states.IStateService;
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

    public SchemaService(IStateService stateService) {
        super();

        configuration = stateService.getState(new SchemaConfiguration());

        Collections.sort(schemas);

        checkForUpdates();
    }

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

        if(StringUtils.isNotEmpty(moduleId)){
            ModuleResourceCache.addResource(moduleId, Schema.class, schema);
        }
    }

    @Override
    public void moduleStateChanged(Module module, ModuleState newState, ModuleState oldState) {
        if(oldState == ModuleState.LOADED && (newState == ModuleState.INSTALLED ||
                newState == ModuleState.DISABLED || newState == ModuleState.UNINSTALLED)){
            Set<Schema> resources = ModuleResourceCache.getResource(module.getId(), Schema.class);

            for(Schema schema : resources){
                schemas.remove(schema);
            }

            ModuleResourceCache.removeResourceOfType(module.getId(), Schema.class);
        }
    }
}