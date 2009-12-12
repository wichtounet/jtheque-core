package org.jtheque.core.managers;

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

import org.jtheque.core.managers.core.Core;
import org.jtheque.core.managers.core.ICore;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.utils.collections.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class provide access to the different managers of the application.
 *
 * @author Baptiste Wicht
 */
public final class Managers {
    private static final Map<Class<?>, IManager> MANAGERS = new LinkedHashMap<Class<?>, IManager>(10);

    /**
     * This is an utility class, not instanciable.
     */
    private Managers() {
        super();
    }

    /**
     * Pre-init the different managers of the application.
     */
    public static void preInitManagers() {
        for (IManager manager : MANAGERS.values()) {
            manager.preInit();
        }
    }

    /**
     * Init the different managers of the application.
     */
    public static void initManagers() {
        for (IManager manager : MANAGERS.values()) {
            try {
                manager.init();
            } catch (ManagerException e) {
                Managers.getManager(ILoggingManager.class).getLogger(Managers.class).error(e);
                Managers.getManager(IErrorManager.class).addInternationalizedError("error.loading.manager");
            }
        }
    }

    /**
     * Close the different managers of the application.
     */
    public static void closeManagers() {
        CollectionUtils.reverse(MANAGERS);

        for (IManager manager : MANAGERS.values()) {
            try {
                manager.close();
            } catch (ManagerException e) {
                Managers.getManager(ILoggingManager.class).getLogger(Managers.class).error(e);
                Managers.getManager(IErrorManager.class).addInternationalizedError("error.loading.manager");
            }
        }
    }

    /**
     * Return the core.
     *
     * @return The core.
     */
    public static ICore getCore() {
        return Core.getInstance();
    }

    /**
     * Return the manager with the specified class.
     *
     * @param managerClass The class of the manager.
     * @param <T>          The type of the manager.
     * @return The manager.
     */
    public static <T> T getManager(Class<T> managerClass) {
        return (T) MANAGERS.get(managerClass);
    }

    /**
     * Load the manager from the manager container.
     *
     * @param container The manager container.
     */
    static void loadManagers(ManagerContainer container) {
        MANAGERS.putAll(container.getManagers());
    }
}