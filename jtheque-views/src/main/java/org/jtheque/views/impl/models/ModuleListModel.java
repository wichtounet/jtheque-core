package org.jtheque.views.impl.models;

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

import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleListener;
import org.jtheque.modules.able.ModuleState;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A List model to display the modules.
 *
 * @author Baptiste Wicht
 */
public final class ModuleListModel extends DefaultListModel implements ModuleListener {
    private final List<Module> modules;
    private final IModuleService moduleService;

    public ModuleListModel(IModuleService moduleService) {
        super();

        this.moduleService = moduleService;

        moduleService.addModuleListener(this);

        modules = new ArrayList<Module>(moduleService.getModules());
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
    public void moduleStateChanged(Module module, ModuleState newState, ModuleState oldState) {
        if(newState == ModuleState.UNINSTALLED){
            int index = modules.indexOf(module);

            modules.remove(module);

            fireIntervalRemoved(this, index, index);
        }

        if(oldState == null){
            modules.clear();
            modules.addAll(moduleService.getModules());

            fireContentsChanged(this, 0, modules.size());
        }
    }
}