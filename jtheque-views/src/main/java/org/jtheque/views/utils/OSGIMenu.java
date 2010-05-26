package org.jtheque.views.utils;

import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.spring.utils.SwingSpringProxy;
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
 * A menu in OSGI spring context.
 *
 * @author Baptiste Wicht
 */
public class OSGIMenu extends AbstractMenu implements BundleContextAware, ApplicationContextAware {
	private ApplicationContext applicationContext;
	private BundleContext bundleContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}


    /**
     * Return the service of the given class.
     *
     * @param classz The class to get the service.
     * @param <T>    The type of service.
     *
     * @return The service of the given class if it's exists otherwise null.
     */
    protected <T> T getService(Class<T> classz){
        return OSGiUtils.getService(bundleContext, classz);
    }

    /**
     * Return the bean of the given class using the application context.
     *
     * @param classz The classz of the bean to get from application context.
     * @param <T> The type of bean to get.
     *
     * @return The bean of the given class or null if it doesn't exist. 
     */
    protected <T> T getBean(Class<T> classz){
        return applicationContext.getBean(classz);
    }

    /**
     * Return the bean of the given class using the application context. The bean will be retrieved in the EDT, so
     * it can be used for a Swing bean.
     *
     * @param classz The classz of the bean to get from application context.
     * @param <T> The type of bean to get.
     *
     * @return The bean of the given class or null if it doesn't exist.
     */
	protected <T> T getBeanFromEDT(Class<T> classz){
        return new SwingSpringProxy<T>(classz, applicationContext).get();
    }
}