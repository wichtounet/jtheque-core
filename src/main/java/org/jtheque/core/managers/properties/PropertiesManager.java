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

import org.jtheque.core.managers.AbstractActivableManager;
import org.jtheque.core.managers.AbstractManager;
import org.jtheque.core.managers.ManagerException;
import org.jtheque.utils.bean.EqualsUtils;
import org.jtheque.utils.bean.ReflectionUtils;
import org.jtheque.utils.collections.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

/**
 * A properties manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class PropertiesManager extends AbstractActivableManager implements IPropertiesManager {
    @Override
    public <T> T createMemento(T bean) {
        T instance = null;

        try {
            instance = (T) bean.getClass().newInstance();

            for (PropertyDescriptor property : ReflectionUtils.getProperties(bean)) {
                if (property.getReadMethod() == null || property.getWriteMethod() == null) {
                    continue;
                }

                Object value = property.getReadMethod().invoke(bean);

                setValue(instance, property, value);
            }
        } catch (Exception e) {
            getLogger().error(e);
        }

        return instance;
    }

    @Override
    public void restoreMemento(Object bean, Object memento) {
        try {
            for (PropertyDescriptor property : ReflectionUtils.getProperties(bean)) {
                if (property.getReadMethod() == null || property.getWriteMethod() == null) {
                    continue;
                }

                Object value = ReflectionUtils.getProperty(memento, property);

                setValue(bean, property, value);
            }
        } catch (InvocationTargetException e) {
            getLogger().error(e);
        } catch (IllegalAccessException e) {
            getLogger().error(e);
        }
    }

    @Override
    public boolean areEquals(Object bean, Object other, String... properties) {
        if (bean == other) {
            return true;
        }

        if (EqualsUtils.areObjectIncompatible(bean, other)) {
            return false;
        }

        for (String property : properties) {
            Object propertyBean = getPropertyQuickly(bean, property);
            Object propertyOther = getPropertyQuickly(other, property);

            if (propertyBean == null) {
                if (propertyOther != null) {
                    return false;
                }
            } else if (!propertyBean.equals(propertyOther)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Object getProperty(Object bean, String property){
        return ReflectionUtils.getProperty(bean, property);
    }

    @Override
    public Object getPropertyQuickly(Object bean, String property) {
        return ReflectionUtils.getPropertyValue(bean, property);
    }

    /**
     * Set the value to the bean on the bean.
     *
     * @param bean     The bean to edit.
     * @param property The property to set.
     * @param value    The value to set to the property.
     * @throws IllegalAccessException    If the property cannot be accessed.
     * @throws InvocationTargetException If there is a problem during the setting process.
     */
    private static void setValue(Object bean, PropertyDescriptor property, Object value) throws IllegalAccessException, InvocationTargetException {
        if (value != null && List.class.isAssignableFrom(value.getClass())) {
            property.getWriteMethod().invoke(bean, CollectionUtils.copyOf((Collection<?>) value));
        } else {
            property.getWriteMethod().invoke(bean, value);
        }
    }

    @Override
    public String toString(Object bean) {
        StringBuilder builder = new StringBuilder(bean.getClass().getSimpleName());

        builder.append('{');

        for (PropertyDescriptor property : ReflectionUtils.getProperties(bean)) {
            if (property.getReadMethod() == null) {
                continue;
            }

            builder.append(property.getName()).append('=');
            builder.append(ReflectionUtils.getProperty(bean, property));
            builder.append(", ");
        }

        builder.delete(builder.lastIndexOf(", "), builder.lastIndexOf(", ") + 2);

        builder.append('}');

        return builder.toString();
    }
}