package org.jtheque.io;

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
