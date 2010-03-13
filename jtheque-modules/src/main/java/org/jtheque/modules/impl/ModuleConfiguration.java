package org.jtheque.modules.impl;

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

import org.jtheque.io.Node;
import org.jtheque.modules.able.IModuleManager;
import org.jtheque.modules.able.Module;
import org.jtheque.modules.able.ModuleState;
import org.jtheque.states.AbstractState;
import org.jtheque.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A module configuration.
 *
 * @author Baptiste Wicht
 */
public final class ModuleConfiguration extends AbstractState {
    private final Collection<ModuleInfo> infos = new ArrayList<ModuleInfo>(20);

    @Override
    public boolean isDelegated() {
        return true;
    }

    @Override
    public void delegateLoad(Collection<Node> nodes) {
        for (Node node : nodes){
            if ("module".equals(node.getName())) {
                infos.add(convertToModuleInfo(node));
            }
        }
    }

    /**
     * Convert the node state to a ModuleInfo.
     *
     * @param node The node state to convert to ModuleInfo.
     *
     * @return The ModuleInfo.
     */
    private static ModuleInfo convertToModuleInfo(Node node) {
        ModuleInfo info = new ModuleInfo(node.getAttributeValue("id"));

        if(StringUtils.isNotEmpty(node.getAttributeValue("state"))){
            info.setState(ModuleState.valueOf(node.getIntAttributeValue("state")));
        } else {
            for (Node child : node.getChildrens()) {
                if ("state".equals(child.getName())) {
                    info.setState(ModuleState.valueOf(Integer.parseInt(child.getText())));
                }
            }
        }

        if(info.getState() == null){
            info.setState(ModuleState.INSTALLED);
        }

        return info;
    }

    @Override
    public Collection<Node> delegateSave() {
        Collection<Node> states = new ArrayList<Node>(25);

        for (ModuleInfo info : infos) {
            states.add(convertToNodeState(info));
        }

        return states;
    }

    /**
     * Convert the module info the node state.
     *
     * @param info The module info.
     *
     * @return The node state.
     */
    private static Node convertToNodeState(ModuleInfo info) {
        Node state = new Node("module");

        state.setAttribute("id", info.getModuleId());
        state.setAttribute("state", Integer.toString(info.getState().ordinal()));

        return state;
    }

    /**
     * Return the module information of a module.
     *
     * @param moduleName The name of the module.
     *
     * @return The module information.
     */
    ModuleInfo getModuleInfo(String moduleName) {
        for (ModuleInfo i : infos) {
            if (i.getModuleId().equals(moduleName)) {
                return i;
            }
        }

        return null;
    }

    /**
     * Return the state of the module.
     *
     * @param moduleName The name of the module.
     * @return The state of the module.
     */
    public ModuleState getState(String moduleName) {
        ModuleInfo info = getModuleInfo(moduleName);

        if (info != null) {
            return info.getState();
        }

        return null;
    }

    /**
     * Set the state of the module.
     *
     * @param moduleName The name of the module.
     * @param state      The state.
     */
    public void setState(String moduleName, ModuleState state) {
        ModuleInfo info = getModuleInfo(moduleName);

        if (info != null) {
            info.setState(state);
        }
    }

    /**
     * Indicate if the configuration contains a module or not.
     *
     * @param module The module to test.
     * @return true if the manager contains the module.
     */
    public boolean containsModule(Module module) {
        for (ModuleInfo i : infos) {
            if (i.getModuleId().equals(module.getId())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Remove the module.
     *
     * @param module The module to remove.
     */
    public void remove(Module module) {
        ModuleInfo info = getModuleInfo(module.getId());

        infos.remove(info);
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
     * Add a module to the configuration.
     *
     * @param module The module to add.
     * @param state  The state of the module.
     */
    public void add(Module module, ModuleState state) {
        add(module.getId(), state);
    }

    /**
     * Add the result of an installation to the module configuration.
     *
     * @param result The installation result.
     */
    public void add(InstallationResult result) {
        add(result.getName(), ModuleState.INSTALLED);
    }

    /**
     * Add a module info to the configuration.
     *
     * @param id The module id.
     * @param state The module state.
     */
    private void add(String id, ModuleState state) {
        ModuleInfo info = new ModuleInfo(id);

        info.setState(state);

        infos.add(info);
    }

    @Override
    public void setDefaults() {
        for (Module module : ModulesServices.get(IModuleManager.class).getModules()) {
            add(module, ModuleState.INSTALLED);
        }
    }
}