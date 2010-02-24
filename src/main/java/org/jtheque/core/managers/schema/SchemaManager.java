package org.jtheque.core.managers.schema;

import org.jtheque.core.managers.AbstractActivableManager;
import org.jtheque.core.managers.ManagerException;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.module.ModuleListener;
import org.jtheque.core.managers.module.ModuleResourceCache;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.module.beans.ModuleState;
import org.jtheque.core.managers.state.IStateManager;
import org.jtheque.utils.bean.Version;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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
 * @see ISchemaManager
 */
public final class SchemaManager extends AbstractActivableManager implements ISchemaManager, ModuleListener {
    private final List<Schema> schemas = new ArrayList<Schema>(10);

    private SchemaConfiguration configuration;

    @Override
    public void init() throws ManagerException {
        configuration = Managers.getManager(IStateManager.class).getOrCreateState(SchemaConfiguration.class);

        Collections.sort(schemas);

        boolean recoveryNecessary = checkForRecovery();

        if (recoveryNecessary) {
            recoverData();
        }
    }

    /**
     * Recover the data from HSQLDB.
     */
    private void recoverData() {
        File logFile = new File(Managers.getCore().getFolders().getApplicationFolder(), "localhost.log");
        File scriptFile = new File(Managers.getCore().getFolders().getApplicationFolder(), "localhost.script");

        Collection<Insert> inserts = new ArrayList<Insert>(50);

        read(logFile, inserts);
        read(scriptFile, inserts);

        for (Schema schema : schemas) {
            recoverIfNecessary(inserts, schema);
        }
    }

    /**
     * Recover the schema if necessary.
     *
     * @param inserts The inserts of HSQLDB.
     * @param schema  The schema to recover.
     */
    private void recoverIfNecessary(Iterable<Insert> inserts, Schema schema) {
        if (configuration.isNotRecovered(schema.getId())) {
            schema.importDataFromHSQL(inserts);

            configuration.setRecovered(schema.getId());
        }
    }

    /**
     * Read a file of HSQLDB.
     *
     * @param file    The file to read.
     * @param inserts The inserts to add to.
     */
    private static void read(File file, Collection<Insert> inserts) {
        if (file.exists()) {
            inserts.addAll(HSQLFileReader.readFile(file));
        }
    }

    /**
     * Check if the recover is necessary.
     *
     * @return true if the recover is necessary else false.
     */
    private boolean checkForRecovery() {
        boolean dataToRecover = false;

        for (Schema schema : schemas) {
            Version installedVersion = configuration.getVersion(schema.getId());

            if (installedVersion == null) {
                schema.install();

                configuration.setVersion(schema.getId(), schema.getVersion());
            } else if (schema.getVersion().isGreaterThan(installedVersion)) {
                schema.update(installedVersion);
            }

            if (configuration.isNotRecovered(schema.getId())) {
                dataToRecover = true;
            }
        }

        return dataToRecover;
    }

    @Override
    public void registerSchema(String moduleId, Schema schema) {
        schemas.add(schema);

        ModuleResourceCache.addResource(moduleId, Schema.class, schema);
    }

    @Override
    public void moduleStateChanged(ModuleContainer module, ModuleState newState, ModuleState oldState) {
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