package org.jtheque.file.impl;

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

import org.jtheque.file.able.Exporter;
import org.jtheque.file.able.Importer;
import org.jtheque.file.able.ModuleBackup;
import org.jtheque.file.able.ModuleBackuper;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.annotations.GuardedBy;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileException;
import org.jtheque.xml.utils.XMLException;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A FileService implementation.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class FileService implements org.jtheque.file.able.FileService, ModuleListener {
    @GuardedInternally
    private final List<ModuleBackuper> backupers = CollectionUtils.newConcurrentList();

    @GuardedBy("exporters")
    private final Map<String, Set<Exporter<?>>> exporters =  CollectionUtils.newHashMap(3);

    @GuardedBy("importers")
    private final Map<String, Set<Importer>> importers = CollectionUtils.newHashMap(3);

    @Override
    public void registerExporter(String module, Exporter<?> exporter){
        synchronized (exporters){
            if(!exporters.containsKey(module)){
                exporters.put(module, CollectionUtils.<Exporter<?>>newSet());
            }

            exporters.get(module).add(exporter);
        }
    }

    @Override
    public void registerImporter(String module, Importer importer) {
        synchronized (importers) {
            if (!importers.containsKey(module)) {
                importers.put(module, CollectionUtils.<Importer>newSet());
            }
            
            importers.get(module).add(importer);
        }
    }

    @Override
    public <T> void exportDatas(String module, String fileType, String file, Collection<T> datas) throws FileException {
        synchronized (exporters) {
            if (exporters.containsKey(module)) {
                for (Exporter<?> exporter : exporters.get(module)) {
                    if (exporter.canExportTo(fileType)) {
                        ((Exporter<T>) exporter).export(file, datas);

                        return;
                    }
                }
            }
        }
    }

    @Override
    public void importDatas(String module, String fileType, String file) throws FileException {
        synchronized (importers) {
            if (importers.containsKey(module)) {
                for (Importer importer : importers.get(module)) {
                    if (importer.canImportFrom(fileType)) {
                        importer.importFrom(file);

                        return;
                    }
                }
            }
        }
    }

    @Override
    public void backup(File file) {
        List<ModuleBackuper> activeBackupers = CollectionUtils.copyOf(backupers);

        Collections.sort(activeBackupers, new ModuleBackupComparator());

        Collection<ModuleBackup> backups = CollectionUtils.newList(activeBackupers.size());

        for (ModuleBackuper backuper : activeBackupers) {
            backups.add(backuper.backup());
        }

        XMLBackuper.backup(file, backups);
    }

    @Override
    public void restore(File file) throws XMLException {
        List<ModuleBackuper> activeBackupers = CollectionUtils.copyOf(backupers);

        Collections.sort(activeBackupers, new ModuleBackupComparator());

        List<ModuleBackup> restores = XMLRestorer.restore(file);

        for (ModuleBackuper backuper : activeBackupers) {
            for (ModuleBackup backup : restores) {
                if (backup.getId().equals(backuper.getId())) {
                    backuper.restore(backup);
                    break;
                }
            }
        }
    }

    @Override
    public void registerBackuper(String moduleId, ModuleBackuper backuper) {
        backupers.add(backuper);

        if (StringUtils.isNotEmpty(moduleId)) {
            ModuleResourceCache.addResource(moduleId, ModuleBackuper.class, backuper);
        }
    }

    @Override
    public void moduleStopped(Module module) {
        Set<ModuleBackuper> resources = ModuleResourceCache.getResource(module.getId(), ModuleBackuper.class);

        for (ModuleBackuper backuper : resources) {
            backupers.remove(backuper);
        }

        ModuleResourceCache.removeResourceOfType(module.getId(), ModuleBackuper.class);
    }

    @Override
    public void moduleStarted(Module module) {
        //Nothing to do here
    }

    @Override
    public void moduleInstalled(Module module) {
        //Nothing to do here
    }

    @Override
    public void moduleUninstalled(Module module) {
        //Nothing to do here
    }

    /**
     * A module backup comparator to compare the module backups using their dependencies.
     *
     * @author Baptiste Wicht
     */
    private static final class ModuleBackupComparator implements Comparator<ModuleBackuper> {
        @Override
        public int compare(ModuleBackuper backup1, ModuleBackuper backup2) {
            boolean hasDependency = StringUtils.isNotEmpty(backup1.getDependencies());
            boolean hasOtherDependency = StringUtils.isNotEmpty(backup2.getDependencies());

            if (hasDependency && !hasOtherDependency) {
                return 1;
            } else if (!hasDependency && hasOtherDependency) {
                return -1;
            } else {
                //The other depends on me
                if (ArrayUtils.contains(backup2.getDependencies(), backup1.getId())) {
                    return -1;
                }

                //I depends on the other
                if (ArrayUtils.contains(backup1.getDependencies(), backup2.getId())) {
                    return 1;
                }
            }

            return 0;
        }
    }
}