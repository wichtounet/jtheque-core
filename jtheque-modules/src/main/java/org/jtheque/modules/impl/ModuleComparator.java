package org.jtheque.modules.impl;

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

import org.jtheque.modules.able.Module;
import org.jtheque.utils.StringUtils;

import java.util.Comparator;

/**
 * @author Baptiste Wicht
 */
final class ModuleComparator implements Comparator<ModuleContainer> {
    @Override
    public int compare(ModuleContainer o1, ModuleContainer o2) {
        //First the modules without dependency
        if (hasDependency(o1) && !hasDependency(o2)) {
            return -1;
        }

        //TODO Improve that on the base of the schema comparator

        //And finally all the others
        return 0;
    }

    /**
     * Indicate if the module has dependency or not.
     *
     * @param module The module to test.
     * @return true if the module has dependency else false.
     */
    private static boolean hasDependency(Module module) {
        return StringUtils.isNotEmpty(module.getDependencies());
    }
}