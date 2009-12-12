package org.jtheque.core.managers.schema;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.properties.IPropertiesManager;
import org.jtheque.utils.Constants;
import org.jtheque.utils.StringUtils;

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

/**
 * An abstract schema. The only thing this abstract class do is implementing the compareTo() method to ensure for
 * good order in collections.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractSchema implements Schema {
    @Override
    public final int compareTo(Schema other) {
        boolean hasDependency = StringUtils.isNotEmpty(getDependencies());
        boolean hasOtherDependency = StringUtils.isNotEmpty(other.getDependencies());

        if (hasDependency && !hasOtherDependency) {
            return 1;
        } else if (!hasDependency && hasOtherDependency) {
            return -1;
        } else if(hasDependency && hasOtherDependency){
            for (String dependency : other.getDependencies()) {
                if (dependency.equals(getId())) {//The other depends on me
                    return -1;
                }
            }

            for (String dependency : getDependencies()) {
                if (dependency.equals(other.getId())) {//I depends on the other
                    return 1;
                }
            }
        }

        return 0;
    }

    @Override
    public int hashCode() {
        int hash = Constants.HASH_CODE_START;
        
        hash = Constants.HASH_CODE_PRIME * hash + getVersion().hashCode();
        hash = Constants.HASH_CODE_PRIME * hash + getId().hashCode();
        
        for(String dependency : getDependencies()){
            hash = Constants.HASH_CODE_PRIME * hash + dependency.hashCode();
        }
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return Managers.getManager(IPropertiesManager.class).areEquals(this, obj, "id", "version");
    }

    @Override
    public final String toString() {
        return getId() + ':' + getVersion();
    }
}
