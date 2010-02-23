package org.jtheque.core.utils.file;

import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;

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
 * An XML writer.
 *
 * @author Baptiste Wicht
 */
public final class XMLWriter {
    private final Document document;

    private Element current;

    /**
     * Construct a new XML writer.
     *
     * @param root The name of the root element.
     */
    public XMLWriter(String root) {
        super();

        document = new Document(new Element(root));

        current = getRoot();
    }

    /**
     * Add the element to the document and set the new element as the current element.
     *
     * @param element The element to add.
     */
    public void add(String element) {
        Element newElement = new Element(element);

        current.addContent(newElement);

        current = newElement;
    }

    /**
     * Add an element to the document and set the new element as the current element.
     *
     * @param element The name of the element to add.
     * @param text    The text of the element.
     */
    public void add(String element, String text) {
        add(element);

        current.setText(text);
    }

    /**
     * Add the element.
     *
     * @param element The name of the element.
     * @param text    The text of the element.
     */
    public void addOnly(String element, String text) {
        Element newElement = new Element(element);

        newElement.setText(text);

        current.addContent(newElement);
    }

    public void addOnlyWithCDATA(String element, String text) {
        Element newElement = new Element(element);

        newElement.setContent(new CDATA(text));

        current.addContent(newElement);
    }

    /**
     * Add only the element with the specified value.
     *
     * @param element The element.
     * @param value   The value of the element.
     */
    public void addOnly(String element, int value) {
        addOnly(element, Integer.toString(value));
    }

    /**
     * Add an attribute to the current element.
     *
     * @param key   The key of the attribute.
     * @param value The value of the attribute.
     */
    public void addAttribute(String key, String value) {
        current.setAttribute(key, value);
    }

    /**
     * Return the root element of the document.
     *
     * @return The root element of the document.
     */
    public Element getRoot() {
        return document.getRootElement();
    }

    /**
     * Set the current element.
     *
     * @param element The current element.
     */
    public void setCurrent(Element element) {
        current = element;
    }

    /**
     * Write the XML document to the file path.
     *
     * @param filePath The file path.
     */
    public void write(String filePath) {
        XMLUtils.writeXml(document, filePath);
    }

    /**
     * Switch to parent.
     */
    public void switchToParent() {
        current = current.getParentElement();
    }
}