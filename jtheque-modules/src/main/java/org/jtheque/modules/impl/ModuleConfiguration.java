package org.jtheque.modules.impl;

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

import org.jtheque.modules.Module;
import org.jtheque.modules.ModuleState;
import org.jtheque.states.Load;
import org.jtheque.states.Save;
import org.jtheque.states.State;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.annotations.GuardedInternally;
import org.jtheque.utils.annotations.ThreadSafe;
import org.jtheque.utils.collections.CollectionUtils;
import org.jtheque.xml.utils.Node;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A module configuration.
 *
 * @author Baptiste Wicht
 */
@ThreadSafe
@State(id = "jtheque-modules-configuration", delegated = true)
final class ModuleConfiguration {
    @GuardedInternally
    private final Map<String, ModuleState> states = CollectionUtils.newConcurrentMap(20);

    /**
     * Load the nodes in the state.
     *
     * @param nodes The nodes to load.
     */
    @Load
    public void delegateLoad(Iterable<Node> nodes) {
        for (Node node : nodes) {
            if ("module".equals(node.getName())) {
                loadModuleInfo(node);
            }
        }
    }

    /**
     * Convert the node state to a ModuleInfo.
     *
     * @param node The node state to convert to ModuleInfo.
     */
    private void loadModuleInfo(Node node) {
        String id = node.getAttributeValue("id");

        if (StringUtils.isNotEmpty(node.getAttributeValue("state"))) {
            states.put(id, ModuleState.valueOf(node.getIntAttributeValue("state")));
        } else {
            states.put(id, ModuleState.INSTALLED);
        }
    }

    /**
     * Save the nodes.
     *
     * @return All the nodes to be saved by the state.
     */
    @Save
    public Collection<Node> delegateSave() {
        Collection<Node> nodes = CollectionUtils.newList(25);

        for (Entry<String, ModuleState> state : states.entrySet()) {
            nodes.add(convertToNode(state));
        }

        return nodes;
    }

    /**
     * Convert a state to a Node.
     *
     * @param state The state to convert.
     *
     * @return A Node corresponding to the given state. 
     */
    private static Node convertToNode(Entry<String, ModuleState> state) {
        Node node = new Node("module");

        node.setAttribute("id", state.getKey());
        node.setAttribute("state", Integer.toString(state.getValue().ordinal()));

        return node;
    }

    /**
     * Return the state of the module.
     *
     * @param id The id of the module.
     *
     * @return The state of the module.
     */
    public ModuleState getState(String id) {
        return states.get(id);
    }

    /**
     * Set the state of the module.
     *
     * @param id    The id of the module.
     * @param state The state.
     */
    public void setState(String id, ModuleState state) {
        states.put(id, state);
    }

    /**
     * Indicate if the configuration contains a module or not.
     *
     * @param module The module to test.
     *
     * @return true if the manager contains the module.
     */
    boolean containsModule(Module module) {
        return states.containsKey(module.getId());
    }

    /**
     * Remove the module.
     *
     * @param module The module to remove.
     */
    public void remove(Module module) {
        states.remove(module.getId());
    }

    /**
     * Add a module container to the configuration.
     *
     * @param module The module container to add.
     */
    public void add(Module module) {
        add(module.getId(), module.getState());
    }

    /**
     * Add a module info to the configuration.
     *
     * @param id    The module id.
     * @param state The module state.
     */
    private void add(String id, ModuleState state) {
        states.put(id, state);
    }
}