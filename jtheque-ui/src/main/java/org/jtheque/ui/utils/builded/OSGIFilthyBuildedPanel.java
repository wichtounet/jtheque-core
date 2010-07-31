package org.jtheque.ui.utils.builded;

import org.jtheque.core.utils.OSGiUtils;
import org.jtheque.core.utils.SwingSpringProxy;
import org.jtheque.errors.able.IError;
import org.jtheque.errors.able.IErrorService;
import org.jtheque.i18n.able.LanguageService;
import org.jtheque.ui.able.IFilthyUtils;
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
 * An filthy panel builded with a panel builder in osgi spring context.
 *
 * @author Baptiste Wicht
 */
public abstract class OSGIFilthyBuildedPanel extends FilthyBuildedPanel implements BundleContextAware,
        ApplicationContextAware {
    private BundleContext bundleContext;
    private ApplicationContext applicationContext;

    private IFilthyUtils utils;

    @Override
    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    /**
     * Return the service of the given class.
     *
     * @param type The class to get the service.
     * @param <T>  The type of service.
     *
     * @return The service of the given class if it's exists otherwise null.
     */
    protected <T> T getService(Class<T> type) {
        return OSGiUtils.getService(bundleContext, type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Return the bean of the given class using the application context.
     *
     * @param type The type of the bean to get from application context.
     * @param <T>  The type of bean to get.
     *
     * @return The bean of the given class or null if it doesn't exist.
     */
    protected <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    /**
     * Return the bean of the given class using the application context.
     *
     * @param id   The in of the Spring bean.
     * @param type The classz of the bean to get from application context.
     * @param <T>  The type of bean to get.
     *
     * @return The bean of the given class or null if it doesn't exist.
     */
    protected <T> T getBean(String id, Class<T> type) {
        return applicationContext.getBean(id, type);
    }

    /**
     * Return the bean of the given class using the application context. The bean will be retrieved in the EDT, so it
     * can be used for a Swing bean.
     *
     * @param type The classz of the bean to get from application context.
     * @param <T>  The type of bean to get.
     *
     * @return The bean of the given class or null if it doesn't exist.
     */
    protected <T> T getBeanFromEDT(Class<T> type) {
        return new SwingSpringProxy<T>(type, applicationContext).get();
    }

    @Override
    protected LanguageService getLanguageService() {
        return getService(LanguageService.class);
    }

    @Override
    protected IFilthyUtils getFilthyUtils() {
        if (utils == null) {
            utils = getService(IFilthyUtils.class);
        }

        return utils;
    }

    @Override
    public boolean validateContent() {
        Collection<IError> errors = CollectionUtils.newList(5);

        validate(errors);

        for (IError error : errors) {
            getService(IErrorService.class).addError(error);
        }

        return errors.isEmpty();
    }

    /**
     * Validate the panel.
     *
     * @param errors The errors.
     */
    public void validate(Collection<IError> errors) {
        //Default empty implementation
    }
}