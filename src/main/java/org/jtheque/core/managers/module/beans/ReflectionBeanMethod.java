package org.jtheque.core.managers.module.beans;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.log.ILoggingManager;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

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
 * A bean method who invoke a method by reflection.
 *
 * @author Baptiste Wicht
 */
public final class ReflectionBeanMethod implements BeanMethod {
    private final String beanName;
    private final String methodName;

    /**
     * Construct a new ReflectionBeanMethod.
     *
     * @param beanName   The name of the bean to invoke the method from.
     * @param methodName The name of the method.
     */
    public ReflectionBeanMethod(String beanName, String methodName) {
        super();

        this.beanName = beanName;
        this.methodName = methodName;
    }

    @Override
    public void run() {
        Object bean = Managers.getManager(IBeansManager.class).getBean(beanName);

        try {
            final Method method = bean.getClass().getDeclaredMethod(methodName);

            AccessController.doPrivileged(new MakeMethodAccessible(method));

            method.invoke(bean);
        } catch (Exception e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }
    }

    /**
     * A privileged action to make a method accessible.
     *
     * @author Baptiste Wicht
     */
    private static final class MakeMethodAccessible implements PrivilegedAction<Object> {
        private final Method method;

        /**
         * Construct a new MakeMethodAccessible.
         *
         * @param method The method to make accessible.
         */
        private MakeMethodAccessible(Method method) {
            super();
            
            this.method = method;
        }

        @Override
        public Object run() {
            method.setAccessible(true);

            return null;
        }
    }
}
