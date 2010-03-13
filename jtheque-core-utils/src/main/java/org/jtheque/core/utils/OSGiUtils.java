package org.jtheque.core.utils;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

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

public class OSGiUtils {
    public static <T> T getService(BundleContext context, Class<T> classz){
        ServiceReference ref = context.getServiceReference(classz.getName());

        if(ref != null){
            return (T) context.getService(ref);
        }

        return null;
    }

    public static Bundle getBundle(BundleContext context, String symbolicName) {
        for(Bundle bundle : context.getBundles()){
            if(symbolicName.equals(bundle.getSymbolicName())){
                return bundle;
            }
        }
                
        return null;
    }
}