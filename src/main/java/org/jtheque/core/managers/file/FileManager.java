package org.jtheque.core.managers.file;

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

import org.jtheque.core.managers.AbstractActivableManager;
import org.jtheque.core.managers.ManagerException;
import org.jtheque.core.managers.file.able.ModuleBackup;
import org.jtheque.core.managers.file.able.ModuleBackuper;
import org.jtheque.core.managers.file.impl.CoreBackuper;
import org.jtheque.core.managers.file.impl.XMLBackuper;
import org.jtheque.core.managers.file.impl.XMLRestorer;
import org.jtheque.core.managers.module.ModuleListener;
import org.jtheque.core.managers.module.ModuleResourceCache;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.module.beans.ModuleState;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.io.FileException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * A FileManager implementation.
 *
 * @author Baptiste Wicht
 */
public final class FileManager extends AbstractActivableManager implements IFileManager, ModuleListener {
    private final List<ModuleBackuper> backupers = new ArrayList<ModuleBackuper>(5);

    @Override
    public void init() throws ManagerException {
        backupers.add(new CoreBackuper());
    }

    @Override
    public void backup(File file) throws FileException {
        Collection<ModuleBackup> backups = new ArrayList<ModuleBackup>(backupers.size());

        Collections.sort(backupers, new ModuleBackupComparator());

        for(ModuleBackuper backuper : backupers){
            backups.add(backuper.backup());
        }

        XMLBackuper.backup(file, backups);
    }

    @Override
    public void restore(File file) throws FileException {
        List<ModuleBackup> restores = XMLRestorer.restore(file);

        Collections.sort(backupers, new ModuleBackupComparator());

        for(ModuleBackuper backuper : backupers){
            for(ModuleBackup backup : restores){
                if(backup.getId().equals(backuper.getId())){
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
    public void moduleStateChanged(ModuleContainer module, ModuleState newState, ModuleState oldState) {
        if(oldState == ModuleState.LOADED && (newState == ModuleState.INSTALLED ||
                newState == ModuleState.DISABLED || newState == ModuleState.UNINSTALLED)){
            Set<ModuleBackuper> resources = ModuleResourceCache.getResource(module.getId(), ModuleBackuper.class);

            for(ModuleBackuper backuper : resources){
                backupers.remove(backuper);
            }

            ModuleResourceCache.removeResourceOfType(module.getId(), ModuleBackuper.class);
        }
    }

    private static class ModuleBackupComparator implements Comparator<ModuleBackuper> {
        @Override
        public int compare(ModuleBackuper backup1, ModuleBackuper backup2) {
            boolean hasDependency = StringUtils.isNotEmpty(backup1.getDependencies());
            boolean hasOtherDependency = StringUtils.isNotEmpty(backup2.getDependencies());

            if (hasDependency && !hasOtherDependency) {
                return 1;
            } else if (!hasDependency && hasOtherDependency) {
                return -1;
            } else if(hasDependency && hasOtherDependency){
                for (String dependency : backup2.getDependencies()) {
                    if (dependency.equals(backup1.getId())) {//The other depends on me
                        return -1;
                    }
                }

                for (String dependency : backup1.getDependencies()) {
                    if (dependency.equals(backup2.getId())) {//I depends on the other
                        return 1;
                    }
                }
            }

            return 0;
        }
    }
}