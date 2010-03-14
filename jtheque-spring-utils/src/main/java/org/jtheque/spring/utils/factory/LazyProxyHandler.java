package org.jtheque.spring.utils.factory;

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