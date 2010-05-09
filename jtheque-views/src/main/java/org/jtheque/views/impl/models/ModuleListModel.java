package org.jtheque.views.impl.models;

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