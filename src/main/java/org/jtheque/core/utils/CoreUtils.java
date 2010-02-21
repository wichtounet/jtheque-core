package org.jtheque.core.utils;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.log.IJThequeLogger;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.view.able.IMainView;
import org.jtheque.core.managers.view.able.IViewManager;

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
 * Utility class for the most used methods of the Core.
 *
 * @author Baptiste Wicht
 */
public final class CoreUtils {
    /**
     * Utility class, not instanciable. 
     */
    private CoreUtils(){
        super();
    }

    /**
     * Return the bean with a specific name.
     *
     * @param name The name of the bean.
     * @return The bean.
     */
    public static <T> T getBean(String name){
        return Managers.getManager(IBeansManager.class).<T>getBean(name);
    }

    /**
     * Return the bean of the specified type.
     *
     * @param classz The class to search for.
     * @param <T> The type of class.
     *
     * @return The bean of the specified type if there is one otherwise null. 
     */
    public static <T> T getBean(Class<T> classz){
        return Managers.getManager(IBeansManager.class).<T>getBean(classz);
    }

    /**
     * Return the main view.
     *
     * @return the main view.
     */
    public static IMainView getMainView(){
        return getView().getViews().getMainView();
    }

    /**
     * Return the view manager.
     *
     * @return The view manager. 
     */
    public static IViewManager getView(){
        return Managers.getManager(IViewManager.class);
    }

    /**
     * Return the logger for a class.
     *
     * @param classz The class for which we want the logger.
     *
     * @return The appropriate logger.
     */
    public static IJThequeLogger getLogger(Class<?> classz){
        return Managers.getManager(ILoggingManager.class).getLogger(classz);
    }

    /**
     * Return the message of the key. If there is no message with this key, the method return the key and
     * log the message to the log system.
     *
     * @param key The message key.
     *
     * @return The message of the key of the current locale, empty string if the key is <code>null</code> else
     *         the key if there is no message for this key.
     */
    public static String getMessage(String key){
        return Managers.getManager(ILanguageManager.class).getMessage(key);
    }

    /**
     * Return the message of the key and effect the replaces.
     *
     * @param key      The message key.
     * @param replaces The replacements.
     * @return The message of the current locale with the replacements.
     */
    public static String getMessage(String key, Object... replaces){
        return Managers.getManager(ILanguageManager.class).getMessage(key, replaces);
    }
}