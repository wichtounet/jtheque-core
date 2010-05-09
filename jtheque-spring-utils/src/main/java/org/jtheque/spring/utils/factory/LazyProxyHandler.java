package org.jtheque.spring.utils.factory;

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

import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericApplicationContext;

import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * An invocation handler to disable the manager.
 *
 * @author Baptiste Wicht
 */
final class LazyProxyHandler implements InvocationHandler {
    private final String beanName;
    private final boolean swing;
    private final GenericApplicationContext context;

    private Object instance;

    /**
     * Construct a new LazyProxyHandler.
     *
     * @param beanName The name of the bean.
     * @param swing A boolean tag indicating if the bean is swing bean.
     * @param context The application context of the target. 
     */
    LazyProxyHandler(String beanName, boolean swing, GenericApplicationContext context) {
        super();

        this.beanName = beanName;
        this.swing = swing;
        this.context = context;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (instance == null) {
            LoggerFactory.getLogger(getClass()).debug("Init {} due to call to {}", beanName, method.toGenericString());

            if(swing && !SwingUtilities.isEventDispatchThread()){
                try {
                    SwingUtilities.invokeAndWait(new Runnable(){
                        @Override
                        public void run() {
                            instance = context.getBean(beanName);
                        }
                    });
                } catch (InterruptedException e) {
                    LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
                }
            } else {
                instance = context.getBean(beanName);
            }
        }

        return method.invoke(instance, args);
    }
}