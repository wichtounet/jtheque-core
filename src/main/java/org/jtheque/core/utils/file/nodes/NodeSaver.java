package org.jtheque.core.utils.file.nodes;

import org.jtheque.core.utils.file.XMLWriter;

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

public class NodeSaver {
    private NodeSaver() {
    }

    public static void writeNodes(XMLWriter writer, Iterable<Node> nodes) {
        for (Node node : nodes) {
            add(node, writer);
        }
    }

    /**
     * Add the node state to the writer.
     *
     * @param node   The node state to add to the writer.
     * @param writer The XML writer.
     */
    private static void add(Node node, XMLWriter writer) {
        if (node.hasChildren()) {
            writer.add(node.getName());

            writeNodes(writer, node.getChildrens());
        } else {
            writer.add(node.getName(), node.getText());
        }

        if (node.hasAttribute()) {
            for (NodeAttribute attribute : node.getAttributes()) {
                writer.addAttribute(attribute.getKey(), attribute.getValue());
            }
        }

        writer.switchToParent();
    }
}
