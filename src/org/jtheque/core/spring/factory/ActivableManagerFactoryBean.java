package org.jtheque.core.spring.factory;

import org.apache.commons.logging.LogFactory;
import org.jtheque.core.managers.ActivableManager;
import org.springframework.beans.factory.config.AbstractFactoryBean;

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
    private ActivableManager manager;

    /**
     * Set the manager to factor.
     *
     * @param manager The activable manager to factor.
     */
    public void setManager(ActivableManager manager) {
        this.manager = manager;
    }

    @Override
    public Class<?> getObjectType() {
        if (manager == null) { //Property not already set by Spring
            LogFactory.getLog(getClass()).error("Manager is null");

            return null;
        }

        return manager.getClass();
    }

    @Override
    protected Object createInstance() {
        return Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                manager.getClass().getInterfaces(),
                new ActivableManagerHandler(manager));
    }
}
