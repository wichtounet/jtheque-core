package org.jtheque.core.managers.beans;

import org.springframework.context.ApplicationContext;

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
 * A beans manager. It seems a manager who provide access to injected beans.
 *
 * @author Baptiste Wicht
 */
public interface IBeansManager {
    /**
     * Return the bean with a specific name.
     *
     * @param name The name of the bean.
     * @return The bean.
     */
    <T> T getBean(String name);

    /**
     * Inject the dependencies into the object. A dependency is habitually declared with the
     * Resource annotation.
     *
     * @param object The object to inject dependencies into
     */
    void inject(Object object);

    /**
     * Return the application context.
     *
     * @return The application context.
     */
    ApplicationContext getApplicationContext();
}