package org.jtheque.core.managers.state;

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

import org.jtheque.utils.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A node of a state.
 *
 * @author Baptiste Wicht
 */
public final class NodeState {
    private final String name;
    private Collection<NodeState> childrens;
    private String text;
    private Collection<NodeStateAttribute> attributes;

    /**
     * Construct a new NodeState.
     *
     * @param name The name of the node.
     */
    public NodeState(String name) {
        super();

        this.name = name;

        childrens = new ArrayList<NodeState>(10);
        attributes = new ArrayList<NodeStateAttribute>(10);
    }

    /**
     * Construct a new NodeState.
     *
     * @param name The name of the node.
     * @param text The text of the node.
     */
    public NodeState(String name, String text) {
        super();

        this.name = name;
        this.text = text;

        childrens = new ArrayList<NodeState>(10);
        attributes = new ArrayList<NodeStateAttribute>(10);
    }

    /**
     * Return the name of the node.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the childrens of the node.
     *
     * @return A List containing all the NodeState children.
     */
    public Collection<NodeState> getChildrens() {
        return childrens;
    }

    /**
     * Set the childrens of the node.
     *
     * @param childrens The childrens.
     */
    public void setChildrens(Collection<NodeState> childrens) {
        this.childrens = CollectionUtils.copyOf(childrens);
    }

    /**
     * Add a simple child value.
     *
     * @param name  The name of the node.
     * @param value The value of the node.
     */
    public void addSimpleChildValue(String name, String value) {
        childrens.add(new NodeState(name, value));
    }

    /**
     * Return the text of the node.
     *
     * @return The text of the node.
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text of the node.
     *
     * @param text The text of the node.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Return the attributes of the node.
     *
     * @return A List containing all the attributes.
     */
    public Collection<NodeStateAttribute> getAttributes() {
        return attributes;
    }

    /**
     * Set the attributes of the node.
     *
     * @param attributes A List containing all the attributes.
     */
    public void setAttributes(Collection<NodeStateAttribute> attributes) {
        this.attributes = CollectionUtils.copyOf(attributes);
    }

    /**
     * Indicate if the node has children or not.
     *
     * @return true if the node has children else false.
     */
    public boolean hasChildren() {
        return childrens != null && !childrens.isEmpty();
    }

    /**
     * Indicate if the node has attribute or not.
     *
     * @return true if the node has attribute else false.
     */
    public boolean hasAttribute() {
        return attributes != null && !attributes.isEmpty();
    }

    /**
     * Set an attribute.
     *
     * @param key   The key of the attribute.
     * @param value The value of the attribute.
     */
    public void setAttribute(String key, String value) {
        NodeStateAttribute attribute = new NodeStateAttribute(key, value);

        attributes.add(attribute);
    }

    /**
     * Return the attribute value.
     *
     * @param key The name of the attribute.
     * @return The value of the attribute or null if the attribute doesn't exist.
     */
    public String getAttributeValue(String key) {
        String value = null;

        for (NodeStateAttribute attribute : attributes) {
            if (attribute.getKey().equals(key)) {
                value = attribute.getValue();
                break;
            }
        }

        return value;
    }
}