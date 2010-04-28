package org.jtheque.spring.utils.factory;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.Proxy;
import java.util.logging.Logger;

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
 * A factory bean for keep a proxy of a bean.
 *
 * @author Baptiste Wicht
 */
public final class LazyFactoryBean extends AbstractFactoryBean {
    private final String beanName;
    private final boolean swing;
    private final Class<?> beanClass;
    private final GenericApplicationContext context;

    /**
     * Construct a new LazyFactoryBean.
     *
     * @param beanName  The name of the bean to keep the proxy for.
     * @param swing A boolean tag indicating if the bean is swing bean.
     * @param beanClass The class of the bean to keep the proxy for.
     * @param context The application context of the bean. 
     */
    public LazyFactoryBean(String beanName, boolean swing, String beanClass, GenericApplicationContext context) throws ClassNotFoundException {
        super();

        this.beanName = beanName;
        this.swing = swing;
        this.context = context;

        try {
            this.beanClass = Class.forName(beanClass);
        } catch (ClassNotFoundException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to get the class from the proxified object", e);

            throw e;
        }
    }

    @Override
    public Class<?> getObjectType() {
        return beanClass;
    }

    @Override
    protected Object createInstance() {
        return Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                beanClass.getInterfaces(),
                new LazyProxyHandler(beanName, swing, context));
    }
}
