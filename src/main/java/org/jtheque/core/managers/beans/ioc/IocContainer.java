package org.jtheque.core.managers.beans.ioc;

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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractMessageSource;

/**
 * An IoC container specification.
 *
 * @author Baptiste Wicht
 */
public interface IocContainer {
    /**
     * Return the resource bundle of the IoC container.
     *
     * @return The resource bundle of the container.
     */
    AbstractMessageSource getResourceBundle();

    /**
     * Return the application context.
     *
     * @return The Spring application context.
     */
    ApplicationContext getApplicationContext();

    /**
     * Load the context.
     */
    void loadContext();

    /**
     * Destroy the application context.
     */
    void destroy();

    /**
     * Inject the dependencies into the object. A dependency is habitually declared with the
     * Resource annotation. If the class has been annotated with AfterInject, the method will be invoked after the
     * injection.
     *
     * @param bean The object to inject dependencies into
     */
    void inject(Object bean);

    /**
     * Add a beans file.
     *
     * @param file The beans file to add.
     */
    void addBeansFile(String file);
}
