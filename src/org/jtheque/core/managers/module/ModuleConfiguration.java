package org.jtheque.core.managers.module;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.module.beans.ModuleContainer;
import org.jtheque.core.managers.module.beans.ModuleState;
import org.jtheque.core.managers.state.AbstractState;
import org.jtheque.core.managers.state.NodeState;
import org.jtheque.core.managers.update.InstallationResult;

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
    public void delegateLoad(Collection<NodeState> nodes) {
        for (NodeState node : nodes) {
            if ("module".equals(node.getName())) {
                infos.add(convertToModuleInfo(node));
            }
        }
    }

    /**
     * Convert the node state to a ModuleInfo.
     *
     * @param node The node state to convert to ModuleInfo.
     * @return The ModuleInfo.
     */
    private static ModuleInfo convertToModuleInfo(NodeState node) {
        ModuleInfo info = new ModuleInfo();

        info.setModuleId(node.getAttributeValue("id"));

        for (NodeState child : node.getChildrens()) {
            if ("state".equals(child.getName())) {
                info.setState(ModuleState.valueOf(Integer.parseInt(child.getText())));
            } else if ("file".equals(child.getName())) {
                info.setFilePath(child.getText());
            }
        }
        return info;
    }

    @Override
    public Collection<NodeState> delegateSave() {
        Collection<NodeState> states = new ArrayList<NodeState>(25);

        for (ModuleInfo info : infos) {
            states.add(convertToNodeState(info));
        }

        return states;
    }

    /**
     * Convert the module info the node state.
     *
     * @param info The module info.
     * @return The node state.
     */
    private static NodeState convertToNodeState(ModuleInfo info) {
        NodeState state = new NodeState("module");

        state.setAttribute("id", info.getModuleId());
        state.addSimpleChildValue("state", Integer.toString(info.getState().ordinal()));
        state.addSimpleChildValue("file", info.getFilePath());

        return state;
    }

    /**
     * Return the module information of a module.
     *
     * @param moduleName The name of the module.
     * @return The module information.
     */
    ModuleInfo getModuleInfo(String moduleName) {
        ModuleInfo info = null;

        for (ModuleInfo i : infos) {
            if (i.getModuleId().equals(moduleName)) {
                info = i;
            }
        }

        return info;
    }

    /**
     * Return the state of the module.
     *
     * @param moduleName The name of the module.
     * @return The state of the module.
     */
    public ModuleState getState(String moduleName) {
        ModuleState state = null;

        ModuleInfo info = getModuleInfo(moduleName);

        if (info != null) {
            state = info.getState();
        }

        return state;
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
    public boolean containsModule(ModuleContainer module) {
        boolean contains = false;

        for (ModuleInfo i : infos) {
            if (i.getFilePath().equals(module.getModuleFile().getName()) && i.getModuleId().equals(module.getId())) {
                contains = true;
                break;
            }
        }

        return contains;
    }

    /**
     * Remove the module.
     *
     * @param module The module to remove.
     */
    public void remove(ModuleContainer module) {
        ModuleInfo info = getModuleInfo(module.getId());

        infos.remove(info);
    }

    /**
     * Add a module container to the configuration.
     *
     * @param module The module container to add.
     */
    public void add(ModuleContainer module) {
        add(module, module.getState());
    }

    /**
     * Add a module to the configuration.
     *
     * @param module The module to add.
     * @param state  The state of the module.
     */
    public void add(ModuleContainer module, ModuleState state) {
        ModuleInfo info = new ModuleInfo();

        info.setState(state);
        info.setModuleId(module.getId());
        info.setFilePath(module.getModuleFile().getName());

        infos.add(info);
    }

    /**
     * Add the result of an installation to the module configuration.
     *
     * @param result The installation result.
     */
    public void add(InstallationResult result) {
        ModuleInfo info = new ModuleInfo();

        info.setFilePath(Managers.getCore().getFolders().getModulesFolder() + "/" + result.getJarFile());
        info.setModuleId(result.getName());
        info.setState(ModuleState.INSTALLED);

        infos.add(info);
    }
}