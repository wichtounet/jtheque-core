package org.jtheque.modules.impl;

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

import org.jtheque.modules.able.Module;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.collections.ArrayUtils;

import java.util.Comparator;

/**
 * @author Baptiste Wicht
 */
final class ModuleComparator implements Comparator<Module> {
    @Override
    public int compare(Module o1, Module o2) {
        boolean hasDependency = hasDependency(o1);
        boolean hasOtherDependency = hasDependency(o2);

        if (hasDependency && !hasOtherDependency) {
            return 1;
        } else if (!hasDependency && hasOtherDependency) {
            return -1;
        } else {
            //The other depends on me
            if (ArrayUtils.search(o2.getDependencies(), o1.getId())) {
                return -1;
            }

            //I depends on the other
            if (ArrayUtils.search(o1.getDependencies(), o2.getId())) {
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