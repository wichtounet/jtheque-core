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

import org.jtheque.utils.StringUtils;

import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.awt.Color;

/**
 * A bean definition parser for the color element of the jtheque namespace.
 *
 * @author Baptiste Wicht
 */
public final class ColorBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {
    /**
     * The String definition of an int.
     */
    private static final String INT_VALUE = "int";

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        ConstructorArgumentValues values = new ConstructorArgumentValues();
        values.addGenericArgumentValue(Integer.parseInt(element.getAttribute("r")), INT_VALUE);
        values.addGenericArgumentValue(Integer.parseInt(element.getAttribute("g")), INT_VALUE);
        values.addGenericArgumentValue(Integer.parseInt(element.getAttribute("b")), INT_VALUE);

        if (StringUtils.isNotEmpty(element.getAttribute("alpha"))) {
            values.addGenericArgumentValue(Integer.parseInt(element.getAttribute("alpha")), INT_VALUE);
        }

        builder.getBeanDefinition().setConstructorArgumentValues(values);
    }

    @Override
    protected Class<?> getBeanClass(Element element) {
        return Color.class;
    }
}