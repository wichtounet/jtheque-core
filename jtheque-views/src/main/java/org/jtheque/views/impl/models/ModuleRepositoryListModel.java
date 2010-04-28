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
import org.jtheque.modules.impl.ModuleDescription;
import org.jtheque.utils.collections.CollectionUtils;

import javax.swing.DefaultListModel;
import java.util.List;

/**
 * A List model to display the modules from a repository.
 *
 * @author Baptiste Wicht
 */
public final class ModuleRepositoryListModel extends DefaultListModel {
    private final List<ModuleDescription> modules;

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