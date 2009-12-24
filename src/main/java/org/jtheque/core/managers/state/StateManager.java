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

import org.jdom.Attribute;
import org.jdom.Element;
import org.jtheque.core.managers.IManager;
import org.jtheque.core.managers.ManagerException;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.utils.file.XMLException;
import org.jtheque.core.utils.file.XMLReader;
import org.jtheque.core.utils.file.XMLWriter;
import org.jtheque.utils.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A state manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class StateManager implements IStateManager, IManager {
    private final Map<Class<? extends IState>, IState> states = new HashMap<Class<? extends IState>, IState>(10);

    @Override
    public void preInit() {
        try {
            loadStates();
        } catch (ManagerException e1) {
            Managers.getManager(IErrorManager.class).addError(new JThequeError(e1));
        }
    }

    /**
     * Load the states.
     *
     * @throws ManagerException If an error occurs during the reading of the states.
     */
    @Override
    public void loadStates() throws ManagerException {
        XMLReader reader = new XMLReader();

        try {
            reader.openFile(getConfigFile());

            for (Object currentNode : reader.getNodes("state", reader.getRootElement())) {

                Class<?> stateClass = Class.forName(reader.readString("@class", currentNode), true, getClass().getClassLoader());

                if (IState.class.isAssignableFrom(stateClass)) {
                    IState state = (IState) stateClass.newInstance();

                    loadState(reader, currentNode, state);

                    states.put(state.getClass(), state);
                }
            }
        } catch (Exception e) {
            throw new ConfigException(e);
        } finally {
            FileUtils.close(reader);
        }
    }

    /**
     * Load the state from the XML file.
     *
     * @param reader      The XML reader.
     * @param currentNode The node to read from.
     * @param state       The state to fill.
     * @throws XMLException If an error occurs during the XML reading process.
     */
    private static void loadState(XMLReader reader, Object currentNode, IState state) throws XMLException {
        if (state.isDelegated()) {
            Collection<Element> nodes = reader.getNodes("*", currentNode);

            state.delegateLoad(resolveNodeStates(nodes));
        } else {
            for (Object propertyNode : reader.getNodes("properties/property", currentNode)) {
                state.setProperty(reader.readString("@key", propertyNode), reader.readString("@value", propertyNode));
            }
        }
    }

    /**
     * Resolve the node states from the XML elements.
     *
     * @param nodes The nodes to transform to NodeState.
     * @return A List containing all the resolved NodeState.
     */
    private static Collection<NodeState> resolveNodeStates(Collection<Element> nodes) {
        Collection<NodeState> nodeStates = new ArrayList<NodeState>(nodes.size());

        for (Element element : nodes) {
            NodeState nodeState = resolve(element);

            nodeStates.add(nodeState);
        }

        return nodeStates;
    }

    @Override
    public void init() throws ManagerException {
        //Nothing to init
    }

    /**
     * Return the config file.
     *
     * @return The config file.
     */
    private static File getConfigFile() {
        File configFile = new File(Managers.getCore().getFolders().getApplicationFolder(), "/core/config.xml");

        if (!configFile.exists()) {
            FileUtils.createEmptyFile(configFile.getAbsolutePath());

            initConfigFile(configFile);
        }

        return configFile;
    }

    /**
     * Init the config file.
     *
     * @param file The config file.
     */
    private static void initConfigFile(File file) {
        XMLWriter writer = new XMLWriter("states");

        writer.write(file.getAbsolutePath());
    }

    @Override
    public void close() throws ManagerException {
        saveStates();
    }

    /**
     * Save the states.
     */
    private void saveStates() {
        XMLWriter writer = new XMLWriter("states");

        for (IState state : states.values()) {
            writer.add("state");
            writer.addAttribute("class", state.getClass().getCanonicalName());

            if (state.isDelegated()) {
                delegateSave(state, writer);
            } else {
                save(state, writer);
            }

            writer.switchToParent();
        }

        writer.write(getConfigFile().getAbsolutePath());
    }

    /**
     * Save the state to the writer.
     *
     * @param state  The state to save.
     * @param writer The writer.
     */
    private static void save(IState state, XMLWriter writer) {
        writer.add("properties");

        for (String key : state.getProperties()) {
            if (state.getProperty(key) != null) {
                writer.add("property");
                writer.addAttribute("key", key);
                writer.addAttribute("value", state.getProperty(key));

                writer.switchToParent();
            }
        }

        writer.switchToParent();
    }

    /**
     * Delegate the save to the state.
     *
     * @param state  The state to delegate the save process to.
     * @param writer The writer to use.
     */
    private static void delegateSave(IState state, XMLWriter writer) {
        Collection<NodeState> nodes = state.delegateSave();

        for (NodeState node : nodes) {
            add(node, writer);
        }
    }

    /**
     * Add the node state to the writer.
     *
     * @param node   The node state to add to the writer.
     * @param writer The XML writer.
     */
    private static void add(NodeState node, XMLWriter writer) {
        if (node.hasChildren()) {
            writer.add(node.getName());

            for (NodeState children : node.getChildrens()) {
                add(children, writer);
            }
        } else {
            writer.add(node.getName(), node.getText());
        }

        if (node.hasAttribute()) {
            for (NodeStateAttribute attribute : node.getAttributes()) {
                writer.addAttribute(attribute.getKey(), attribute.getValue());
            }
        }

        writer.switchToParent();
    }

    /**
     * Resolve a NodeState apart from an Element.
     *
     * @param element The Element representing the NodeState
     * @return The resolved NodeState.
     */
    private static NodeState resolve(Element element) {
        NodeState nodeState = new NodeState(element.getName());

        readNode(element, nodeState);
        readAttributes(element, nodeState);

        return nodeState;
    }

    /**
     * Read and fill the NodeState from the element.
     *
     * @param element   The element to read.
     * @param nodeState The node state to fill.
     */
    private static void readNode(Element element, NodeState nodeState) {
        if (element.getChildren().isEmpty()) {
            String text = element.getText();

            if (text != null && !text.isEmpty()) {
                nodeState.setText(text);
            }
        } else {
            Collection<Element> childrenElements = element.getChildren();

            Collection<NodeState> childrens = new ArrayList<NodeState>(childrenElements.size());

            for (Element childrenElement : childrenElements) {
                childrens.add(resolve(childrenElement));
            }

            nodeState.setChildrens(childrens);
        }
    }

    /**
     * Read the attributes of the node state.
     *
     * @param element   The element to get the attributes from.
     * @param nodeState The node state to fill.
     */
    private static void readAttributes(Element element, NodeState nodeState) {
        if (!element.getAttributes().isEmpty()) {
            Collection<Attribute> attributes = element.getAttributes();

            Collection<NodeStateAttribute> nodeAttributes = new ArrayList<NodeStateAttribute>(attributes.size());

            for (Attribute attribute : attributes) {
                nodeAttributes.add(new NodeStateAttribute(attribute.getName(), attribute.getValue()));
            }

            nodeState.setAttributes(nodeAttributes);
        }
    }

    @Override
    public <T extends IState> T getState(Class<T> c) {
        T state = null;

        if (states.containsKey(c)) {
            state = (T) states.get(c);
        }

        return state;
    }

    @Override
    public <T extends IState> T getOrCreateState(Class<T> c) throws StateException {
        return states.containsKey(c) ? (T) states.get(c) : createState(c);
    }

    @Override
    public <T extends IState> T createState(Class<T> c) throws StateException {
        T state;
        try {
            Object o = c.newInstance();

            state = (T) o;
        } catch (InstantiationException e) {
            throw new StateException(e);
        } catch (IllegalAccessException e) {
            throw new StateException(e);
        }

        states.put(c, state);

        return state;
    }

    @Override
    public void registerState(IState state) {
        states.put(state.getClass(), state);
    }
}