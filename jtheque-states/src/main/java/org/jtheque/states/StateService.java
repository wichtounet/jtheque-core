package org.jtheque.states;

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

import org.jdom.Element;
import org.jtheque.core.utils.SystemProperty;
import org.jtheque.io.Node;
import org.jtheque.io.NodeLoader;
import org.jtheque.io.NodeSaver;
import org.jtheque.io.XMLReader;
import org.jtheque.io.XMLWriter;
import org.jtheque.utils.bean.ReflectionUtils;
import org.jtheque.utils.io.FileUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A state manager implementation.
 *
 * @author Baptiste Wicht
 */
public final class StateService implements IStateService {
    private final Map<String, Object> states = new HashMap<String, Object>(10);
    private final Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>(5);
    private final Map<String, Collection<Node>> nodes = new HashMap<String, Collection<Node>>(5);

    /**
     * Load the states.
     *
     * @throws StateException If an error occurs during the reading of the states.
     */
    public void loadStates() throws StateException{
        XMLReader reader = new XMLReader();

        try {
            reader.openFile(getConfigFile());

            for (Object stateNode : reader.getNodes("state", reader.getRootElement())) {
                String id = reader.readString("@id", stateNode);
                boolean delegated = reader.readBoolean("@delegated", stateNode);

                if(delegated){
                    Collection<Element> nodeElements = reader.getNodes("*", stateNode);

                    Collection<Node> stateNodes = NodeLoader.resolveNodeStates(nodeElements);

                    nodes.put(id, stateNodes);
                } else {
                    Collection<Element> propertyElements = reader.getNodes("properties/property", stateNode);

                    Map<String, String> stateProperties = new HashMap<String, String>(propertyElements.size());

                    for (Object propertyNode : propertyElements) {
                        stateProperties.put(reader.readString("@key", propertyNode), reader.readString("@value", propertyNode));
                    }

                    properties.put(id, stateProperties);
                }
            }
        } catch (Exception e) {
            throw new StateException(e);
        } finally {
            FileUtils.close(reader);
        }
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

    public void close(){
        XMLWriter writer = new XMLWriter("states");

        writeStates(writer);
        writeProperties(writer);
        writeNodes(writer);

        writer.write(getConfigFile().getAbsolutePath());
    }

    private void writeStates(XMLWriter writer) {
        for(Map.Entry<String, Object> state : states.entrySet()){
            writer.add("state");
            writer.addAttribute("id", state.getKey());

            boolean delegated = state.getValue().getClass().getAnnotation(State.class).delegated();

            writer.addAttribute("delegated", Boolean.toString(delegated));

            Method saveMethod = ReflectionUtils.getMethod(Save.class, state.getValue().getClass());

            if(delegated){
                try {
                    Iterable<Node> nodes = (Iterable<Node>) saveMethod.invoke(state.getValue());

                    NodeSaver.writeNodes(writer, nodes);
                } catch (IllegalAccessException e) {
                    LoggerFactory.getLogger(getClass()).error("Unable to access the @Save method of " + state, e);
                } catch (InvocationTargetException e) {
                    LoggerFactory.getLogger(getClass()).error("Unable to invoke the @Save method of " + state, e);
                }
            } else {
                writer.add("properties");

                try {
                    Map<String, String> properties = (Map<String, String>) saveMethod.invoke(state.getValue());

                    for (Map.Entry<String, String> property : properties.entrySet()) {
                        writer.add("property");
                        writer.addAttribute("key", property.getKey());
                        writer.addAttribute("value", property.getValue());

                        writer.switchToParent();
                    }
                } catch (IllegalAccessException e) {
                    LoggerFactory.getLogger(getClass()).error("Unable to access the @Save method of " + state, e);
                } catch (InvocationTargetException e) {
                    LoggerFactory.getLogger(getClass()).error("Unable to invoke the @Save method of " + state, e);
                }

                writer.switchToParent();
            }

            writer.switchToParent();
        }
    }

    private void writeProperties(XMLWriter writer) {
        for (Map.Entry<String, Map<String, String>> state : properties.entrySet()) {
            writer.add("state");

            writer.addAttribute("id", state.getKey());
            writer.addAttribute("delegated", "false");

            writer.add("properties");

            for (Map.Entry<String, String> property : state.getValue().entrySet()) {
                writer.add("property");
                writer.addAttribute("key", property.getKey());
                writer.addAttribute("value", property.getValue());

                writer.switchToParent();
            }

            writer.switchToParent();

            writer.switchToParent();
        }
    }

    private void writeNodes(XMLWriter writer) {
        for (Map.Entry<String, Collection<Node>> state : nodes.entrySet()) {
            writer.add("state");

            writer.addAttribute("id", state.getKey());
            writer.addAttribute("delegated", "true");

            NodeSaver.writeNodes(writer, state.getValue());

            writer.switchToParent();
        }
    }

    /**
     * Return the config file.
     *
     * @return The config file.
     */
    private static File getConfigFile() {
        File configFile = new File(SystemProperty.USER_DIR.get(), "config.xml");

        if (!configFile.exists()) {
            FileUtils.createEmptyFile(configFile.getAbsolutePath());

            initConfigFile(configFile);
        }

        return configFile;
    }

    @Override
    public <T> T getState(T state) {
        if(state.getClass().isAnnotationPresent(State.class)){
            State stateAnnotation = state.getClass().getAnnotation(State.class);

            Method loadMethod = ReflectionUtils.getMethod(Load.class, state.getClass());

            if(loadMethod == null){
                throw new IllegalArgumentException("The state must have a method with @Load annotation");
            }

            if(stateAnnotation.delegated()){
                try {
                    Collection<Node> stateNodes = nodes.get(stateAnnotation.id());

                    if(stateNodes != null){
                        loadMethod.invoke(state, stateNodes);
                    }

                    nodes.remove(stateAnnotation.id());
                } catch (IllegalAccessException e) {
                    LoggerFactory.getLogger(getClass()).error("Unable to access the @Load method of " + state, e);
                } catch (InvocationTargetException e) {
                    LoggerFactory.getLogger(getClass()).error("Unable to invoke the @Load method of " + state, e);
                }
            } else {
                try {
                    Map<String, String> stateProperties = properties.get(stateAnnotation.id());

                    if(stateProperties != null){
                        loadMethod.invoke(state, stateProperties);
                    }

                    properties.remove(stateAnnotation.id());
                } catch (IllegalAccessException e) {
                    LoggerFactory.getLogger(getClass()).error("Unable to access the @Load method of " + state, e);
                } catch (InvocationTargetException e) {
                    LoggerFactory.getLogger(getClass()).error("Unable to invoke the @Load method of " + state, e);
                }
            }

            states.put(stateAnnotation.id(), state);
        }

        return state;
    }
}