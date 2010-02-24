package org.jtheque.core.managers.view.impl.components.model;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.module.IModuleManager;
import org.jtheque.core.managers.module.ModuleListener;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.module.beans.ModuleState;
import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.DefaultListModel;
import java.util.List;

/**
 * A List model to display the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleListModel extends DefaultListModel implements ModuleListener {
    private final List<ModuleContainer> modules;

    /**
     * Construct a new ModuleListModel.
     */
    public ModuleListModel() {
        super();

        Managers.getManager(IModuleManager.class).addModuleListener(this);


        modules = CollectionUtils.copyOf(Managers.getManager(IModuleManager.class).getModules());
    }

    @Override
    public Object getElementAt(int index) {
        return modules.get(index);
    }

    @Override
    public Object get(int index) {
        return modules.get(index);
    }

    @Override
    public int getSize() {
        return modules.size();
    }

    @Override
    public void moduleStateChanged(ModuleContainer module, ModuleState newState, ModuleState oldState) {
        if(newState == ModuleState.UNINSTALLED){
            int index = modules.indexOf(module);

            modules.remove(module);

            fireIntervalRemoved(this, index, index);
        }

        if(oldState == null){
            modules.clear();
            modules.addAll(Managers.getManager(IModuleManager.class).getModules());

            fireContentsChanged(this, 0, modules.size());
        }
    }
}