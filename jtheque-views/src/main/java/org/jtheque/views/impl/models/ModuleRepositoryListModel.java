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
import org.jtheque.modules.able.IModuleDescription;
import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.DefaultListModel;
import java.util.List;

/**
 * A List model to display the modules from a repository.
 *
 * @author Baptiste Wicht
 */
public final class ModuleRepositoryListModel extends DefaultListModel {
    private final List<IModuleDescription> modules;

    /**
     * Construct a new ModuleListModel.
     */
    public ModuleRepositoryListModel(IModuleService moduleService) {
        super();

        modules = CollectionUtils.copyOf(moduleService.getModulesFromRepository());
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
}