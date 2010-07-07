package org.jtheque.modules.utils;

import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.modules.able.IModuleService;
import org.jtheque.modules.able.SwingLoader;
import org.jtheque.utils.collections.ArrayUtils;
import org.jtheque.utils.ui.SwingUtils;

import org.osgi.framework.BundleContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
 * An abstract module for Swing module.
 *
 * @author Baptiste Wicht
 */
public class SwingModule implements ApplicationContextAware, SwingLoader, BundleContextAware {
    private final String[] edtBeans;

    private ApplicationContext applicationContext;
    private final String id;

    /**
     * Construct a new SwingModule.
     *
     * @param id       The id of the module.
     * @param edtBeans The beans to load in EDT.
     */
    public SwingModule(String id, String[] edtBeans) {
        super();

        this.id = id;
        this.edtBeans = ArrayUtils.copyOf(edtBeans);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        OSGiUtils.getService(bundleContext, IModuleService.class).registerSwingLoader(id, this);
    }

    @Override
    public void afterAll() {
        for (final String bean : edtBeans) {
            SwingUtils.inEdt(new Runnable() {
                @Override
                public void run() {
                    applicationContext.getBean(bean);
                }
            });
        }
    }
}