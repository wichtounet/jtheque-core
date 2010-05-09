package org.jtheque.spring.utils.extension;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.spring.utils.factory.LazyFactoryBean;
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

        factory.addConstructorArgValue(element.getAttribute("type"));

        Element targetElement = DomUtils.getChildElementByTagName(element, "bean");

        BeanDefinitionHolder definition = parserContext.getDelegate().parseBeanDefinitionElement(targetElement);
                
        GenericApplicationContext appContext = new GenericApplicationContext();
        appContext.registerBeanDefinition(beanName, definition.getBeanDefinition());
        appContext.getDefaultListableBeanFactory().addBeanPostProcessor(PROCESSOR);

        factory.addConstructorArgValue(appContext);

        return factory.getBeanDefinition();
    }
}