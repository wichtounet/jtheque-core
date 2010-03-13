package org.jtheque.io;

import org.jdom.Attribute;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.Collection;

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

public class NodeLoader {
    private NodeLoader() {
        super();
    }
    
    /**
     * Resolve the node states from the XML elements.
     *
     * @param nodes The nodes to transform to Node.
     * @return A List containing all the resolved Node.
     */
    public static Collection<Node> resolveNodeStates(Collection<Element> nodes) {
        Collection<Node> nodeStates = new ArrayList<Node>(nodes.size());

        for (Element element : nodes) {
            Node node = resolve(element);

            nodeStates.add(node);
        }

        return nodeStates;
    }

    /**
     * Resolve a Node apart from an Element.
     *
     * @param element The Element representing the Node
     * @return The resolved Node.
     */
    private static Node resolve(Element element) {
        Node node = new Node(element.getName());

        readNode(element, node);
        readAttributes(element, node);

        return node;
    }

    /**
     * Read and fill the Node from the element.
     *
     * @param element   The element to read.
     * @param node The node state to fill.
     */
    private static void readNode(Element element, Node node) {
        if (element.getChildren().isEmpty()) {
            String text = element.getText();

            if (text != null && !text.isEmpty()) {
                node.setText(text);
            }
        } else {
            Collection<Element> childrenElements = element.getChildren();

            Collection<Node> childrens = new ArrayList<Node>(childrenElements.size());

            for (Element childrenElement : childrenElements) {
                childrens.add(resolve(childrenElement));
            }

            node.setChildrens(childrens);
        }
    }

    /**
     * Read the attributes of the node state.
     *
     * @param element   The element to get the attributes from.
     * @param node The node state to fill.
     */
    private static void readAttributes(Element element, Node node) {
        if (!element.getAttributes().isEmpty()) {
            Collection<Attribute> attributes = element.getAttributes();

            Collection<NodeAttribute> nodeAttributes = new ArrayList<NodeAttribute>(attributes.size());

            for (Attribute attribute : attributes) {
                nodeAttributes.add(new NodeAttribute(attribute.getName(), attribute.getValue()));
            }

            node.setAttributes(nodeAttributes);
        }
    }
}