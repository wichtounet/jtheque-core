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

import org.jtheque.core.utils.CoreUtils;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;

/**
 * A factory bean for keep a proxy of a bean.
 *
 * @author Baptiste Wicht
 */
public final class SwingBeanCreator extends AbstractFactoryBean {
    private final Class<?> beanClass;

    /**
     * Construct a new LazyFactoryBean.
     *
     * @param beanClass The class of the bean to keep the proxy for.
     */
    public SwingBeanCreator(Class<?> beanClass) {
        super();

        this.beanClass = beanClass;
    }

    @Override
    public Class<?> getObjectType() {
        return beanClass;
    }

    @Override
    protected Object createInstance() {
        final Object[] instance = new Object[1];

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    instance[0] = beanClass.newInstance();
                } catch (InstantiationException e) {
                    CoreUtils.getLogger(SwingBeanCreator.this.getClass()).error(e);
                } catch (IllegalAccessException e) {
                    CoreUtils.getLogger(SwingBeanCreator.this.getClass()).error(e);
                }
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(runnable);
            } catch (InterruptedException e) {
                CoreUtils.getLogger(getClass()).error(e);
            } catch (InvocationTargetException e) {
                CoreUtils.getLogger(getClass()).error(e);
            }
        }

        return instance[0];
    }
}