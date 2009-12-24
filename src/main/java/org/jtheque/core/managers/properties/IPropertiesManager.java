package org.jtheque.core.managers.properties;

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

/**
 * A properties manager specification.
 *
 * @author Baptiste Wicht
 */
public interface IPropertiesManager extends ActivableManager {
    /**
     * Create a memento for the bean. A memento is a clone of all the properties of the bean.
     * <p/>
     * Note : The properties of the Object class are not retrieved.
     *
     * @param bean The bean to create the memento from.
     * @param <T>  The class of the bean.
     * @return The memento.
     */
    <T> T createMemento(T bean);

    /**
     * Generate a toString() String based on all the properties of the bean.
     * <p/>
     * Note : The properties of the Object class are not retrieved.
     *
     * @param bean The bean.
     * @return A String representation of all the properties of the bean.
     */
    String toString(Object bean);

    /**
     * Restore the state of the memento. It seems to copy all the properties values from the memento to the bean.
     * <p/>
     * Note : The properties of the Object class are not retrieved.
     *
     * @param bean    The bean.
     * @param memento The memento.
     */
    void restoreMemento(Object bean, Object memento);

    /**
     * Test if two object are equals referring to a certain list of properties.
     *
     * @param bean       The first bean.
     * @param other      The other bean.
     * @param properties The properties to use.
     * @return <code>true</code> if the two objects are equals else <code>false</code>.
     */
    boolean areEquals(Object bean, Object other, String... properties);

    /**
     * Return the value of the property. This method doesn't parse the entire class, so it's quicker than
     * getProperty() but it doesn't use so if you've to use many times, use getProperty.
     *
     * @param bean     The bean to get the property value from.
     * @param property The property.
     * @return the value of the property.
     */
    Object getPropertyQuickly(Object bean, String property);
}