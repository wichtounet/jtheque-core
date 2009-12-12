package org.jtheque.core.spring.extension;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

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
public final class JThequeNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("proxy", new ProxyBeanDefinitionParser());
        registerBeanDefinitionParser("swing-bean", new SwingBeanDefinitionParser());
        registerBeanDefinitionParser("activable", new ActivableBeanDefinitionParser());
        registerBeanDefinitionParser("color", new ColorBeanDefinitionParser());
        registerBeanDefinitionParser("point", new PointBeanDefinitionParser());
        registerBeanDefinitionParser("close", new JThequeActionBeanDefinitionParser("close"));
        registerBeanDefinitionParser("display", new JThequeActionBeanDefinitionParser("display"));
    }
}
