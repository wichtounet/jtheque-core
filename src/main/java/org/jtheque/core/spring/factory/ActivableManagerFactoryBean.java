package org.jtheque.core.spring.factory;

import org.apache.commons.logging.LogFactory;
import org.jtheque.core.managers.ActivableManager;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.beans.ioc.Ioc;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.Proxy;

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
 * A factory bean for activable managers.
 *
 * @author Baptiste Wicht
 */
public final class ActivableManagerFactoryBean extends AbstractFactoryBean {
    private final String manager;
    private final Class<?> managerClass;
    private final GenericApplicationContext context;

    public ActivableManagerFactoryBean(String manager, Class<?> managerClass, GenericApplicationContext context) {
        super();

        this.managerClass = managerClass;
        this.context = context;
        this.manager = manager;
    }

    @Override
    public Class<?> getObjectType() {
        return managerClass;
    }

    @Override
    protected Object createInstance() {
        context.setParent(Ioc.getContainer().getApplicationContext());

        ActivableManager activableManager = (ActivableManager)context.getBean(manager);
        
        return Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                activableManager.getClass().getInterfaces(),
                new ActivableManagerHandler(activableManager));
    }
}
