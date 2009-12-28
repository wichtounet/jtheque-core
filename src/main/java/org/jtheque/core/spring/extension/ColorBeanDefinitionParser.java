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