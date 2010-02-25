package org.jtheque.core.utils.file.nodes;

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
import org.jtheque.utils.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A node of a state.
 *
 * @author Baptiste Wicht
 */
public final class Node {
    private final String name;
    private Collection<Node> childrens;
    private String text;
    private Collection<NodeAttribute> attributes;

    /**
     * Construct a new Node.
     *
     * @param name The name of the node.
     */
    public Node(String name) {
        super();

        this.name = name;

        childrens = new ArrayList<Node>(10);
        attributes = new ArrayList<NodeAttribute>(10);
    }

    /**
     * Construct a new Node.
     *
     * @param name The name of the node.
     * @param text The text of the node.
     */
    public Node(String name, String text) {
        super();

        this.name = name;
        this.text = text;

        childrens = new ArrayList<Node>(10);
        attributes = new ArrayList<NodeAttribute>(10);
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
     * @return A List containing all the Node children.
     */
    public Collection<Node> getChildrens() {
        return childrens;
    }

    /**
     * Set the childrens of the node.
     *
     * @param childrens The childrens.
     */
    public void setChildrens(Collection<Node> childrens) {
        this.childrens = CollectionUtils.copyOf(childrens);
    }

    /**
     * Add a simple child value.
     *
     * @param name  The name of the node.
     * @param value The value of the node.
     */
    public void addSimpleChildValue(String name, String value) {
        childrens.add(new Node(name, value));
    }

    public void addSimpleChildValue(String name, int value) {
        childrens.add(new Node(name, Integer.toString(value)));
    }

    public void addSimpleChildValue(String name, long value) {
        childrens.add(new Node(name, Long.toString(value)));
    }

    /**
     * Return the text of the node.
     *
     * @return The text of the node.
     */
    public String getText() {
        return text;
    }

    public int getInt() {
        return Integer.parseInt(text);
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
    public Collection<NodeAttribute> getAttributes() {
        return attributes;
    }

    /**
     * Set the attributes of the node.
     *
     * @param attributes A List containing all the attributes.
     */
    public void setAttributes(Collection<NodeAttribute> attributes) {
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
        NodeAttribute attribute = new NodeAttribute(key, value);

        attributes.add(attribute);
    }

    /**
     * Return the attribute value.
     *
     * @param key The name of the attribute.
     *
     * @return The value of the attribute or null if the attribute doesn't exist.
     */
    public String getAttributeValue(String key) {
        String value = null;

        for (NodeAttribute attribute : attributes) {
            if (attribute.getKey().equals(key)) {
                value = attribute.getValue();
                break;
            }
        }

        return value;
    }

    /**
     * Return the integer attribute value.
     *
     * @param key The name of the attribute.
     *
     * @return The int value of the attribute or 0 if the attribute doesn't exist.
     */
    public int getIntAttributeValue(String key){
        String value = getAttributeValue(key);

        if(StringUtils.isNotEmpty(value)){
            return Integer.parseInt(value);
        }

        return 0;
    }

    public String getChildValue(String name) {
        for(Node child : childrens){
            if(name.equals(child.name)){
                return child.text;
            }
        }

        return null;
    }

    public int getChildIntValue(String name) {
        String value = getChildValue(name);

        return value == null ? 0 : Integer.parseInt(value);
    }

    public long getChildLongValue(String name) {
        String value = getChildValue(name);

        return value == null ? 0 : Long.parseLong(value);
    }
}