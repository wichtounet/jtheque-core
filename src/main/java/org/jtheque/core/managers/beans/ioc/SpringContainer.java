package org.jtheque.core.managers.beans.ioc;

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

import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The spring context. This class is responsible for the Spring Application Context.
 *
 * @author Baptiste Wicht
 */
public final class SpringContainer implements IocContainer {
    private ClassPathXmlApplicationContext applicationContext;

    private final Collection<String> files = new ArrayList<String>(10);

    /**
     * Construct a new SpringContainer.
     */
    public SpringContainer() {
        super();

        files.add("org/jtheque/core/spring/core-beans.xml");
    }

    @Override
    public AbstractMessageSource getResourceBundle() {
        return (AbstractMessageSource) getApplicationContext().getBean("messageSource");
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void loadContext() {
        LogFactory.getLog(SpringContainer.class).debug("Load Spring with XML files : " + files);

        applicationContext = new ClassPathXmlApplicationContext(files.toArray(new String[files.size()]));
        applicationContext.registerShutdownHook();
    }

    @Override
    public void destroy() {
        if (applicationContext != null) {
            applicationContext.destroy();
        }
    }

    @Override
    public void inject(Object bean) {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
    }

    @Override
    public void addBeansFile(String file) {
        files.add(file);
    }
}