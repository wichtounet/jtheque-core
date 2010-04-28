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
import org.jtheque.utils.collections.ArrayUtils;

import java.util.Comparator;

/**
 * @author Baptiste Wicht
 */
final class ModuleComparator implements Comparator<ModuleContainer> {
    @Override
    public int compare(ModuleContainer o1, ModuleContainer o2) {
        boolean hasDependency = hasDependency(o1);
        boolean hasOtherDependency = hasDependency(o2);

        if (hasDependency && !hasOtherDependency) {
            return 1;
        } else if (!hasDependency && hasOtherDependency) {
            return -1;
        } else {
            //The other depends on me
            if(ArrayUtils.search(o2.getDependencies(), o1.getId())){
                return -1;
            }

            //I depends on the other
            if(ArrayUtils.search(o1.getDependencies(), o2.getId())){
                return 1;
            }
        }

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