package org.jtheque.views.utils;

import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.views.able.Controller;

import org.osgi.framework.BundleContext;
import org.springframework.osgi.context.BundleContextAware;

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
 * An abstract controller.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractController implements Controller, BundleContextAware {
    private BundleContext bundleContext;

    @Override
    public final void closeView() {
        getView().closeDown();
    }

    @Override
    public void displayView() {
        getView().display();
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    /**
     * Return the service of the given class.
     *
     * @param classz The class of the service to get.
     * @param <T>    The Type of class.
     *
     * @return The service.
     */
    protected <T> T getService(Class<T> classz) {
        return OSGiUtils.getService(bundleContext, classz);
    }
}