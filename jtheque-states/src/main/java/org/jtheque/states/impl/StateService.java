package org.jtheque.states.impl;

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

import org.jtheque.core.utils.SystemProperty;
import org.jtheque.states.able.IStateService;
import org.jtheque.states.able.Load;
import org.jtheque.states.able.Save;
import org.jtheque.states.able.State;
import org.jtheque.utils.bean.ReflectionUtils;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.IXMLReader;
import org.jtheque.xml.utils.IXMLWriter;
import org.jtheque.xml.utils.Node;
import org.jtheque.xml.utils.XML;
import org.jtheque.xml.utils.XMLException;

import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
     */
    @PostConstruct
    public void loadStates() {
        IXMLReader<org.w3c.dom.Node> reader = XML.newJavaFactory().newReader();

        try {
            reader.openFile(getConfigFile());

            for (Object stateNode : reader.getNodes("state", reader.getRootElement())) {
                String id = reader.readString("@id", stateNode);
                boolean delegated = reader.readBoolean("@delegated", stateNode);

                if (delegated) {
                    Collection<org.w3c.dom.Node> nodeElements = reader.getNodes("*", stateNode);

                    Collection<Node> stateNodes = XML.newJavaFactory().newNodeLoader().resolveNodeStates(nodeElements);

                    nodes.put(id, stateNodes);
                } else {
                    Collection<org.w3c.dom.Node> propertyElements = reader.getNodes("properties/property", stateNode);

                    Map<String, String> stateProperties = new HashMap<String, String>(propertyElements.size());

                    for (Object propertyNode : propertyElements) {
                        stateProperties.put(reader.readString("@key", propertyNode), reader.readString("@value", propertyNode));
                    }

                    properties.put(id, stateProperties);
                }
            }
        } catch (XMLException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
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
        IXMLWriter<org.w3c.dom.Node> writer = XML.newJavaFactory().newWriter("states");
        writer.write(file.getAbsolutePath());
    }

    /**
     * Save the states.
     */
    @PreDestroy
    public void saveStates() {
        IXMLWriter<org.w3c.dom.Node> writer = XML.newJavaFactory().newWriter("states");

        writeStates(writer);
        writeProperties(writer);
        writeNodes(writer);

        writer.write(getConfigFile().getAbsolutePath());
    }

    /**
     * Write the states using the given writer.
     *
     * @param writer The write to use to write the states.
     */
    private void writeStates(IXMLWriter<org.w3c.dom.Node> writer) {
        for (Map.Entry<String, Object> state : states.entrySet()) {
            writer.add("state");
            writer.addAttribute("id", state.getKey());

            boolean delegated = state.getValue().getClass().getAnnotation(State.class).delegated();

            writer.addAttribute("delegated", Boolean.toString(delegated));

            Method saveMethod = ReflectionUtils.getMethod(Save.class, state.getValue().getClass());

            if (delegated) {
                delegatedWrite(writer, state, saveMethod);
            } else {
                simpleWrite(writer, state, saveMethod);
            }

            writer.switchToParent();
        }
    }

    /**
     * Simply writer the state with the writer.
     *
     * @param writer     The writer to use.
     * @param state      The state to write.
     * @param saveMethod The save method.
     */
    private void simpleWrite(IXMLWriter<org.w3c.dom.Node> writer, Map.Entry<String, Object> state, Method saveMethod) {
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

    /**
     * Write all the nodes of the given state.
     *
     * @param writer     The writer to use.
     * @param state      The state to write.
     * @param saveMethod The save method.
     */
    private void delegatedWrite(IXMLWriter<org.w3c.dom.Node> writer, Map.Entry<String, Object> state, Method saveMethod) {
        try {
            XML.newJavaFactory().newNodeSaver().writeNodes(writer, (Iterable<Node>) saveMethod.invoke(state.getValue()));
        } catch (IllegalAccessException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to access the @Save method of " + state, e);
        } catch (InvocationTargetException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to invoke the @Save method of " + state, e);
        }
    }

    /**
     * Write the properties to the writer.
     *
     * @param writer The XML writer.
     */
    private void writeProperties(IXMLWriter<org.w3c.dom.Node> writer) {
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

    /**
     * Write the nodes to the writer.
     *
     * @param writer The writer.
     */
    private void writeNodes(IXMLWriter<org.w3c.dom.Node> writer) {
        for (Map.Entry<String, Collection<Node>> state : nodes.entrySet()) {
            writer.add("state");

            writer.addAttribute("id", state.getKey());
            writer.addAttribute("delegated", "true");

            XML.newJavaFactory().newNodeSaver().writeNodes(writer, state.getValue());

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
        if (state.getClass().isAnnotationPresent(State.class)) {
            State stateAnnotation = state.getClass().getAnnotation(State.class);

            Method loadMethod = ReflectionUtils.getMethod(Load.class, state.getClass());

            if (loadMethod == null) {
                throw new IllegalArgumentException("The state must have a method with @Load annotation");
            }

            if (stateAnnotation.delegated()) {
                getDelegatedState(state, stateAnnotation, loadMethod);
            } else {
                getSimpleState(state, stateAnnotation, loadMethod);
            }

            states.put(stateAnnotation.id(), state);
        }

        return state;
    }

    /**
     * Return the delegated state.
     *
     * @param state           The state to load.
     * @param stateAnnotation The state annotation of the state.
     * @param loadMethod      The load method.
     * @param <T>             The type of state.
     */
    private <T> void getDelegatedState(T state, State stateAnnotation, Method loadMethod) {
        try {
            Collection<Node> stateNodes = nodes.get(stateAnnotation.id());

            if (stateNodes != null) {
                loadMethod.invoke(state, stateNodes);
            }

            nodes.remove(stateAnnotation.id());
        } catch (IllegalAccessException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to access the @Load method of " + state, e);
        } catch (InvocationTargetException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to invoke the @Load method of " + state, e);
        }
    }

    /**
     * Return the simple state.
     *
     * @param state           The state to load.
     * @param stateAnnotation The state annotation of the state.
     * @param loadMethod      The load method.
     * @param <T>             The type of state.
     */
    private <T> void getSimpleState(T state, State stateAnnotation, Method loadMethod) {
        try {
            Map<String, String> stateProperties = properties.get(stateAnnotation.id());

            if (stateProperties != null) {
                loadMethod.invoke(state, stateProperties);
            }

            properties.remove(stateAnnotation.id());
        } catch (IllegalAccessException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to access the @Load method of " + state, e);
        } catch (InvocationTargetException e) {
            LoggerFactory.getLogger(getClass()).error("Unable to invoke the @Load method of " + state, e);
        }
    }
}