package org.jtheque.schemas;

import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.states.IStateService;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.Version;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
 * A Schema manager implementation.
 *
 * @author Baptiste Wicht
 * @see ISchemaService
 */
public final class SchemaService implements ISchemaService, ModuleListener {
    private final List<Schema> schemas = new ArrayList<Schema>(10);

    private SchemaConfiguration configuration;

    private IStateService stateService;

    public void setStateManager(IStateService stateService) {
        this.stateService = stateService;
    }

    @PostConstruct
    public void init(){
        configuration = stateService.getOrCreateState(SchemaConfiguration.class);

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