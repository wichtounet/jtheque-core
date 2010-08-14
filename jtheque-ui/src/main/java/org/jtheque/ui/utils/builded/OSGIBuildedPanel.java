package org.jtheque.ui.utils.builded;

import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.core.utils.SwingSpringProxy;
import org.jtheque.errors.Error;
import org.jtheque.errors.ErrorService;
import org.jtheque.i18n.LanguageService;
import org.jtheque.utils.collections.CollectionUtils;

import org.osgi.framework.BundleContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.osgi.context.BundleContextAware;

import java.util.Collection;

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
 * An OSGi builded panel.
 *
 * @author Baptiste Wicht
 */
public abstract class OSGIBuildedPanel extends BuildedPanel implements BundleContextAware, ApplicationContextAware {
    private BundleContext bundleContext;
    private ApplicationContext applicationContext;

    @Override
    protected LanguageService getLanguageService() {
        return getService(LanguageService.class);
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
    protected <T> T getService(Class<T> classz) {
        return OSGiUtils.getService(bundleContext, classz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Return the bean of the given class using the application context.
     *
     * @param classz The classz of the bean to get from application context.
     * @param <T>    The type of bean to get.
     *
     * @return The bean of the given class or null if it doesn't exist.
     */
    protected <T> T getBean(Class<T> classz) {
        return applicationContext.getBean(classz);
    }

    /**
     * Return the bean of the given class using the application context. The bean will be retrieved in the EDT, so it
     * can be used for a Swing bean.
     *
     * @param classz The classz of the bean to get from application context.
     * @param <T>    The type of bean to get.
     *
     * @return The bean of the given class or null if it doesn't exist.
     */
    protected <T> T getBeanFromEDT(Class<T> classz) {
        return new SwingSpringProxy<T>(classz, applicationContext).get();
    }

    @Override
    public boolean validateContent() {
        Collection<Error> errors = CollectionUtils.newList(5);

        validate(errors);

        for (Error error : errors) {
            getService(ErrorService.class).addError(error);
        }

        return errors.isEmpty();
    }

    /**
     * Validate the panel. By default the validation does nothing, so the panel is always valid.
     *
     * @param errors The errors to fill.
     */
    protected void validate(Collection<org.jtheque.errors.Error> errors) {
        //Default empty implementation
    }
}