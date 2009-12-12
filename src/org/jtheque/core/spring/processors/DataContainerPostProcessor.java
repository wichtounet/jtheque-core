package org.jtheque.core.spring.processors;

import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.managers.persistence.able.DataContainerProvider;
import org.jtheque.core.managers.persistence.able.Entity;
import org.springframework.beans.factory.config.BeanPostProcessor;

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
