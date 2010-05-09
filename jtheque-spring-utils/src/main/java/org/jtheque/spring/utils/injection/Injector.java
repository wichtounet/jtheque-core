package org.jtheque.spring.utils.injection;

import org.jtheque.utils.bean.ReflectionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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