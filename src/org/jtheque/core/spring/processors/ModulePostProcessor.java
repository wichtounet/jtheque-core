package org.jtheque.core.spring.processors;

import org.jtheque.core.managers.module.annotations.Module;
import org.jtheque.core.managers.module.annotations.Plug;
import org.jtheque.core.managers.module.annotations.PrePlug;
import org.jtheque.core.managers.module.annotations.UnPlug;
import org.jtheque.core.managers.module.beans.EmptyBeanMethod;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.module.beans.ReflectionBeanMethod;
import org.jtheque.core.managers.module.loaders.ModuleLoader;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

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
 * @author Baptiste Wicht
 */
public final class ModulePostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Class<?> c = bean.getClass();

        if (isModule(c)) {
            registerModule(bean, beanName);
        }

        return bean;
    }

    /**
     * Test if the annotated element is a module.
     *
     * @param c The annotated element.
     * @return true if the element is a module else false.
     */
    private static boolean isModule(AnnotatedElement c) {
        return c.getAnnotation(Module.class) != null;
    }

    /**
     * Register a module.
     *
     * @param bean     The bean instance.
     * @param beanName The bean name.
     */
    private static void registerModule(Object bean, String beanName) {
        ModuleContainer module = new ModuleContainer(
                beanName,
                bean.getClass().getAnnotation(Module.class)
        );

        searchForAnnotatedMethods(bean, beanName, module);

        completeMethods(module);

        ModuleLoader.getModules().add(module);
    }

    /**
     * Search for annotated methods.
     *
     * @param bean     The bean instance.
     * @param beanName The bean name.
     * @param module   The module container to fill.
     */
    private static void searchForAnnotatedMethods(Object bean, String beanName, ModuleContainer module) {
        searchMethod(bean.getClass().getSuperclass(), beanName, module); //Proxy
        searchMethod(bean.getClass(), beanName, module); //Not proxy
    }

    /**
     * Search for module methods.
     *
     * @param beanClass The bean class.
     * @param beanName  The bean name.
     * @param module    The module container to fill.
     */
    private static void searchMethod(Class<?> beanClass, String beanName, ModuleContainer module) {
        for (Method m : beanClass.getDeclaredMethods()) {
            if (m.getAnnotation(UnPlug.class) != null) {
                module.setUnPlugMethod(new ReflectionBeanMethod(beanName, m.getName()));
            } else if (m.getAnnotation(Plug.class) != null) {
                module.setPlugMethod(new ReflectionBeanMethod(beanName, m.getName()));
            } else if (m.getAnnotation(PrePlug.class) != null) {
                module.setPrePlugMethod(new ReflectionBeanMethod(beanName, m.getName()));
            }
        }
    }

    /**
     * Complete the un-setted methods.
     *
     * @param module The module container to fill.
     */
    private static void completeMethods(ModuleContainer module) {
        if (module.getUnPlugMethod() == null) {
            module.setUnPlugMethod(new EmptyBeanMethod());
        }

        if (module.getPlugMethod() == null) {
            module.setPlugMethod(new EmptyBeanMethod());
        }

        if (module.getPrePlugMethod() == null) {
            module.setPrePlugMethod(new EmptyBeanMethod());
        }
    }
}