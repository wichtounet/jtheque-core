package org.jtheque.spring.utils;

import org.jtheque.utils.ui.SwingUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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
 * A Swing Spring Proxy. It's a proxy for a swing bean guaranteeing that the bean is created in the EDT.
 *
 * @author Baptiste Wicht
 */
public class SwingSpringProxy<T> implements ApplicationContextAware {
    private final Class<T> classz;

    private ApplicationContext applicationContext;

    private T instance;

    /**
     * Construct a new SwingSpringProxy of the given class.
     *
     * @param classz             The class of the view.
     * @param applicationContext The application context.
     */
    public SwingSpringProxy(Class<T> classz, ApplicationContext applicationContext) {
        super();

        this.classz = classz;
        this.applicationContext = applicationContext;
    }

    /**
     * Construct a new SwingSpringProxy of the given class. Only used for beans in Spring contexts
     *
     * @param classz The class of the view.
     */
    public SwingSpringProxy(Class<T> classz) {
        super();

        this.classz = classz;
    }

    /**
     * Return the instance of the view. The instance is only created only once. This proxy assert that the instance is
     * created in EDT.
     *
     * @return The instance of the view.
     */
    public T get() {
        if (instance == null) {
            createInstance();
        }

        return instance;
    }

    /**
     * Create the instance in the EDT.
     */
    private void createInstance() {
        SwingUtils.inEdt(new Runnable() {
            @Override
            public void run() {
                instance = applicationContext.getBean(classz);
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}