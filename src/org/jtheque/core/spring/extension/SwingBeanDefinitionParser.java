package org.jtheque.core.spring.extension;

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

import org.jtheque.core.spring.factory.SwingBeanCreator;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * A bean definition parser to parse the proxy element of the jtheque namespace.
 *
 * @author Baptiste Wicht
 */
public final class SwingBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {
    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        try {
            builder.addConstructorArgValue(Class.forName(element.getAttribute("class")));
        } catch (ClassNotFoundException e) {
            parserContext.getReaderContext().error("Class not found", element, e);
        }
    }

    @Override
    protected Class<?> getBeanClass(Element element) {
        return SwingBeanCreator.class;
    }
}