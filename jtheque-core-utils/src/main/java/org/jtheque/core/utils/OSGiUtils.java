package org.jtheque.core.utils;

import org.jtheque.utils.io.FileUtils;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import java.io.File;
import java.io.FileNotFoundException;

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

/**
 * OSGi utilities.
 *
 * @author Baptiste Wicht
 */
public final class OSGiUtils {
    /**
     * Utility class, not instantiable.
     */
    private OSGiUtils() {
        throw new AssertionError();
    }

    /**
     * Return the service of the given class in the given bundle context.
     *
     * @param context The bundle context
     * @param type  The class to get the service for.
     * @param <T>     The type of service.
     *
     * @return The service of the given type.
     */
    public static <T> T getService(BundleContext context, Class<T> type) {
        ServiceReference ref = context.getServiceReference(type.getName());

        if (ref != null) {
            return type.cast(context.getService(ref));
        }

        return null;
    }

    public static void update(Bundle bundle, File file) {
        try {
            bundle.update(FileUtils.asInputStream(file));
        } catch (BundleException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}