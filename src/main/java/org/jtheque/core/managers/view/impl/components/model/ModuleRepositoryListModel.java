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
import org.jtheque.core.managers.update.repository.ModuleDescription;
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
    public ModuleRepositoryListModel() {
        super();

        modules = CollectionUtils.copyOf(Managers.getManager(IModuleManager.class).getModulesFromRepository());
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