package org.jtheque.spring.utils.injection;

import org.jtheque.utils.bean.ReflectionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

public class Injector {
    public static void inject(ApplicationContext applicationContext, Object object){
        if(object instanceof Injectable){
            applicationContext.getAutowireCapableBeanFactory().autowireBean(object);

            Method initMethod = ReflectionUtils.getMethod(Init.class, object.getClass());

            if(initMethod != null){
                try {
                    initMethod.invoke(object);
                } catch (IllegalAccessException e) {
                    LoggerFactory.getLogger(Injector.class).error("Unable to invoke the init method", e);
                } catch (InvocationTargetException e) {
                    LoggerFactory.getLogger(Injector.class).error("Unable to invoke the init method", e);
                }
            }
        }
    }
}