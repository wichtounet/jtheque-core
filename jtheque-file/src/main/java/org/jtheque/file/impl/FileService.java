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

import org.jtheque.file.able.IFileService;
import org.jtheque.file.able.ModuleBackup;
import org.jtheque.file.able.ModuleBackuper;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.utils.ModuleResourceCache;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.xml.utils.XMLException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * A FileService implementation.
 *
 * @author Baptiste Wicht
 */
public final class FileService implements IFileService, ModuleListener {
    private final List<ModuleBackuper> backupers = new ArrayList<ModuleBackuper>(5);

    @Override
    public void backup(File file) {
        Collection<ModuleBackup> backups = new ArrayList<ModuleBackup>(backupers.size());

        Collections.sort(backupers, new ModuleBackupComparator());

        for (ModuleBackuper backuper : backupers) {
            backups.add(backuper.backup());
        }

        XMLBackuper.backup(file, backups);
    }

    @Override
    public void restore(File file) throws XMLException {
        List<ModuleBackup> restores = XMLRestorer.restore(file);

        Collections.sort(backupers, new ModuleBackupComparator());

        for (ModuleBackuper backuper : backupers) {
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
    private static class ModuleBackupComparator implements Comparator<ModuleBackuper> {
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
                if (ArrayUtils.search(backup2.getDependencies(), backup1.getId())) {
                    return -1;
                }

                //I depends on the other
                if (ArrayUtils.search(backup1.getDependencies(), backup2.getId())) {
                    return 1;
                }
            }

            return 0;
        }
    }
}