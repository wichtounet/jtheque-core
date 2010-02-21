package org.jtheque.core.managers.beans;

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

import org.jtheque.core.managers.AbstractManager;
import org.jtheque.core.managers.beans.ioc.Ioc;
import org.springframework.context.ApplicationContext;

/**
 * A beans manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class BeansManager extends AbstractManager implements IBeansManager {
    @Override
    public void close() {
        Ioc.getContainer().destroy();
    }

    @Override
    public <T> T getBean(String name) {
        return (T) Ioc.getContainer().getApplicationContext().getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> classz) {
        return Ioc.getContainer().getApplicationContext().getBean(classz);
    }

    @Override
    public void inject(Object bean) {
        Ioc.getContainer().inject(bean);
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return Ioc.getContainer().getApplicationContext();
    }
}