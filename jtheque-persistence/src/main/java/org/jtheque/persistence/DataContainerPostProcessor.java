package org.jtheque.persistence;

import org.jtheque.persistence.able.DataContainer;
import org.jtheque.persistence.able.DataContainerProvider;
import org.jtheque.persistence.able.Entity;
import org.springframework.beans.factory.config.BeanPostProcessor;

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
 * A post processor for the data container.
 *
 * @author Baptiste Wicht
 */
public final class DataContainerPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> c = bean.getClass();

        if (isDataContainer(c)) {
            DataContainerProvider.getInstance().addContainer((DataContainer<Entity>) bean);
        }

        return bean;
    }

    /**
     * Test if a class is a data container or not.
     *
     * @param c The class to test.
     * @return true if the class is a data container else false.
     */
    private static boolean isDataContainer(Class<?> c) {
        return DataContainer.class.isAssignableFrom(c);
    }
}
