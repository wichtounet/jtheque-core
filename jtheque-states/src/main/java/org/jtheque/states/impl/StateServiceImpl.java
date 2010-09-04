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

import org.jtheque.states.State;
import org.jtheque.states.Load;
import org.jtheque.states.Save;
import org.jtheque.states.StateService;
import org.jtheque.utils.SystemProperty;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.bean.ReflectionUtils;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.utils.io.FileUtils;
import org.jtheque.xml.utils.XMLReader;
import org.jtheque.xml.utils.XMLWriter;
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
import java.util.Map;

/**
 * A state manager implementation.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
public final class StateServiceImpl implements StateService {
    @GuardedInternally
    private final Map<String, Object> states = CollectionUtils.newConcurrentMap(10);

    @GuardedInternally
    private final Map<String, Map<String, String>> properties = CollectionUtils.newConcurrentMap(5);
    
    @GuardedInternally
    private final Map<String, Collection<Node>> nodes = CollectionUtils.newConcurrentMap(5);

    @Override
    public <T> T getState(T state) {
        Method loadMethod = checkState(state);

        State stateAnnotation = state.getClass().getAnnotation(State.class);

        if (stateAnnotation.delegated()) {
            loadDelegatedState(state, stateAnnotation, loadMethod);
        } else {
            loadSimpleState(state, stateAnnotation, loadMethod);
        }

        states.put(stateAnnotation.id(), state);

        return state;
    }

    /**
     * Check the state for validity.
     *
     * @param state The state to check.
     *
     * @return The load method of the state.
     */
    private static Method checkState(Object state) {
        if (state == null) {
            throw new NullPointerException("The state cannot be null");
        }

        if (!state.getClass().isAnnotationPresent(State.class)) {
            throw new IllegalArgumentException("The state must be annotated with @State");
        }

        Method loadMethod = ReflectionUtils.getMethod(Load.class, state.getClass());

        if (loadMethod == null) {
            throw new IllegalArgumentException("The state must have a method with @Load annotation");
        }

        Method saveMethod = ReflectionUtils.getMethod(Save.class, state.getClass());

        if (saveMethod == null) {
            throw new IllegalArgumentException("The state must have a method with @Save annotation");
        }

        return loadMethod;
    }

    /**
     * Return the delegated state.
     *
     * @param state           The state to load.
     * @param stateAnnotation The state annotation of the state.
     * @param loadMethod      The load method.
     * @param <T>             The type of state.
     */
    private <T> void loadDelegatedState(T state, State stateAnnotation, Method loadMethod) {
        Collection<Node> stateNodes = nodes.remove(stateAnnotation.id());

        invokeLoadMethod(state, stateNodes, loadMethod);
    }

    /**
     * Return the simple state.
     *
     * @param state           The state to load.
     * @param stateAnnotation The state annotation of the state.
     * @param loadMethod      The load method.
     * @param <T>             The type of state.
     */
    private <T> void loadSimpleState(T state, State stateAnnotation, Method loadMethod) {
        Map<String, String> stateProperties = properties.remove(stateAnnotation.id());

        invokeLoadMethod(state, stateProperties, loadMethod);
    }

    /**
     * Invoke the load method of the state.
     *
     * @param state      The state.
     * @param parameter  The parameter to pass to the load method.
     * @param loadMethod The load method.
     */
    private static void invokeLoadMethod(Object state, Object parameter, Method loadMethod) {
        try {
            if (parameter != null) {
                loadMethod.invoke(state, parameter);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access the @Load method of " + state, e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unable to access the @Load method of " + state, e);
        }
    }

    /**
     * Load the states. Must only be called from Spring Framework.
     */
    @PostConstruct
    public void loadStates() {
        XMLReader<org.w3c.dom.Node> reader = XML.newJavaFactory().newReader();

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

                    Map<String, String> stateProperties = CollectionUtils.newHashMap(propertyElements.size());

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
        XMLWriter<org.w3c.dom.Node> writer = XML.newJavaFactory().newWriter("states");

        writer.write(file.getAbsolutePath());
    }

    /**
     * Save the states. Must only be called from Spring Framework.
     */
    @PreDestroy
    public void saveStates() {
        XMLWriter<org.w3c.dom.Node> writer = XML.newJavaFactory().newWriter("states");

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
    private void writeStates(XMLWriter<org.w3c.dom.Node> writer) {
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
    private static void simpleWrite(XMLWriter<org.w3c.dom.Node> writer, Map.Entry<String, Object> state, Method saveMethod) {
        writer.add("properties");

        Map<String, String> saveProperties = getObjectsFromSaveMethod(state.getValue(), saveMethod);

        for (Map.Entry<String, String> property : saveProperties.entrySet()) {
            writer.add("property");
            writer.addAttribute("key", property.getKey());
            writer.addAttribute("value", property.getValue());

            writer.switchToParent();
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
    private static void delegatedWrite(XMLWriter<org.w3c.dom.Node> writer, Map.Entry<String, Object> state, Method saveMethod) {
        Iterable<Node> savedNodes = getObjectsFromSaveMethod(state.getValue(), saveMethod);

        XML.newJavaFactory().newNodeSaver().writeNodes(writer, savedNodes);
    }

    /**
     * Return the objects from the save method.
     *
     * @param state      The state to invoke the method on.
     * @param saveMethod The save method.
     * @param <T>        The type of objects to get.
     *
     * @return The objects from the save method.
     */
    private static <T> T getObjectsFromSaveMethod(Object state, Method saveMethod) {
        try {
            return (T) saveMethod.invoke(state);
        } catch (IllegalAccessException e) {
            LoggerFactory.getLogger(StateServiceImpl.class).error("Unable to access the @Save method of " + state, e);
        } catch (InvocationTargetException e) {
            LoggerFactory.getLogger(StateServiceImpl.class).error("Unable to invoke the @Save method of " + state, e);
        }

        return null;
    }

    /**
     * Write the properties to the writer.
     *
     * @param writer The XML writer.
     */
    private void writeProperties(XMLWriter<org.w3c.dom.Node> writer) {
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
    private void writeNodes(XMLWriter<org.w3c.dom.Node> writer) {
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
}