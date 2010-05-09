package org.jtheque.spring.utils;

import org.jtheque.utils.ui.SwingUtils;
import org.springframework.context.ApplicationContext;

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

public class SwingSpringProxy<T> {
    private final Class<T> classz;
    private final ApplicationContext applicationContext;

    private T instance;

    public SwingSpringProxy(Class<T> classz, ApplicationContext applicationContext){
        super();


        this.classz = classz;
        this.applicationContext = applicationContext;
    }

    public T get(){
        if(instance == null){
            createInstance();
        }

        return instance;
    }

    private void createInstance() {
        SwingUtils.inEdtSync(new Runnable(){
            @Override
            public void run() {
                instance = applicationContext.getBean(classz);
            }
        });
    }
}