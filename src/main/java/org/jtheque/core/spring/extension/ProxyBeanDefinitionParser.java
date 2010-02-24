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

import org.jtheque.core.spring.factory.LazyFactoryBean;
import org.jtheque.utils.StringUtils;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * A bean definition parser to parse the proxy element of the jtheque namespace.
 *
 * @author Baptiste Wicht
 */
public final class ProxyBeanDefinitionParser extends AbstractBeanDefinitionParser {
    private static final BeanPostProcessor PROCESSOR = new CommonAnnotationBeanPostProcessor();

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        if(element == null){
            return null;
        }

        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(LazyFactoryBean.class);

        String beanName = '_' + element.getAttribute("id");

        factory.addConstructorArgValue(beanName);

        String swing = element.getAttribute("swing");

        if(StringUtils.isNotEmpty(swing)){
            factory.addConstructorArgValue(Boolean.valueOf(swing));
        } else {
            factory.addConstructorArgValue(false);
        }

        try {
            factory.addConstructorArgValue(Class.forName(element.getAttribute("type")));
        } catch (ClassNotFoundException e) {
            parserContext.getReaderContext().error("Class not found", element, e);
        }

        Element targetElement = DomUtils.getChildElementByTagName(element, "bean");

        BeanDefinitionHolder definition = parserContext.getDelegate().parseBeanDefinitionElement(targetElement);
        
        GenericApplicationContext appContext = new GenericApplicationContext();
        appContext.registerBeanDefinition(beanName, definition.getBeanDefinition());
        appContext.getDefaultListableBeanFactory().addBeanPostProcessor(PROCESSOR);

        factory.addConstructorArgValue(appContext);

        return factory.getBeanDefinition();
    }
}