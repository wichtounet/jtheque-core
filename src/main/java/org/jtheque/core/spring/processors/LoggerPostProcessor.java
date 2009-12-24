package org.jtheque.core.spring.processors;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.IJThequeLogger;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.log.Logger;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
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
 * A bean post process to process @Logger and to inject loggers to the classes.
 *
 * @author Baptiste Wicht
 */
public final class LoggerPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> clazz = bean.getClass();

        for (final Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(Logger.class) != null) {
               AccessController.doPrivileged(new MakeFieldAccessible(field));

                IJThequeLogger logger = Managers.getManager(ILoggingManager.class).getLogger(clazz);

                try {
                    field.set(bean, logger);
                } catch (IllegalAccessException e) {
                    throw new BeanInitializationException("Unable to access field " + field.getName(), e);
                }
            }
        }

        return bean;
    }

    private static final class MakeFieldAccessible implements PrivilegedAction<Object> {
        private final Field field;

        private MakeFieldAccessible(Field field) {
            super();
            
            this.field = field;
        }

        @Override
        public Object run() {
            field.setAccessible(true);

            return null;
        }
    }
}