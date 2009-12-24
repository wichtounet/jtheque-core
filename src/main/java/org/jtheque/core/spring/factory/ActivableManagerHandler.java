package org.jtheque.core.spring.factory;

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

import org.jtheque.core.managers.ActivableManager;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.ILoggingManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * An invocation handler to disable the manager.
 *
 * @author Baptiste Wicht
 */
final class ActivableManagerHandler implements InvocationHandler {
    private final ActivableManager manager;

    /**
     * Construct a new ActivableManagerHandler.
     *
     * @param manager The manager to handle.
     */
    ActivableManagerHandler(ActivableManager manager) {
        super();

        this.manager = manager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (manager.isEnabled()) {
            return method.invoke(manager, args);
        } else {
            Managers.getManager(ILoggingManager.class).getLogger(manager.getClass()).warn("The manager is disabled. ");

            return null;
        }
    }
}
