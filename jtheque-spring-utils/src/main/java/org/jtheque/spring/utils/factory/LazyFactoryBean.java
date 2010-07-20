package org.jtheque.spring.utils.factory;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.Proxy;

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
 * A factory bean for keep a proxy of a bean.
 *
 * @author Baptiste Wicht
 */
public final class LazyFactoryBean extends AbstractFactoryBean<Object> {
    private final String beanName;
    private final boolean swing;
    private final Class<?> beanClass;
    private final GenericApplicationContext context;

    /**
     * Construct a new LazyFactoryBean.
     *
     * @param beanName  The name of the bean to keep the proxy for.
     * @param swing     A boolean tag indicating if the bean is swing bean.
     * @param beanClass The class of the bean to keep the proxy for.
     * @param context   The application context of the bean.
     *
     * @throws ClassNotFoundException If the bean class doesn't exist.
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
