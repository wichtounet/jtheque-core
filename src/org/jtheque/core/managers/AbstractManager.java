package org.jtheque.core.managers;

import org.jdesktop.swingx.event.WeakEventListenerList;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.log.IJThequeLogger;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.state.IStateManager;

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
 * An abstract manager.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractManager implements IManager {
    private static final WeakEventListenerList LISTENERS = new WeakEventListenerList();

    /**
     * Return the listeners of the manager.
     *
     * @return The listeners of the manager.
     */
    protected static WeakEventListenerList getListeners() {
        return LISTENERS;
    }

    /**
     * Return the logger of the manager.
     *
     * @return The logger of the manager.
     */
    protected final IJThequeLogger getLogger() {
        return getManager(ILoggingManager.class).getLogger(getClass());
    }

    /**
     * Return the error manager.
     *
     * @return The error manager.
     */
    protected static IErrorManager getErrors() {
        return getManager(IErrorManager.class);
    }

    /**
     * Return the state manager.
     *
     * @return The state manager.
     */
    protected static IStateManager getStates() {
        return getManager(IStateManager.class);
    }

    /**
     * Return the internationalized message.
     *
     * @param key The internationalization key.
     * @return The internationalized message.
     */
    protected static String getMessage(String key) {
        return getManager(ILanguageManager.class).getMessage(key);
    }

    /**
     * Return the internationalized message.
     *
     * @param key      The internationalization key.
     * @param replaces The objects to use for the replaces.
     * @return The internationalized message.
     */
    protected static String getMessage(String key, Object... replaces) {
        return getManager(ILanguageManager.class).getMessage(key, replaces);
    }

    /**
     * Return the manager with the specific class.
     *
     * @param managerClass The class of the manager.
     * @param <T>          The type of manager.
     * @return The manager.
     */
    protected static <T> T getManager(Class<T> managerClass) {
        return Managers.getManager(managerClass);
    }
}